# Task 1 – Building Text Under Load

**Problem:** Railway e-ticket generation using String, StringBuilder and StringBuffer.

## Implementation
- Compares repeated `+`, `StringBuilder` and `StringBuffer`.
- Runs three contrasting workloads and averages five measurements using `System.nanoTime()`.
- Reports timing in milliseconds. Object counts and memory are discussed as estimates because the JVM does not expose exact per-method String allocation counts through this simple program.

## Run
```bash
javac StringBuildingBenchmark.java
java StringBuildingBenchmark
```

## Analysis
- String is immutable; repeated concatenation in a loop creates intermediate objects and can copy an increasing amount of text. For n similarly sized fields, repeated concatenation can approach O(n²) character copying.
- StringBuilder appends to a mutable buffer and is approximately O(n) in total appended characters, with amortized growth.
- StringBuffer has synchronized methods and similar append complexity, with synchronization overhead.
- Use StringBuilder for each thread's private ticket. For a shared log, prefer a thread-safe logging mechanism or synchronized access; StringBuffer is an option only when a shared mutable buffer is genuinely required.
- StringJoiner/String.join are suitable for joining known collections of fields; String.format is convenient but generally not the fastest option for tight loops.

**Evidence reminder:** Run on your machine, preserve the actual output and repeat measurements. Timings vary with JVM warm-up, hardware and garbage collection; do not report sample output as measured evidence.