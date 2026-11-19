# Task 7 – Diagnose, Redesign and Validate

**Selected limitation:** Q1's repeated String concatenation when generating a ticket with many fields.

## A. Diagnose the bottleneck
Repeated concatenation creates immutable intermediate Strings and recopies the previous text. The cumulative copying grows rapidly with the output size.

## B. Design objective
Produce identical ticket text while reducing repeated copying and temporary String creation.

## C. Revised design
Original: `text = text + field` in a loop. Redesigned: allocate a StringBuilder (with estimated capacity), append each field, and convert once at the end.

## D–E. Predict and validate
The program warms up both implementations, then compares them at 100, 500, 1,000 and 2,000 fields using the same input and seven measured runs.

## Run
```bash
javac RedesignValidation.java
java RedesignValidation
```

## F. Complexity and trade-offs
If n fields have similar lengths, repeated concatenation may copy O(n²) total characters. StringBuilder appends in O(L) amortized time for total output length L, with O(L) buffer space. Pre-sizing can reduce reallocations but may reserve unused capacity. Simple concatenation remains readable and reasonable for a small fixed number of fields.

**Evidence:** Preserve the actual output from your computer and discuss measurement noise. The program does not invent timing results; execution on your system is required. The selected redesign is one defensible challenge based on Q1 and should be supported with your own observations.