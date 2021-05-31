package ru.ifmo.se.kastricyn.lab7.server.commands;

import ru.ifmo.se.kastricyn.lab7.server.TicketCollection;

/**
 * Команда вывести первый элемент коллекции
 */
public class Head extends ServerAbstractCommand {

    public Head() {
        super("head", "вывести первый элемент коллекции");

        setNeedArgumentType(TicketCollection.class);
    }

    @Override
    public void execute(String... args) {
        TicketCollection ticketCollection = objArgs.getTicketCollection();
        answer = ticketCollection.isEmpty() ? "Коллекция пуста" : ticketCollection.peekFirst().toString();
    }
}