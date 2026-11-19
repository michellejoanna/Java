# Task 2 – ArrayList Engineering Choice

**Problem:** Playlist operations compared across ArrayList, LinkedList and ArrayDeque.

## Implementation
The benchmark tests indexed access, front insertion/removal, full iteration and deque operations at 1,000, 10,000 and 50,000 elements. It prints measured times in milliseconds.

## Run
```bash
javac CollectionBenchmark.java
java CollectionBenchmark
```

## Complexity comparison
| Operation | ArrayList | LinkedList | ArrayDeque |
|---|---:|---:|---:|
| Add at end | Amortized O(1) | O(1) | Amortized O(1) |
| Insert at front | O(n) | O(1) | O(1) |
| Insert middle (finding position included) | O(n) | O(n) | O(n) / not indexed |
| Get k-th | O(1) | O(n) | Not supported |
| Iterate all | O(n) | O(n) | O(n) |
| Space per element | Array reference slot; spare capacity possible | Node with item and two links | Circular-array slot |

ArrayList is a sensible playlist choice when direct random access and iteration are frequent and edits are mostly at the end. Prefer LinkedList only when operations at known node positions dominate; locating a middle position is still linear. Use ArrayDeque for the Up Next queue because it efficiently adds/removes at both ends. A plain array requires manual shifting for front/middle changes.

**Evidence reminder:** Timings are machine/JVM dependent. Run several times for stronger evidence; this program gives a first comparison, not a universal performance guarantee.