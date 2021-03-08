package ru.ifmo.se.kastricyn.commands;

import ru.ifmo.se.kastricyn.TicketCollection;
import ru.ifmo.se.kastricyn.ticket.Ticket;

import java.util.Scanner;

public class Add extends AbstractCommand {
    private Scanner in;
    private boolean shouldPrintHints;
    private TicketCollection ticketCollection;

    public Add(TicketCollection ticketCollection, Scanner in, boolean shouldPrintHints) {
        super("add", "add {element} \n - добавить новый элемент в коллекцию");
        this.in = in;
        this.shouldPrintHints = shouldPrintHints;
        this.ticketCollection = ticketCollection;
    }

    @Override
    public void execute(String ... args) {
        Ticket t = Ticket.getTicket(in, shouldPrintHints);
        ticketCollection.add(t);
        System.out.println("В коллекцю добавлен объект " + t);
    }
}
