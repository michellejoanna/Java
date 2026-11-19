# Task 5 – Race Conditions in a Cinema Seat-Booking System (6 Marks)

The completed TODO is a synchronized method. The availability check, booked flag update and bookingsMade increment are one atomic critical section.

Run:
```bash
javac SeatBooking.java
java SeatBooking
```
Expected synchronized result: `Seats sold: 10`.

The unsafe controlled example uses CountDownLatch so ten threads can all observe one seat as free before proceeding to mark it booked. This exposes the check-then-act race. A synchronized method locks the SeatBooking instance; a synchronized block can lock only the shared state but all callers must use the same lock. Static synchronization is needed only for static shared state and locks the Class object.

For a 20-screen cinema, keep separate booking state/locks per screen so independent screens can progress concurrently. AtomicBoolean[] with compareAndSet(false,true) is another per-seat solution and uses O(s) flags for s seats. Record actual run timings locally; no timings are fabricated here.