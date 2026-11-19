# Assignment 1 – Campus Smart-Parking System

**Course:** Programming in Java  
**CO1:** Java fundamentals and object-oriented programming

## Source files
- `src/Task03/ParkingFacility.java` – completed jagged-array allocation/release program for Task 3.
- `src/Task08/FeeDemo.java` – completed inheritance, overriding, polymorphism and Chargeable interface demo for Tasks 6–9.
- `src/parking/CampusParkingSystem.java` – integrated smart-parking example with vehicle types, passes, ticket generation, jagged floors, active-ticket lookup and pricing abstraction.

## Requirements
Java 17 or later. No external libraries are required.

## Compile and run
Run from this folder:

```bash
javac -d out src/Task03/ParkingFacility.java
java -cp out ParkingFacility

javac -d out src/Task08/FeeDemo.java
java -cp out FeeDemo

javac -d out src/parking/CampusParkingSystem.java
java -cp out parking.CampusParkingSystem
```

## Task 3 expected output
```text
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]
```

## Task 8 expected output
```text
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true
```

## Design notes
- A jagged array represents floors with different slot counts.
- Active tickets are indexed by registration number for efficient exit lookup.
- A static counter generates unique ticket numbers.
- Vehicle subclasses override their hourly rates; charging is modelled as an interface capability.
- Pricing is separated behind a `PricingRule` interface to make future changes easier to isolate.

**Scope note:** The integrated program is an educational baseline. The PDF's delivery-van rule is specifically time-window based (9 AM–5 PM); the current sample uses a simplified double-rate calculation and should be extended to calculate exact overlap from entry/exit timestamps before treating that requirement as fully implemented. The charging interface is demonstrated separately and can be integrated into final ticket billing.

## Submission reminder
The PDF also requires written analysis for Tasks 1–12, UML, pseudocode, complexity comparisons, test evidence and a reasoned design defence. Add actual execution screenshots and complete the written report before final submission.
