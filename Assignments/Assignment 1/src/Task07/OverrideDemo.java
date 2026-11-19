import java.util.Objects;

abstract class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo.toUpperCase(); }
    abstract double ratePerHour();
    public String getRegNo() { return regNo; }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
    @Override public boolean equals(Object obj) {
        return obj instanceof Vehicle other && regNo.equals(other.regNo);
    }
    @Override public int hashCode() { return Objects.hash(regNo); }
}
class Bike extends Vehicle {
    Bike(String regNo) { super(regNo); }
    @Override double ratePerHour() { return 10; }
}
class Sedan extends Vehicle {
    Sedan(String regNo) { super(regNo); }
    @Override double ratePerHour() { return 30; }
}
public class OverrideDemo {
    public static void main(String[] args) {
        final int minutes = 130;
        Vehicle bike = new Bike("tn01aa1111");
        Vehicle car = new Sedan("TN02BB2222");
        System.out.println(bike + " fee Rs " + (((minutes + 59) / 60) * bike.ratePerHour()));
        System.out.println(car + " fee Rs " + (((minutes + 59) / 60) * car.ratePerHour()));
        System.out.println("Equal registration: " + bike.equals(car));
    }
}