# Task 8 – Apply Polymorphism (4 Marks)

## Compile-time polymorphism
Overload a method with different parameter lists, for example `calculateFee(int minutes)` and `calculateFee(LocalDateTime in, LocalDateTime out)`. The compiler selects the overload using the declared argument types and count.

## Runtime polymorphism
A `Vehicle[]` can hold Car, TwoWheeler and ElectricCar objects through upcasting. When `v.calculateFee(130)` is called, Java selects the overridden implementation based on the actual object's class. Example trace: declared reference type = Vehicle; actual object = Car; executed implementation = Car's ratePerHour() invoked through Vehicle.calculateFee().

## Completed code
See `FeeDemo.java` in this folder. It completes all TODOs in the supplied assignment code and preserves the expected output.

## Why polymorphism
It avoids long if/else or switch chains and lets a new subtype supply its own behaviour. A conditional chain can require up to k checks for k types; dynamic dispatch selects the implementation without application-level type branching. Explain that JVM dispatch implementation details are runtime-dependent; O(1) is the usual algorithmic model for virtual dispatch.
