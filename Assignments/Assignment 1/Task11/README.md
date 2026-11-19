# Task 11 – Implement, Test and Defend the Design (5 Marks)

## Implementation scope
The integrated application should combine Vehicle subclasses, ParkingFacility, ParkingTicket, unique ticket numbering, active-ticket lookup, pass validation, fee calculation and separate EV charging fees. The completed source programs for the two required code-completion exercises are in Task03 and Task08. The integrated application should be kept in a separate source folder so each exercise remains independently runnable.

## Test plan
| ID | Scenario | Expected result |
|---|---|---|
| TC01 | Two-wheeler enters | First suitable slot and unique ticket |
| TC02 | Car enters | Slot allocated |
| TC03 | Last free slot | Allocation succeeds |
| TC04 | Facility full | No allocation; clear message |
| TC05 | Zero-minute stay | Zero duration fee, per chosen policy |
| TC06 | Exactly 60 minutes | One started-hour slab |
| TC07 | 61 minutes | Two started-hour slabs |
| TC08 | Unknown vehicle category | Rejected |
| TC09 | Duplicate active registration | Rejected |
| TC10 | Invalid floor/slot | Release rejected |
| TC11 | Valid student/staff monthly pass | Parking fee waived |
| TC12 | Visitor without pass | Full fee charged |

**Actual results:** Fill this column only after running the program on your machine. Do not claim execution or screenshots until they have been captured.

## Hand calculation example
Using demonstration rates of Rs.10/hour for a two-wheeler and Rs.30/hour for a car, with started-hour billing: a bike parked 120 minutes costs Rs.20; a car parked 180 minutes costs Rs.90; an eligible monthly-pass holder pays Rs.0. Total collection = Rs.110, excluding any separately recorded EV charging fees.

## Design evaluation
Encapsulation protects fields; inheritance models vehicle categories; overriding supplies category-specific rates; polymorphism enables a Vehicle reference to invoke subtype behaviour; abstraction separates common vehicle state and optional capabilities. A limitation of a simple in-memory array design is that data is lost when the process stops and concurrent access needs synchronization.

## Alternative active-ticket structures
- Array: search O(n), insert O(1) if free capacity is available, space O(n).
- ArrayList: search O(n), append O(1) amortized, space O(n).
- HashMap keyed by normalized registration number: lookup/insert O(1) average, space O(n); worst-case lookup can degrade depending on hashing. Use it for fast exit processing and enforce unique active registrations.
