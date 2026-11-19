# Task 12 – Re-evaluate and Adapt the Design (8 Marks)

## Changed requirements
Add e-bikes with a flat charging fee, delivery vans charged at double the car rate during 9 AM–5 PM, visitor one-day passes, and five floors with different slot capacities.

## A. Impact analysis (2)
Affected components:
- Vehicle hierarchy: add EBike and DeliveryVan.
- Charging capability: EBike can implement Chargeable.
- Pricing service: add time-window-aware delivery-van pricing.
- Pass model: add OneDayPass for visitors.
- ParkingFacility: use a jagged array, because each floor can have a different slot count.
- ParkingTicket: retain entry and exit timestamps so pricing can calculate overlap with the 9 AM–5 PM window.

## B. Evaluate original design (2)
The original OOP model remains a sound foundation but must be modified. New vehicle types can be added as subclasses if the superclass is stable. Pricing should be centralized behind PricingRule rather than duplicated in every vehicle. Existing tested Vehicle, Car and TwoWheeler classes can remain unchanged if new behaviour is implemented in new subclasses and strategy classes; ParkingFacility and pricing/pass services may need changes.

## C. Adaptation (2)
**Decision: MODIFY.** Retain inheritance and interfaces, add EBike, DeliveryVan, OneDayPass and a PricingRule abstraction. Use a jagged array:
```java
int[] capacities = {20, 25, 30, 35, 40};
Vehicle[][] floors = new Vehicle[capacities.length][];
for (int f = 0; f < capacities.length; f++)
    floors[f] = new Vehicle[capacities[f]];
```

A one-day pass stores its valid LocalDate and returns true only for that date. The visitor's fee waiver, if any, must be explicitly defined by the policy; a pass being valid does not automatically imply free parking.

Delivery-van pricing must calculate the actual overlap of the parking interval with the daily 09:00–17:00 window. For each date touched by the parking interval, intersect [entry, exit) with [date 09:00, date 17:00); charge the overlap at twice the car rate and the remaining duration at the normal van rate. This handles stays crossing 9 AM, 5 PM and midnight. The exact rounding/slab policy must be stated before converting durations to billable hours.

## D. Evidence-based defence (2)
Record:
1. Number of existing classes changed (report this after implementation; aim to leave stable tested vehicle classes unchanged).
2. Time and files needed to add a further category.
3. Tests for e-bike flat fee, van overlap before/during/after the window, valid/expired one-day pass and unequal floor capacities.
4. Trade-offs: strategy abstraction adds classes but reduces conditional logic; jagged arrays fit unequal floors but require nested traversal; a database-backed sequence is needed for globally unique tickets across multiple application instances.

**Do not claim tests passed until actual execution results and screenshots are added.**
