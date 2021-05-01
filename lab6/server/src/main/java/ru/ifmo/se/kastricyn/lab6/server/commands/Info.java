package ru.ifmo.se.kastricyn.lab6.server.commands;

import ru.ifmo.se.kastricyn.lab6.server.TicketCollection;

/**
 * Команда вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
 */
public class Info extends AbstractCommand {
    private TicketCollection ticketCollection;

    public Info(TicketCollection ticketCollection) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.ticketCollection = ticketCollection;
    }

    @Override
    public void execute(String... args) {
        String answer = "Информация о коллекции:\n"
                + "тип: Ticket" + "\n" //todo: обобщённая коллекция, получение параметра
                + "дата инициализации: " + ticketCollection.getInitDate() + "\n"
                + "кол-во элементов: " + ticketCollection.size() + "\n";
        System.out.println(answer);
    }
}
