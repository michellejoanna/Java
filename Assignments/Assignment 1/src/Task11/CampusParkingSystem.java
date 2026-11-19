import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

enum VehicleType { BIKE, CAR, ELECTRIC_CAR }
enum UserType { STUDENT, STAFF, VISITOR }

class ParkingVehicle {
    final String regNo;
    final VehicleType type;
    ParkingVehicle(String regNo, VehicleType type) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration required");
        this.regNo = regNo.toUpperCase();
        this.type = Objects.requireNonNull(type);
    }
    double rate() {
        return switch (type) {
            case BIKE -> 10.0;
            case CAR, ELECTRIC_CAR -> 30.0;
        };
    }
}
class ParkingTicket {
    final long id;
    final ParkingVehicle vehicle;
    final int floor, slot;
    final LocalDateTime entry;
    ParkingTicket(long id, ParkingVehicle v, int floor, int slot) {
        this.id = id; this.vehicle = v; this.floor = floor; this.slot = slot;
        this.entry = LocalDateTime.now();
    }
}
class CampusParkingSystem {
    private final String[][] slots;
    private final Map<String, ParkingTicket> active = new HashMap<>();
    private long nextTicket = 1000;

    CampusParkingSystem(int[] capacities) {
        slots = new String[capacities.length][];
        for (int i = 0; i < capacities.length; i++) slots[i] = new String[capacities[i]];
    }
    ParkingTicket enter(ParkingVehicle v) {
        if (active.containsKey(v.regNo)) throw new IllegalArgumentException("Duplicate active registration");
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (slots[f][s] == null) {
                    slots[f][s] = v.regNo;
                    ParkingTicket t = new ParkingTicket(++nextTicket, v, f, s);
                    active.put(v.regNo, t);
                    return t;
                }
        throw new IllegalStateException("Parking facility is full");
    }
    double exit(String regNo, UserType userType, boolean validMonthlyPass, int chargingUnits) {
        ParkingTicket t = active.remove(regNo.toUpperCase());
        if (t == null) throw new IllegalArgumentException("No active ticket");
        slots[t.floor][t.slot] = null;
        long minutes = Math.max(0, Duration.between(t.entry, LocalDateTime.now()).toMinutes());
        long hours = (minutes + 59) / 60;
        double parkingFee = (userType != UserType.VISITOR && validMonthlyPass) ? 0 : hours * t.vehicle.rate();
        double chargingFee = t.vehicle.type == VehicleType.ELECTRIC_CAR ? chargingUnits * 12.0 : 0;
        return parkingFee + chargingFee;
    }
}
public class CampusParkingSystemDemo {
    public static void main(String[] args) {
        CampusParkingSystem system = new CampusParkingSystem(new int[]{2, 2, 1});
        ParkingTicket t = system.enter(new ParkingVehicle("TN01AB1234", VehicleType.CAR));
        System.out.println("Ticket " + t.id + " allocated floor " + t.floor + ", slot " + t.slot);
        System.out.println("Fee on exit (approximate short stay): Rs " +
                system.exit("TN01AB1234", UserType.VISITOR, false, 0));
    }
}