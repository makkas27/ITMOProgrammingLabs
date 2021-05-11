package ru.ifmo.se.kastricyn.lab6.lib;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Исполнитель команд. Класс реализует управление коммандами, доступными для пользователя.
 * Разные объекты этого класса могут по-разному исполнять комманды.
 */
public abstract class AbstractCommandManager implements Runnable {
    protected HashMap<String, AbstractCommand> commands;

    /**
     * Создаёт менеджер коммандами.
     */
    public AbstractCommandManager() {
        commands = new HashMap<>();
    }

    /**
     * Добавляет команду в менеджер команд, если её не было до этого
     *
     * @param cmd команда
     */
    public void addIfAbsent(AbstractCommand cmd) {
        commands.putIfAbsent(cmd.getName(), cmd);
    }

    /**
     * Возвращает поток из команд в строковом представлении
     */
    public Stream<String> getCommandsAsString() {
        return commands.values().stream().map(AbstractCommand::toString);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }


    /**
     * Исполняет команду, имя которой передано в первом аргументе, если она доступна в менеджере команд
     *
     * @param commandName имя команды
     * @param args        аргументы команды в строковом представлении
     */
    public abstract void executeCommand(String commandName, String... args) throws JAXBException, IOException;

    /**
     * Обрабатывает команды, пока они поступают от пользователя.
     * Надо переопределить для конкретной реализации
     */
    public abstract void run();

    /**
     * Иполняется при попытке выхода пользователя из программы (команда exit, ctrl+D)
     */
    public void exit() {
        System.exit(0);
    }
}