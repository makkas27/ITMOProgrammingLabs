package ru.ifmo.se.kastricyn.lab6.server.commands;

import ru.ifmo.se.kastricyn.lab6.lib.data.Ticket;
import ru.ifmo.se.kastricyn.lab6.server.ServerAbstractCommand;
import ru.ifmo.se.kastricyn.lab6.server.TicketCollection;

import java.util.Iterator;

/**
 * Команда удалить из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends ServerAbstractCommand {

    public RemoveLower() {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        setNeedArgumentType(TicketCollection.class, Ticket.class);
    }


    @Override
    public void execute(String... args) {
        TicketCollection ticketCollection = objArgs.getTicketCollection();
        Ticket minTicket = objArgs.getTicket();

        Iterator<Ticket> iterator = ticketCollection.iterator();
        Ticket t;
        int i = 0;
        while (iterator.hasNext()) {
            t = iterator.next();
            if (t.compareTo(minTicket) < 0) {
                iterator.remove();
                i++;
            }
        }
        answer = "Из коллекции удалено " + i + " объектов.";
        if (i > 0)
            ticketCollection.setSaved(false);
    }
}
