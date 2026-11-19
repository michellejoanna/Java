# Task 10 – Enforce Encapsulation through Packages and Access Modifiers (3 Marks)

## Package structure
```text
parking.model       Vehicle, Car, TwoWheeler, ParkingTicket, User
parking.service     ParkingFacility, ParkingManager, PricingRule
parking.interfaces  Chargeable, ParkingPass
parking.app         Main
```

## Access policy
- Fields: `private` to protect state.
- Constructors: `public` for externally creatable model types, or package-private when creation must be controlled.
- Getters/setters: public only where needed; setters validate input.
- `protected`: for subclass extension points such as an abstract rate method.
- Package-private: internal helpers used only within a package.
- Public service methods: expose supported operations, not internal arrays.

## Validation example
```java
public void setDuration(long minutes) {
    if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
    this.durationMinutes = minutes;
}
```
Validate registration number, floor/slot bounds, pass dates and monetary values at the object's boundary.

## Wrapper classes
Use Integer/Long/Double when a collection or nullable value requires an object. Autoboxing converts primitive to wrapper and unboxing reverses it. Avoid `Integer == Integer` for value comparison; use `equals()` or compare primitive values. Unboxing null throws NullPointerException.

## Alternative: public fields
Public fields allow callers to assign invalid values and force validation to be duplicated across call sites. A single validation is O(1), but centralized setters ensure every update follows the same rule; repeated caller-side checks multiply maintenance points and risk inconsistent validation.
