# Task 4 – Design Classes, Objects and Constructors (4 Marks)

## Class design
| Class | Example fields | Main methods |
|---|---|---|
| Vehicle | private regNo, category | getRegNo(), calculateFee() |
| Car / TwoWheeler / ElectricCar | category-specific rate/capability | overridden rate, chargingFee() |
| ParkingTicket | ticketNo, vehicle, entryTime, slot | close(), duration(), details() |
| ParkingFacility | jagged slots, active tickets | allocate(), release(), findTicket() |
| User / ParkingPass | identity, category, validity | isValid() |
| PricingRule | pricing policy | calculateFee() |

## Constructors
- Default constructor: appropriate only when a meaningful default state exists.
- Parameterized constructor: initializes required fields and validates them.
- Overloaded constructors: allow supported creation variants.
- `this`: distinguishes fields from parameters and delegates to another constructor using `this(...)`.
- Copy constructor: useful for copying value data, but a copied active ticket must receive a new unique ticket number; never duplicate an active ticket identity.

```java
class ParkingTicket {
    private final long ticketNo;
    private final String regNo;

    ParkingTicket() {
        this(1001L, "UNKNOWN");
    }

    ParkingTicket(long ticketNo, String regNo) {
        if (ticketNo <= 0 || regNo == null || regNo.isBlank())
            throw new IllegalArgumentException("Invalid ticket data");
        this.ticketNo = ticketNo;
        this.regNo = regNo;
    }
}
```

## Memory
A local reference variable is typically held in a stack frame; the object created with `new` resides on the heap. Passing an object passes its reference value. An object becomes eligible for garbage collection when it is no longer reachable; eligibility does not mean immediate collection.

## Alternative creation patterns
Default constructor + setters can temporarily create incomplete objects and requires validation at every setter. A static factory can validate and return a fully initialized object. Creating n tickets takes O(n) total time and O(n) total heap space in all patterns for fixed-size fields; factory/constructor validation adds O(1) per ticket. Parameterized constructors best enforce valid state at creation.
