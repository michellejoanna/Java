# Task 5 – Apply Static Members and the Object Life Cycle (3 Marks)

## Static members
A ticket sequence can be shared by all ticket instances:
```java
class TicketNumber {
    private static long next = 1000;
    static synchronized long nextTicketNumber() {
        return ++next;
    }
}
```
A static method is appropriate for class-wide utilities that do not depend on one object's state. A static block can initialize class-wide configuration once when the class is initialized. A total occupancy count may be static only if it genuinely represents all facilities and is updated safely; otherwise it belongs to each facility.

## Ticket lifecycle and object passing
A ticket is created on entry, referenced by the active-ticket collection while parked, closed on exit, and removed from active tracking. It becomes eligible for garbage collection only when no reachable references remain. Methods can accept a Vehicle/Ticket object and return a newly created ParkingTicket or allocation result.

## Alternative: instance counter
With k ParkingFacility instances, an instance counter consumes O(k) counter storage and can generate duplicate numbers across north/south gates (each might issue 1001). A static shared counter uses O(1) counter storage for k facilities and n tickets, while the n ticket objects themselves use O(n). In a multi-process deployment, use a database sequence or globally unique ID; a Java static variable is shared only within one JVM. Synchronization/atomic increment is needed for concurrent threads.
