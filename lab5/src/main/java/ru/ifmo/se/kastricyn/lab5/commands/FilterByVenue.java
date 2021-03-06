package ru.ifmo.se.kastricyn.lab5.commands;

import ru.ifmo.se.kastricyn.lab5.TicketCollection;
import ru.ifmo.se.kastricyn.lab5.data.Ticket;
import ru.ifmo.se.kastricyn.lab5.data.Venue;
import ru.ifmo.se.kastricyn.lab5.utility.Console;

import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Команда вывести элементы, значение поля venue которых равно заданному
 */
public class FilterByVenue extends AbstractCommand {
    private Scanner in;
    private TicketCollection ticketCollection;
    private boolean shouldPrintHints;

    public FilterByVenue(TicketCollection ticketCollection, Scanner in, boolean shouldPrintHints) {
        super("filter_by_venue", "filter_by_venue {venue} \n - вывести элементы, значение поля venue которых равно заданному");
        this.in = in;
        this.shouldPrintHints = shouldPrintHints;
        this.ticketCollection = ticketCollection;
    }

    @Override
    public void execute(String... args) {
        Venue venue = new Venue(new Console(in, shouldPrintHints)); //todo change all commands in, shouldPrintHints -> CONSOLE
        Supplier<Stream<Ticket>> v = () -> StreamSupport.stream(ticketCollection.spliterator(), true).filter(x -> x.getVenue().equals(venue));
        if (v.get().findAny().isPresent()) {
            System.out.println("Элементы имеющие введённый venue:");
            v.get().forEach(System.out::println);
        } else
            System.out.println("В коллекции нет элементов, имеющие введённый venue.");
    }
}
