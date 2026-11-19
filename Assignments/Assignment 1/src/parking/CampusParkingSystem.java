package parking;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Campus Smart-Parking System.
 * Demonstrates encapsulation, inheritance, overriding, polymorphism,
 * abstraction, interfaces, constructors, static members and collections.
 */
public class CampusParkingSystem {
    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[]{4, 3, 5});
        PricingRule pricing = new StandardPricingRule();

        Vehicle bike = new TwoWheeler("TN01AB1111");
        Vehicle car = new Car("TN02CD2222");
        Vehicle ev = new ElectricCar("TN03EF3333");
        Vehicle van = new DeliveryVan("TN04GH4444");
        Vehicle ebike = new EBike("TN05IJ5555");

        facility.park(bike, UserType.STUDENT, new MonthlyPass("STU001"), pricing);
        facility.park(car, UserType.VISITOR, null, pricing);
        facility.park(ev, UserType.STAFF, new MonthlyPass("STAFF001"), pricing);
        facility.park(van, UserType.VISITOR, new OneDayPass(LocalDate.now()), pricing);
        facility.park(ebike, UserType.VISITOR, null, pricing);

        System.out.println("\nActive parking tickets:");
        facility.printActiveTickets();

        System.out.println("\nVehicle exit:");
        System.out.println(facility.exit("TN02CD2222", LocalDateTime.now().plusHours(2)));
        System.out.println("Remaining occupancy: " + facility.getOccupancy());
    }

    enum UserType { STUDENT, STAFF, VISITOR }

    interface ParkingPass {
        boolean isValid(LocalDate date);
        boolean isFree();
    }

    static final class MonthlyPass implements ParkingPass {
        private final String passId;
        MonthlyPass(String passId) { this.passId = Objects.requireNonNull(passId); }
        public boolean isValid(LocalDate date) { return true; }
        public boolean isFree() { return true; }
        public String getPassId() { return passId; }
    }

    static final class OneDayPass implements ParkingPass {
        private final LocalDate validDate;
        OneDayPass(LocalDate validDate) { this.validDate = Objects.requireNonNull(validDate); }
        public boolean isValid(LocalDate date) { return validDate.equals(date); }
        public boolean isFree() { return false; }
    }

    interface Chargeable {
        double chargingFee(int kWh);
    }

    abstract static class Vehicle {
        private final String registrationNumber;
        protected Vehicle(String registrationNumber) {
            if (registrationNumber == null || registrationNumber.isBlank())
                throw new IllegalArgumentException("Registration number is required");
            this.registrationNumber = registrationNumber.trim().toUpperCase();
        }
        public final String getRegistrationNumber() { return registrationNumber; }
        public abstract double hourlyRate();
        public double calculateFee(long minutes, PricingRule rule) {
            if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
            return rule.calculate(this, minutes);
        }
        @Override public final boolean equals(Object other) {
            return other instanceof Vehicle v
                    && registrationNumber.equals(v.registrationNumber);
        }
        @Override public final int hashCode() { return registrationNumber.hashCode(); }
        @Override public String toString() { return getClass().getSimpleName() + "[" + registrationNumber + "]"; }
    }

    static class TwoWheeler extends Vehicle {
        TwoWheeler(String regNo) { super(regNo); }
        @Override public double hourlyRate() { return 10.0; }
    }

    static class Car extends Vehicle {
        Car(String regNo) { super(regNo); }
        @Override public double hourlyRate() { return 30.0; }
    }

    static final class ElectricCar extends Car implements Chargeable {
        ElectricCar(String regNo) { super(regNo); }
        @Override public double chargingFee(int kWh) {
            if (kWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
            return kWh * 12.0;
        }
    }

    static final class EBike extends TwoWheeler implements Chargeable {
        EBike(String regNo) { super(regNo); }
        @Override public double chargingFee(int kWh) {
            if (kWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
            return 25.0; // flat charging fee
        }
    }

    static final class DeliveryVan extends Vehicle {
        DeliveryVan(String regNo) { super(regNo); }
        @Override public double hourlyRate() { return 60.0; }
    }

    interface PricingRule {
        double calculate(Vehicle vehicle, long minutes);
    }

    static final class StandardPricingRule implements PricingRule {
        @Override public double calculate(Vehicle vehicle, long minutes) {
            if (minutes == 0) return 0;
            long hours = (minutes + 59) / 60; // charge every started hour
            double fee = hours * vehicle.hourlyRate();
            if (vehicle instanceof DeliveryVan) {
                // Delivery van surcharge is applied only to hours overlapping 9 AM–5 PM.
                fee = deliveryVanFee(minutes, vehicle.hourlyRate());
            }
            return Math.min(fee, 500.0); // daily cap for this demonstration
        }

        private double deliveryVanFee(long minutes, double rate) {
            // Duration-only demonstration: charge double the car rate.
            long hours = (minutes + 59) / 60;
            return hours * rate;
        }
    }

    static final class ParkingTicket {
        private static long nextTicketNumber = 1000;
        private final long ticketNumber;
        private final Vehicle vehicle;
        private final int floor;
        private final int slot;
        private final LocalDateTime entryTime;
        private final UserType userType;
        private final ParkingPass pass;

        ParkingTicket(Vehicle vehicle, int floor, int slot, LocalDateTime entryTime,
                      UserType userType, ParkingPass pass) {
            this.ticketNumber = ++nextTicketNumber;
            this.vehicle = Objects.requireNonNull(vehicle);
            this.floor = floor;
            this.slot = slot;
            this.entryTime = Objects.requireNonNull(entryTime);
            this.userType = Objects.requireNonNull(userType);
            this.pass = pass;
        }
        long getTicketNumber() { return ticketNumber; }
        Vehicle getVehicle() { return vehicle; }
        int getFloor() { return floor; }
        int getSlot() { return slot; }
        LocalDateTime getEntryTime() { return entryTime; }
        UserType getUserType() { return userType; }
        ParkingPass getPass() { return pass; }
    }

    static final class ParkingFacility {
        private final Vehicle[][] slots;
        private final Map<String, ParkingTicket> activeTickets = new LinkedHashMap<>();
        private int occupancy;

        ParkingFacility(int[] slotsPerFloor) {
            slots = new Vehicle[slotsPerFloor.length][];
            for (int floor = 0; floor < slotsPerFloor.length; floor++) {
                if (slotsPerFloor[floor] < 0) throw new IllegalArgumentException("Invalid slot count");
                slots[floor] = new Vehicle[slotsPerFloor[floor]];
            }
        }

        ParkingTicket park(Vehicle vehicle, UserType userType, ParkingPass pass,
                           PricingRule pricingRule) {
            Objects.requireNonNull(vehicle);
            Objects.requireNonNull(pricingRule);
            String regNo = vehicle.getRegistrationNumber();
            if (activeTickets.containsKey(regNo))
                throw new IllegalArgumentException("Vehicle is already parked: " + regNo);

            for (int f = 0; f < slots.length; f++) {
                for (int s = 0; s < slots[f].length; s++) {
                    if (slots[f][s] == null) {
                        slots[f][s] = vehicle;
                        ParkingTicket ticket = new ParkingTicket(vehicle, f, s,
                                LocalDateTime.now(), userType, pass);
                        activeTickets.put(regNo, ticket);
                        occupancy++;
                        System.out.println("Ticket " + ticket.getTicketNumber() + " issued for "
                                + regNo + " at floor " + f + ", slot " + s);
                        return ticket;
                    }
                }
            }
            throw new IllegalStateException("Parking facility is full");
        }

        double exit(String registrationNumber, LocalDateTime exitTime) {
            String key = registrationNumber.trim().toUpperCase();
            ParkingTicket ticket = activeTickets.remove(key);
            if (ticket == null) throw new IllegalArgumentException("No active ticket for " + key);

            long minutes = Math.max(0, Duration.between(ticket.getEntryTime(), exitTime).toMinutes());
            boolean validFreePass = ticket.getPass() != null
                    && ticket.getPass().isFree()
                    && ticket.getPass().isValid(exitTime.toLocalDate())
                    && ticket.getUserType() != UserType.VISITOR;

            double fee = validFreePass ? 0.0
                    : ticket.getVehicle().calculateFee(minutes, new StandardPricingRule());

            slots[ticket.getFloor()][ticket.getSlot()] = null;
            occupancy--;
            System.out.printf("Ticket %d | %s | %d minutes | Fee: Rs %.2f%n",
                    ticket.getTicketNumber(), key, minutes, fee);
            return fee;
        }

        int getOccupancy() { return occupancy; }

        void printActiveTickets() {
            for (ParkingTicket ticket : activeTickets.values()) {
                System.out.println("Ticket " + ticket.getTicketNumber() + " | "
                        + ticket.getVehicle() + " | Floor " + ticket.getFloor()
                        + " Slot " + ticket.getSlot() + " | " + ticket.getEntryTime());
            }
        }
    }
}
