# Task 3 – Design the Control Logic (4 Marks)

## Allocation pseudocode
```text
START
IF vehicle number is null/blank THEN report invalid input and STOP
FOR each floor f
  FOR each slot s in floor f
    IF slot[f][s] is empty THEN
      store vehicle number in slot[f][s]
      RETURN {f, s}
    END IF
  END FOR
END FOR
REPORT no suitable slot available
RETURN null
STOP
```

## Fee pseudocode
```text
START
VALIDATE duration >= 0
IF eligible student/staff monthly pass is valid THEN fee = 0
ELSE
  determine billable duration using the chosen fee-slab rule
  select applicable vehicle rate
  calculate parking fee
  IF daily maximum applies AND fee exceeds maximum THEN fee = maximum
END IF
ADD separate EV charging fee when applicable
RETURN fee
STOP
```

## Completed ParkingFacility.java
See `ParkingFacility.java` in this folder. It implements first-free-slot allocation and validates floor/slot indices before releasing a slot.

## Complexity
For F floors and total S slots, scanning allocation is O(S) worst case and O(1) extra space. Release by known floor/slot is O(1). Maintaining a stack/queue of free positions can make allocation and release O(1) amortized, but requires O(S) extra storage to maintain free positions; the slot array itself still requires O(S). A free-count per floor can skip full floors.
