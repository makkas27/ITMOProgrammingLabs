package ru.ifmo.se.kastricyn.lab6.server.commands;

import ru.ifmo.se.kastricyn.lab6.server.ServerAbstractCommand;
import ru.ifmo.se.kastricyn.lab6.server.TicketCollection;

/**
 * Команда вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
 */
public class Info extends ServerAbstractCommand {

    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");

        setNeedArgumentType(TicketCollection.class);
    }


    @Override
    public void execute(String... args) {
        TicketCollection ticketCollection = objArgs.getTicketCollection();

        answer = "Информация о коллекции:\n"
                + "тип: Ticket" + "\n"
                + "дата инициализации: " + ticketCollection.getInitDate() + "\n"
                + "кол-во элементов: " + ticketCollection.size() + "\n";
    }
}
