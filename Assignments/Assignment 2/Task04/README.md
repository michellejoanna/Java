# Task 4 – Choosing a Map for Pharmacy Inventory

Compares HashMap, TreeMap and LinkedHashMap for 100, 1,000 and 10,000 medicine codes. It times 100,000 lookups and iteration.

## Run
```bash
javac PharmacyInventory.java
java PharmacyInventory
```

| Map | put/get | Iteration |
|---|---|---|
| HashMap | Expected O(1) | No guaranteed order |
| TreeMap | O(log n) | Sorted key order |
| LinkedHashMap | Expected O(1) | Insertion order |

TreeMap supplies the code-order report; LinkedHashMap supplies insertion order. HashMap needs sorting for ordered reports. Hashtable is a legacy synchronized collection; modern code generally uses HashMap with explicit concurrency design. A sorted array with binarySearch has O(log n) lookup, O(n) insertion due to shifting, and O(n) report traversal. Timings are environment-dependent; run repeatedly and preserve your own measurements.