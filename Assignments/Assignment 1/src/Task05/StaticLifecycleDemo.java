class ParkingTicket {
    private static long nextTicket = 5000;
    private final long ticketNumber;
    private final String regNo;

    ParkingTicket(String regNo) {
        this.ticketNumber = nextTicketNumber();
        this.regNo = regNo;
    }
    private static synchronized long nextTicketNumber() { return ++nextTicket; }
    public String toString() { return ticketNumber + " : " + regNo; }
}
public class StaticLifecycleDemo {
    public static void main(String[] args) {
        ParkingTicket t1 = new ParkingTicket("TN01AA1111");
        ParkingTicket t2 = new ParkingTicket("TN02BB2222");
        System.out.println(t1);
        System.out.println(t2);
        t1 = null; // eligible for garbage collection if no other reference exists
        System.out.println("Ticket reference cleared; GC timing is not guaranteed.");
    }
}