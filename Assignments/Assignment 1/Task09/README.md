# Task 9 – Introduce Abstraction (4 Marks)

## Abstract class
Vehicle should be abstract because it has shared state (registration number), a constructor and common fee logic, while each concrete vehicle supplies its rate through an abstract method.

## Interfaces
`Chargeable` models an optional charging capability; `Payable` could model payment behaviour. A class may implement multiple interfaces, such as ElectricCar implementing Chargeable and Payable, while extending one superclass.

```java
interface Chargeable {
    double chargingFee(int kWh);
    default void chargingInfo() {
        System.out.println("Charging facility available");
    }
    static double standardRate() { return 12.0; }
}
```

Default methods provide a reusable capability implementation; static interface methods provide related utility behaviour.

## Alternative reversal
If Vehicle became an interface, it could not have constructors or ordinary per-instance fields (interface fields are constants); shared state initialization would need to move into classes or composition. If Chargeable became an abstract class, a vehicle already extending Vehicle could not also extend Chargeable because Java allows one direct superclass. Interfaces support multiple implemented contracts. Method dispatch cost is broadly similar for ordinary calls; per-object memory depends on the concrete fields, not merely whether a contract is an interface or abstract class.
