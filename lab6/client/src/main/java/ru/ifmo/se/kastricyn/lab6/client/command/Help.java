package ru.ifmo.se.kastricyn.lab6.client.command;

import ru.ifmo.se.kastricyn.lab6.client.ClientAbstractCommand;
import ru.ifmo.se.kastricyn.lab6.client.ClientCommandManager;
import ru.ifmo.se.kastricyn.lab6.lib.connection.ServerRequest;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Console;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Help extends ClientAbstractCommand {
    /**
     * конструктор класса наседника, принимает на вход параметры, необходимые для реализации конкретной команды
     * вызвается из конструктора класса наследника
     */
    public Help() {
        super("help", "вывести справку по доступным командам");
        setNeedArgumentType(ClientCommandManager.class, Stream.class);
    }

    /**
     * @param args аргументы команды
     * @throws IndexOutOfBoundsException if paramsIsValidate()!=true
     */
    @Override
    public void execute(String... args) {
        ClientCommandManager ccm = objArgs.getCommandManager();

        try {
            //получаем доступные команды от сервера
            ccm.getConnection().sendRequest(new ServerRequest("help"));
            String string = ccm.getConnection().getAnswer().getAnswer();

            // выделяем команды как строки в Set
            final String regex = "^([\\w\\{}\\s-]{2,})([А-Яа-я ,\\.()\\w]+)$";
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(string);
            Set<String> strings = new HashSet<>();
            while (matcher.find())
                strings.add(matcher.group(0));

            answer = Console.getStringFromStream("Доступны следующие команды:",
                    Stream.of(strings, ccm.getCommandsAsString().sorted()));
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
