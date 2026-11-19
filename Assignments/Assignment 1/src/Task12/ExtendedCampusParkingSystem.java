import java.time.*;
import java.util.*;

interface Pass {
    boolean isValid(LocalDate date);
}
class EBike {\n    private static final double FLAT_PARKING_FEE = 25.0;\n    double parkingFee() { return FLAT_PARKING_FEE; }\n}\nclass OneDayVisitorPass implements Pass {
    private final LocalDate validDate;
    OneDayVisitorPass(LocalDate validDate) { this.validDate = Objects.requireNonNull(validDate); }
    @Override public boolean isValid(LocalDate date) { return validDate.equals(date); }
}
interface PricingRule {
    double fee(LocalDateTime entry, LocalDateTime exit, double hourlyRate);
}
class StandardPricingRule implements PricingRule {
    @Override public double fee(LocalDateTime entry, LocalDateTime exit, double hourlyRate) {
        long minutes = Math.max(0, Duration.between(entry, exit).toMinutes());
        return ((minutes + 59) / 60) * hourlyRate;
    }
}
class DeliveryVanPricingRule implements PricingRule {
    @Override public double fee(LocalDateTime entry, LocalDateTime exit, double carRate) {
        if (exit.isBefore(entry)) throw new IllegalArgumentException("Exit precedes entry");
        double total = 0;
        LocalDate day = entry.toLocalDate();
        while (!day.isAfter(exit.toLocalDate())) {
            LocalDateTime windowStart = day.atTime(9, 0);
            LocalDateTime windowEnd = day.atTime(17, 0);
            LocalDateTime overlapStart = entry.isAfter(windowStart) ? entry : windowStart;
            LocalDateTime overlapEnd = exit.isBefore(windowEnd) ? exit : windowEnd;
            long overlapMinutes = Math.max(0, Duration.between(overlapStart, overlapEnd).toMinutes());
            long allMinutes = Math.max(0, Duration.between(
                    entry.isAfter(day.atStartOfDay()) ? entry : day.atStartOfDay(),
                    exit.isBefore(day.plusDays(1).atStartOfDay()) ? exit : day.plusDays(1).atStartOfDay()
            ).toMinutes());
            long normalMinutes = Math.max(0, allMinutes - overlapMinutes);
            total += ((overlapMinutes + 59) / 60) * (2 * carRate);
            total += ((normalMinutes + 59) / 60) * carRate;
            day = day.plusDays(1);
        }
        return total;
    }
}
class ExtendedParkingFacility {
    private final String[][] slots;
    ExtendedParkingFacility(int[] capacities) {
        slots = new String[capacities.length][];
        for (int f = 0; f < capacities.length; f++) slots[f] = new String[capacities[f]];
    }
    int[] allocate(String regNo) {
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (slots[f][s] == null) { slots[f][s] = regNo; return new int[]{f, s}; }
        return null;
    }
}
public class ExtendedCampusParkingSystem {
    public static void main(String[] args) {
        ExtendedParkingFacility facility = new ExtendedParkingFacility(new int[]{20, 25, 30, 35, 40});
        System.out.println("E-bike slot: " + Arrays.toString(facility.allocate("TN-E-BIKE-01")));

        Pass visitorPass = new OneDayVisitorPass(LocalDate.now());
        System.out.println("Visitor pass valid today: " + visitorPass.isValid(LocalDate.now()));

        LocalDateTime in = LocalDate.now().atTime(8, 0);
        LocalDateTime out = LocalDate.now().atTime(18, 0);
        PricingRule vanRule = new DeliveryVanPricingRule();
        System.out.println("Delivery van fee for 8 AM–6 PM: Rs " + vanRule.fee(in, out, 30.0));
        System.out.println("E-bike flat parking fee: Rs " + new EBike().parkingFee());
    }
}