package ru.ifmo.se.kastricyn.lab6.server.commands;

import ru.ifmo.se.kastricyn.lab6.lib.data.Ticket;
import ru.ifmo.se.kastricyn.lab6.lib.data.Venue;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Console;
import ru.ifmo.se.kastricyn.lab6.server.ServerAbstractCommand;
import ru.ifmo.se.kastricyn.lab6.server.TicketCollection;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Команда вывести элементы, значение поля venue которых равно заданному
 */
public class FilterByVenue extends ServerAbstractCommand {


    public FilterByVenue() {
        super("filter_by_venue", "вывести элементы, значение поля venue которых равно заданному");

        setNeedArgumentType(TicketCollection.class, Venue.class);
    }

    @Override
    public void execute(String... args) {
        TicketCollection ticketCollection = objArgs.getTicketCollection();
        Venue venue = objArgs.getVenue();


        Supplier<Stream<Ticket>> v = () -> StreamSupport.stream(ticketCollection.spliterator(), true).filter(x -> x.getVenue().equals(venue));
        if (v.get().findAny().isPresent()) {
            answer = Console.getStringFromStream("Элементы имеющие введённый venue:", v.get());

        } else
            answer = "В коллекции нет элементов, имеющие введённый venue.";
    }
}
