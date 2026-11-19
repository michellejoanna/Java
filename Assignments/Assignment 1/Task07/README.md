# Task 7 – Override and Specialise Behaviour (4 Marks)

## Overriding
Subclasses override `ratePerHour()` or `calculateFee()` because vehicle categories have different pricing. Use `@Override` so the compiler checks the signature. The method name and parameter list must match; return type must be identical or covariant; access cannot be more restrictive; checked exceptions cannot be broader than the parent declaration.

## Example
```java
abstract class Vehicle {
    protected abstract double ratePerHour();
    public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Negative duration");
        long hours = (minutes + 59L) / 60L;
        return hours * ratePerHour();
    }
}
class TwoWheeler extends Vehicle {
    @Override protected double ratePerHour() { return 10; }
}
class Car extends Vehicle {
    @Override protected double ratePerHour() { return 30; }
}
```

## final and Object methods
Use `final` for immutable identifiers (for example, a final registration number), methods whose invariant must not be overridden, and classes that must not be subclassed. Override `toString()` for readable ticket/vehicle details. Override `equals()` and `hashCode()` consistently; if registration number uniquely identifies a vehicle, compare normalized registration numbers and derive hashCode from the same value.

## Alternative: switch
A single fee method with a switch on vehicle type may require up to O(k) comparisons for k types and must be edited when a type is added. Overriding uses dynamic dispatch (conceptually O(1) selection) and localizes the new behaviour in a subclass. Both perform constant arithmetic once the rate is selected.
