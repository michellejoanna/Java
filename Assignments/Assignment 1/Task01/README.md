# Task 1 – Understand the Problem and Identify the Objects (4 Marks)

## Problem analysis
Build a campus multi-floor parking system for students, staff and visitors. On entry it allocates a suitable free slot, issues a unique ticket and records entry time. On exit it releases the slot and calculates the fee. EV charging is charged separately. Valid monthly-pass holders among students and staff park free; visitors pay the full fee.

## Inputs and outputs
**Inputs:** registration number, vehicle category, user category, entry/exit timestamps, pass details, floor/slot capacities and (for EVs) charging usage.

**Outputs:** allocated floor and slot, unique ticket number, entry/exit details, parking duration, fee, updated availability, and clear errors for invalid/full conditions.

## Candidate classes and responsibilities
| Class | Responsibility / justification |
|---|---|
| Vehicle | Common registration data and vehicle behaviour. |
| TwoWheeler, Car, ElectricCar | Vehicle-specific rates or capabilities. |
| User / Student / Staff / Visitor | Represents parker identity and eligibility. |
| ParkingFacility | Owns floor/slot layout and allocation/release operations. |
| ParkingTicket | Records unique ticket ID, vehicle, slot, entry and exit. |
| ParkingPass | Represents pass validity and eligibility. |
| PricingRule | Separates fee policy from vehicle and facility logic. |
| Chargeable | Optional EV charging capability. |

Nouns become candidate classes/objects; verbs such as allocate, release, validate, calculate, enter and exit become methods.

## Business rules
1. A slot cannot hold two vehicles at once.
2. Ticket numbers must never be reused.
3. A vehicle cannot have two active parking tickets.
4. Registration number and timestamps must be validated.
5. An occupied slot is released only for its active ticket.
6. Valid monthly passes for students/staff waive parking fees.
7. Visitors pay the applicable parking fee; EV charging is accounted for separately.
8. A full facility must report that no suitable slot is available.

## Assumptions
Registration numbers identify vehicles; timestamps use the campus local time zone; started-hour billing is used only if that is the selected fee policy; slot capacities are configured per floor; a charging fee is distinct from the parking fee.

## Alternative: parallel arrays
A String-based design could use `regNo[]`, `type[]` and `entryTime[]` at matching indices. Searching registration number takes O(n) in either parallel arrays or a Vehicle-object array. Parallel arrays use O(n) references per attribute and require every new attribute to be added and kept index-aligned. A Vehicle object groups the fields, so adding an attribute changes the class rather than requiring another parallel array. Both designs use O(n) total storage; object design has per-object header/reference overhead but is safer and easier to maintain.

**Conclusion:** Model vehicles, tickets, users, passes and facility as separate objects because each has a distinct responsibility and lifecycle.
