abstract class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo; System.out.println("Vehicle constructor"); }
    String getRegNo() { return regNo; }
    abstract double ratePerHour();
}
class Car extends Vehicle {
    Car(String regNo) { super(regNo); System.out.println("Car constructor"); }
    @Override double ratePerHour() { return 30; }
}
class ElectricCar extends Car {
    ElectricCar(String regNo) { super(regNo); System.out.println("ElectricCar constructor"); }
}
public class InheritanceDemo {
    public static void main(String[] args) {
        ElectricCar ev = new ElectricCar("TN03EV3333");
        System.out.println(ev.getRegNo() + " rate Rs " + ev.ratePerHour());
    }
}