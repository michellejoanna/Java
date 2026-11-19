class Ticket {
    private final long ticketId;
    private final String registrationNumber;
    private final String category;

    Ticket() { this(1001L, "UNKNOWN", "VISITOR"); }
    Ticket(long ticketId, String registrationNumber) {
        this(ticketId, registrationNumber, "VISITOR");
    }
    Ticket(long ticketId, String registrationNumber, String category) {
        if (ticketId <= 0 || registrationNumber == null || registrationNumber.isBlank())
            throw new IllegalArgumentException("Invalid ticket details");
        this.ticketId = ticketId;
        this.registrationNumber = registrationNumber;
        this.category = category;
    }
    Ticket(Ticket other) {
        this(other.ticketId + 1, other.registrationNumber, other.category);
    }
    public String toString() {
        return ticketId + " " + registrationNumber + " " + category;
    }
}
public class ConstructorDemo {
    public static void main(String[] args) {
        Ticket a = new Ticket();
        Ticket b = new Ticket(1002, "TN01AB1234");
        Ticket copy = new Ticket(b);
        System.out.println(a);
        System.out.println(b);
        System.out.println("Copy receives a new ID: " + copy);
    }
}