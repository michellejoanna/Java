# Task 7 – Design a Concurrent, Fault-Tolerant Processor (10 Marks)

## Design
The examination cell processes independent score files in parallel. Each `ScoreTask` implements `Callable<Result>`; an `ExecutorService` fixed thread pool bounds the number of worker threads. Each submitted task returns a success/failure record. The main thread collects every `Future`, stores results in a `ConcurrentLinkedQueue`, and prints one complete summary after all tasks finish.

## Fault handling
- Missing file: captured as `NoSuchFileException` and recorded as a failed result.
- Corrupt/non-numeric/out-of-range score: captured and recorded as a failed result.
- Unexpected task execution exception: caught while retrieving its Future and counted as failure.
- Pool shutdown is in `finally`; graceful termination is attempted before forced shutdown.

A bad file does not cancel other tasks. The summary checks that total results equals the requested file count.

## Run
Requires Java 11+ (uses Files.readString/writeString).
```bash
javac ConcurrentResultProcessor.java
java ConcurrentResultProcessor
```
Optional arguments: number of files and data directory, for example:
```bash
java ConcurrentResultProcessor 10000 generated_scores
```
The program generates a reproducible dataset: every 101st file is missing and every 97th file is corrupt; other files contain valid scores. It runs the same dataset with pools of 1, 2, 4 and 8 threads.

## Measurement and evaluation
Record the four actual `timeMs` values from your computer in a table and graph them. More threads may not always help because file I/O, storage throughput, task overhead, context switching and memory bandwidth can become bottlenecks. Do not invent measurements.

## Complexity and alternatives
For f files, each file is read/validated once: total work is O(total bytes read), with O(f) Future/result bookkeeping in this implementation. The fixed pool uses O(p) worker threads for pool size p, rather than one thread per file. Parallel streams use the common ForkJoinPool and offer less explicit control of pool sizing and per-file failure isolation. Creating one thread per file can require O(f) thread stacks and excessive scheduling overhead. A bounded executor is the chosen design because it limits concurrency and supports per-task Future results.

## Design limitations
The generated test files are local synthetic data; real deployment needs input validation, durable logs, cancellation/timeouts, access controls and careful handling of partial file writes.