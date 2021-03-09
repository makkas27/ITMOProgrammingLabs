package ru.ifmo.se.kastricyn.commands;

import ru.ifmo.se.kastricyn.TicketCollection;
import ru.ifmo.se.kastricyn.ticket.Ticket;

import java.util.Scanner;

public class Update extends AbstractCommand{
    private Scanner in;
    private boolean shouldPrintHints;
    private TicketCollection ticketCollection;
    public Update(TicketCollection ticketCollection, Scanner in, boolean shouldPrintHints) {
        super("update", "update id {element} \n - обновить значение элемента коллекции, id которого равен заданному");
        this.in = in;
        this.shouldPrintHints = shouldPrintHints;
        this.ticketCollection = ticketCollection;
    }

    @Override
    public void execute(String... args) {
        //todo more information exceptions
        try {
            long id = Long.parseLong(args[0]);
            Ticket t = Ticket.getTicket(in, shouldPrintHints);
            ticketCollection.update(id, t);
        } catch (Exception e){
            System.out.println("Команда не удалась");
        }
        System.out.println("Объект обнавлён");
    }
}
