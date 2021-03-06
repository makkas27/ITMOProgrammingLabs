package ru.ifmo.se.kastricyn.lab6.server.commands;

import ru.ifmo.se.kastricyn.lab6.lib.data.Ticket;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Console;
import ru.ifmo.se.kastricyn.lab6.server.ServerAbstractCommand;
import ru.ifmo.se.kastricyn.lab6.server.TicketCollection;

import java.util.Comparator;
import java.util.stream.StreamSupport;

/**
 * Команда вывести значения поля venue всех элементов в порядке убывания
 */
public class PrintFieldDescendingVenue extends ServerAbstractCommand {

    public PrintFieldDescendingVenue() {
        super("print_field_descending_venue", "вывести значения поля venue всех элементов в порядке убывания");
        setNeedArgumentType(TicketCollection.class);
    }


    @Override
    public void execute(String... args) {
        TicketCollection ticketCollection = objArgs.getTicketCollection();

        answer = Console.getStringFromStream("Поля venue всех элементов в порядке убывания:\n",
                StreamSupport.stream(ticketCollection.spliterator(), true)
                        .map(Ticket::getVenue)
                        .distinct()
                        .sorted(Comparator.reverseOrder()));
    }
}
