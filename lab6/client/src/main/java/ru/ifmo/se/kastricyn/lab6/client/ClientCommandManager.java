package ru.ifmo.se.kastricyn.lab6.client;

import ru.ifmo.se.kastricyn.lab6.client.command.Exit;
import ru.ifmo.se.kastricyn.lab6.client.command.Help;
import ru.ifmo.se.kastricyn.lab6.lib.AbstractCommandManager;
import ru.ifmo.se.kastricyn.lab6.lib.Command;
import ru.ifmo.se.kastricyn.lab6.lib.connection.ServerAnswer;
import ru.ifmo.se.kastricyn.lab6.lib.connection.ServerRequest;
import ru.ifmo.se.kastricyn.lab6.lib.data.Ticket;
import ru.ifmo.se.kastricyn.lab6.lib.data.Venue;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Console;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientCommandManager extends AbstractCommandManager {
    private Console console;
    private Connection connection;

    public ClientCommandManager(Connection connection, Console console) {
        this.connection = connection;
        this.console = console;
    }

    public static ClientCommandManager getStandards(Connection connection, Console console) {
        ClientCommandManager ccm = new ClientCommandManager(connection, console);
        ccm.addIfAbsent(new Exit());
        ccm.addIfAbsent(new Help());
        return ccm;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Исполняет команду, имя которой передано в первом аргументе, если она доступна в менеджере команд
     *
     * @param commandName имя команды
     * @param args        аргументы команды в строковом представлении
     */
    public void executeCommand(String commandName, String... args) throws JAXBException, IOException {
        Command command = getCommand(commandName);
        if (command == null) {
            while (true) {
                ServerRequest sr;
                connection.sendRequest(sr = new ServerRequest(commandName));
                ServerAnswer sa = connection.getAnswer();
                switch (sa.getSat()) {
                    case OK:
                        console.println(sa.getAnswer());
                        return;
                    case NOT_FOUND_COMMAND:
                        console.printlnErr("Команды" + commandName + " не существует. Для вызова справки введите: help");
                        return;
                    case MISTAKE_ARGS:
                        console.println("Полученные дополнительные аргументы были ошибочны, попробуйте ввести их ещё раз:");
                    case NEED_ARGS:
                        console.println("Для этой команды необходимы дополнительные аргументы.");
                        for (Class eClass :
                                sa.getParamTypes()) {
                            if (eClass.equals(Ticket.class))
                                sr.addParams(new Ticket(console));
                            else if (eClass.equals(Venue.class))
                                sr.addParams(new Venue(console));
                        }
                        connection.sendRequest(sr);
                        break;
                    default:
                        console.printlnErr("Какой-то новый ServerAnswerType");
                }
            }

        }
        //установка аргументов команде
        ArrayList<Object> arguments = new ArrayList<>();
        for (Class eClass :
                command.getArgumentTypes()) {
            if (eClass.equals(ClientCommandManager.class))
                arguments.add(this);
        }
        command.setArguments(arguments);
        command.execute(args);
        console.println(command.getAnswer());
    }

    /**
     * Обрабатывает команды, пока они поступают от пользователя.
     * Надо переопределить для конкретной реализации
     */
    @Override
    public void run() {
        while (true) {
            String t = console.nextLine().trim();
            if (t.isEmpty())
                return;
            String[] s = t.trim().split("\\s");
            try {
                executeCommand(s[0], Arrays.copyOfRange(s, 1, s.length));
            } catch (JAXBException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
