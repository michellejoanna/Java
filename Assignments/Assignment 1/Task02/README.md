# Task 2 – Choose the Data Representation (3 Marks)

## Data types
| Field | Suggested Java type | Reason |
|---|---|---|
| Floor / slot index | `int` | Non-negative index. |
| Registration number | `String` | Alphanumeric identifier. |
| Ticket number | `long` | Monotonically increasing identifier. |
| Entry / exit time | `LocalDateTime` | Date and time with minute/second precision. |
| Duration | `long` minutes | Avoids truncating fractional hours. |
| Fee / rate | `BigDecimal` for money | Avoids binary floating-point rounding; double may be used in a simple classroom demo. |
| Occupancy | `boolean` or null slot reference | Represents free/occupied state. |
| Vehicle category | `enum` | Restricts values to known categories. |

## Floors and slots
Use a jagged array because floors may have different capacities:
```java
String[][] slots = new String[3][];
slots[0] = new String[20];
slots[1] = new String[30];
slots[2] = new String[50];
```
Each row represents one floor; each cell stores a registration number or null.

## Conversion and precision
Integer division truncates: `130 / 60 == 2`, although 130 minutes spans 3 started hours. For started-hour billing use:
```java
long billableHours = (minutes + 59) / 60;
```
Validate that minutes is non-negative before calculating. For exact elapsed hours rather than started-hour slabs, use a duration in minutes and apply the stated pricing policy without an unsafe cast.

## Input
Use `Scanner` for interactive console input or command-line arguments for scripted runs. For dates, parse a clearly specified format into `LocalDateTime`.

## Alternative representations
- 1D array: index = floor offset + slot; with unequal floor capacities, maintain prefix offsets and translate each floor/slot to a global index.
- ArrayList<String[]>: each list element is one floor array; it naturally supports different row lengths.
- Jagged array: direct `slots[floor][slot]` access.

Locating a known floor/slot is O(1) in all three with direct indexing; searching all slots is O(N), where N is total capacity. Space is O(N). A 1D array requires offset bookkeeping; jagged arrays and ArrayList rows make unequal floor sizes more explicit.
