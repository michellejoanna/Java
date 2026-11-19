import java.time.LocalDateTime;
import java.util.*;

class VehicleModel {
    private final String registrationNumber;
    private final String type;
    VehicleModel(String registrationNumber, String type) {
        if (registrationNumber == null || registrationNumber.isBlank())
            throw new IllegalArgumentException("Registration number required");
        this.registrationNumber = registrationNumber;
        this.type = type;
    }
    String getRegistrationNumber() { return registrationNumber; }
    String getType() { return type; }
}
class TicketModel {
    private static long nextId = 1000;
    private final long ticketNumber;
    private final VehicleModel vehicle;
    private final LocalDateTime entryTime;
    TicketModel(VehicleModel vehicle) {
        this.ticketNumber = ++nextId;
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now();
    }
    public String toString() {
        return "Ticket " + ticketNumber + " | " + vehicle.getRegistrationNumber()
                + " | " + vehicle.getType() + " | " + entryTime;
    }
}
public class ParkingModelDemo {
    public static void main(String[] args) {
        VehicleModel car = new VehicleModel("TN01AB1234", "CAR");
        TicketModel ticket = new TicketModel(car);
        System.out.println(ticket);
        System.out.println("Inputs: registration, category, entry time");
        System.out.println("Outputs: allocated slot, ticket and parking fee");
    }
}