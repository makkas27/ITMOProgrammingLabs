package ru.ifmo.se.kastricyn.lab5.data;

import ru.ifmo.se.kastricyn.lab5.LocalDateAdapter;
import ru.ifmo.se.kastricyn.lab5.utility.Console;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс представляющий элемент коллекции
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket implements Comparable<Ticket> {
    public static final int PRICE_MIN = 1;
    public static final double DISCOUNT_MIN = 0;
    public static final double DISCOUNT_MAX = 100;

    private static long nextId = 1;

    @XmlAttribute
    private final Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0
    private double discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    private TicketType type; //Поле может быть null
    private Venue venue; //Поле не может быть null

    /**
     * @return true, если все поля заданы верно, иначе могут быть @exception
     */
    public boolean isExisting() {
        if (id < 1)
            throw new IllegalStateException();
        setName(name).setCoordinates(coordinates).setPrice(price).setDiscount(discount).setType(type).setVenue(venue);
        coordinates.isExisting();
        venue.isExisting();
        if (creationDate == null)
            throw new NullPointerException();
        return true;
    }

    private void initial(String name, Coordinates coordinates, Integer price, double discount, TicketType type, Venue venue) {
        setName(name).setCoordinates(coordinates).setPrice(price).setDiscount(discount).setType(type).setVenue(venue);
    }

    public Ticket(String name, Coordinates coordinates, Integer price, double discount, TicketType type, Venue venue) {
        initial(name, coordinates, price, discount, type, venue);
        id = nextId++;
        creationDate = LocalDate.now();
    }

    public Ticket(long id, String name, Coordinates coordinates, LocalDate creationDate, Integer price, double discount, TicketType type, Venue venue) {
        initial(name, coordinates, price, discount, type, venue);
        if (creationDate == null)
            throw new NullPointerException("Поле creationDate не может быть null");
        this.creationDate = creationDate;
        if (id < nextId)
            throw new IllegalArgumentException("Значение поля id должно быть больше id предыдущего объекта этого типа/минимально возможного значения ("
                    + (nextId - 1) + ")");
        this.id = id;
        nextId = id + 1;
    }

    /**
     * конструктор по умолчанию, для работы JAXB
     */
    private Ticket() {
        id = nextId++;
        creationDate = LocalDate.now();
    }

    public Ticket(Console console) {
        id = nextId++;
        creationDate = LocalDate.now();
        if (console.isInteractiveMode()) {
            System.out.println("Создаём объект типа \"Ticket\":");
            System.out.println("Поле имя:");
        }
        setName(console.getString());

        setCoordinates(new Coordinates(console));

        if (console.isInteractiveMode())
            System.out.println("Возвращаемся к объекту типа \"Ticket\":\n Поле price:");
        setPrice(console.getInt(PRICE_MIN, Integer.MAX_VALUE, true));

        if (console.isInteractiveMode())
            System.out.println("Поле discount:");

        setDiscount(console.getDouble(DISCOUNT_MIN, DISCOUNT_MAX));

        if (console.isInteractiveMode())
            System.out.println("Поле type:");

        setType(console.getEnumConstant(TicketType.class, true));

        setVenue(new Venue(console));

        if (console.isInteractiveMode()) {
            System.out.println("Возвращаемся к объекту типа \"Ticket\":\n создан объект: " + this);
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(ticket.discount, discount) == 0 && name.equals(ticket.name) && coordinates.equals(ticket.coordinates) && creationDate.equals(ticket.creationDate) && price.equals(ticket.price) && type == ticket.type && venue.equals(ticket.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, price, discount, type, venue);
    }

    @Override
    public int compareTo(Ticket o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", type=" + type +
                ", venue=" + venue +
                '}';
    }


//All gets

    public static long getNextAvailableId() {
        return nextId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getDiscount() {
        return discount;
    }

    public Integer getPrice() {
        return price;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TicketType getType() {
        return type;
    }

    public Venue getVenue() {
        return venue;
    }

    //All sets

    public Ticket setName(String name) {
        if (name == null)
            throw new NullPointerException("Поле name не может быть null");
        if (name.equals(""))
            throw new IllegalArgumentException("name не может быть пустым");
        this.name = name;
        return this;
    }

    public Ticket setCoordinates(Coordinates coordinates) {
        if (coordinates == null)
            throw new NullPointerException("поле coordinates не может быть null");
        this.coordinates = coordinates;
        return this;
    }

    public Ticket setDiscount(double discount) {
        if (discount < DISCOUNT_MIN || discount > DISCOUNT_MAX)
            throw new IllegalArgumentException("Значение поля discount должно быть больше " + DISCOUNT_MIN + ", Максимальное значение поля: " + DISCOUNT_MAX);
        this.discount = discount;
        return this;
    }

    public Ticket setPrice(Integer price) {
        if (price == null)
            this.price = null;
        else if (price < PRICE_MIN)
            throw new IllegalArgumentException("Значение поля price должно быть больше" + (PRICE_MIN - 1));
        this.price = price;
        return this;
    }

    public Ticket setType(TicketType type) {
        this.type = type;
        return this;
    }

    public Ticket setVenue(Venue venue) {
        if (venue == null)
            throw new NullPointerException("поле venue не может быть null");
        this.venue = venue;
        return this;
    }
}

