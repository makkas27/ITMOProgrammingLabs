package ru.ifmo.se.kastricyn.commands;

import ru.ifmo.se.kastricyn.TicketCollection;
import ru.ifmo.se.kastricyn.ticket.Ticket;

import java.util.Iterator;

public class PrintAscending extends AbstractCommand {
    private TicketCollection ticketCollection;

    public PrintAscending(TicketCollection ticketCollection) {
        super("print_ascending", "print_ascending \n - вывести элементы коллекции в порядке возрастания");
        this.ticketCollection = ticketCollection;
    }

    @Override
    public void execute(String... args) {
        ticketCollection.sort();
        Iterator<Ticket> iterator = ticketCollection.iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }
}