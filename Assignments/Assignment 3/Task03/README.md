# Task 3 – Resource Safety, Debugging and Robust Code

**Marks:** 7 | **Concepts:** manual close, try-with-resources, suppressed exceptions, debugging.

## Run
```bash
javac ResourceSafetyDemo.java
java ResourceSafetyDemo
```

The program creates three temporary store files. The second contains a deliberately corrupt record. Both implementations stop on that file and log its name. Manual close is placed in nested finally blocks; the try-with-resources version closes resources automatically in reverse declaration order (reader before writer).

## Why leaks occur
If close() is called only after the read loop, an exception jumps past it. A finally block or try-with-resources is required. The assignment's debugging evidence should include a breakpoint at the read and close statements, the corrupt input, and the observed open-handle count. This sample uses real files and reports the temporary directory so the generated input can be inspected.

## Suppressed exceptions
If the try body throws one exception and close() also throws, the body exception remains primary and the close failure is attached as a suppressed exception. Inspect with getSuppressed(). This preserves both failures.

## When try-with-resources is not enough
It closes resources reliably, but does not validate input, make writes atomic, recover from corrupt files, prevent partial output, or guarantee disk space. A robust production report can write to a temporary file and rename only after success, validate each record, and continue/report individual file failures.

## Complexity
For total input size N, reading/writing is O(N) time. With one reader plus one writer open at a time, maximum handles are O(1), independent of f store files. If all f readers are opened together, handles become O(f). No-closing can leak up to O(f) handles. Manual finally and try-with-resources both provide O(1) handle usage in this sequential design.