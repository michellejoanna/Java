interface Chargeable {
    double chargingFee(int units);
    default void showChargingMessage() { System.out.println("Charging is available"); }
    static double standardRate() { return 12.0; }
}
abstract class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo; }
    String getRegNo() { return regNo; }
    abstract double parkingFee(int minutes);
}
class ElectricCar extends Vehicle implements Chargeable {
    ElectricCar(String regNo) { super(regNo); }
    @Override double parkingFee(int minutes) { return ((minutes + 59) / 60) * 30.0; }
    @Override public double chargingFee(int units) {
        if (units < 0) throw new IllegalArgumentException("Units cannot be negative");
        return units * Chargeable.standardRate();
    }
}
public class AbstractionDemo {
    public static void main(String[] args) {
        ElectricCar ev = new ElectricCar("TN03EV3333");
        ev.showChargingMessage();
        System.out.println("Parking fee Rs " + ev.parkingFee(90));
        System.out.println("Charging fee Rs " + ev.chargingFee(5));
    }
}