# Task 5 – Race Conditions in a Cinema Seat-Booking System (6 Marks)

## What is implemented
- A deliberately unsafe check-then-book operation with a latch-controlled demonstration that lets multiple threads pass the availability check before any updates the seat.
- A synchronized method implementation.
- A synchronized block implementation that locks the shared seat array only around the critical section.
- 100 booking threads competing for 10 seats.

## Run
```bash
javac SeatBooking.java
java SeatBooking
```

## Expected safe result
Both synchronized versions must print `Seats sold = 10`. The unsafe controlled race can count more than one sale for the same seat because several threads can observe `booked[seat] == false` before any sets it to true.

## Analysis
The critical section is the combined availability check, booked flag update and booking counter increment. Synchronizing only the write, but not the check, is insufficient. A synchronized instance method locks the SeatBooking object. The synchronized block here locks the booked array. All competing callers must use the same lock.

- Whole method synchronization is simple and protects all instance state accessed in the method.
- A small synchronized block can reduce lock scope, but unrelated code should remain outside it.
- For 20 screens, use one independent booking object/lock per screen so different screens do not block one another. A single global static lock would unnecessarily serialize all screens.
- Static synchronization is appropriate only if a static resource is shared across every instance and must be protected by the Class object lock.
- A per-seat AtomicBoolean with compareAndSet(false,true) is another option; it uses O(s) flags for s seats and makes each reservation attempt atomic.

**Measurement evidence:** Run the implementations repeatedly and record your actual timings and counts. The unsafe test is intentionally synchronized with latches to expose the interleaving; it is not a real-world booking implementation.