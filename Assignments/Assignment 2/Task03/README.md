# Task 3 – Engineer the hashCode–equals Contract

**Problem:** Remove duplicate student attendance scans.

## Implementation
- `StudentBad` deliberately overrides equals but not hashCode to demonstrate the broken contract.
- `Student` uses the registration number in both equals and hashCode.
- Includes repeated-scan and TreeSet registration-number examples.

## Run
```bash
javac Attendance.java
java Attendance
```

## Explanation
HashSet uses hashCode to select a bucket and equals to distinguish matching keys within that bucket. If two objects are equal, they must return the same hash code. Without that rule, equal students may be placed in different buckets and both retained. The broken example's exact size is implementation-dependent and should be recorded from the local run.

The corrected class uses an immutable registration number, preventing mutation of a field involved in hashing after insertion. If a hash-relevant field is changed after insertion, lookup/removal can fail because the object may remain in the bucket selected by its old hash.

Alternative: TreeSet with a comparator on regNo gives O(log n) add/search and sorted order; HashMap<String,Student> keyed by regNo gives expected O(1) lookup/add. Both use O(n) storage for n unique students.