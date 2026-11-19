# Task 6 – Design the Inheritance Hierarchy (4 Marks)

## Vehicle hierarchy
```text
                 Vehicle (abstract)
                 /       |       \
        TwoWheeler      Car    (other types)
                         |
                    ElectricCar
```
TwoWheeler and Car use hierarchical inheritance from Vehicle. ElectricCar extends Car, creating multilevel inheritance. Vehicle stores common registration data; subclasses provide their own hourly rate or capabilities. Users can similarly be modelled as User with Student, Staff and Visitor subclasses if their behaviours differ.

## Use of super
A subclass constructor calls `super(regNo)` to initialize parent state. The parent constructor executes before the child constructor. For ElectricCar: Vehicle constructor → Car constructor → ElectricCar constructor (the implicit/explicit super chain).

## Example
```java
abstract class Vehicle {
    private final String regNo;
    protected Vehicle(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    abstract double ratePerHour();
}
class Car extends Vehicle {
    Car(String regNo) { super(regNo); }
    @Override double ratePerHour() { return 30; }
}
class ElectricCar extends Car {
    ElectricCar(String regNo) { super(regNo); }
}
```

## Alternative: composition
A single Vehicle can contain a VehicleType object holding the rate. Adding a type becomes adding/configuring a VehicleType rather than creating a subclass. Fee calculation remains O(1) in both designs. Inheritance may avoid one separate type object per vehicle; composition may add a reference and shared type object but improves runtime flexibility. Use inheritance for genuine is-a relationships and composition for configurable type/rate behaviour.
