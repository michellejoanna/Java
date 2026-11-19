# Task 6 – Deadlock and Inter-Thread Communication (7 Marks)

## Part A – Wallet transfers
The transfer method locks both wallet objects in ascending wallet-id order. Every thread follows the same global ordering, preventing circular wait and therefore preventing deadlock. Validate positive amounts and sufficient balance while both locks are held.

A deadlock demonstration can be built by having two threads acquire the same two locks in opposite order. Do not leave such a demonstration running as non-daemon threads; it can keep the JVM alive. The delivered wallet implementation uses the safe lock-ordering design.

## Part B – Producer / consumer token counter
Completes both assignment TODOs:
- `put`: waits in a while-loop while the bounded buffer is full, inserts at the circular `in` index, increments count, and calls `notifyAll()`.
- `take`: waits in a while-loop while empty, removes at the circular `out` index, decrements count, calls `notifyAll()`, and returns the token.

The buffer capacity is exactly five. Every operation is synchronized, so count stays between 0 and 5. The main program uses one cook and two student consumers.

## Run
```bash
javac TokenCounter.java
java TokenCounter
```
The final line must be:
```text
Sum of tokens collected: 210 (expected 210)
```

## Key concepts
- `wait()` releases the object's monitor and suspends until notified; `sleep()` pauses but does not release a monitor.
- `wait()` belongs inside synchronized code and a while-loop because of spurious wakeups and because another thread may consume the condition before the awakened thread reacquires the lock.
- `notifyAll()` wakes all waiters to re-check their conditions; it is safer where producers and consumers wait on the same monitor.
- An `ArrayBlockingQueue<Integer>(5)` is a simpler alternative with built-in bounded blocking operations.
- Lock ordering prevents the circular-wait condition. A timed `tryLock` design can also avoid waiting forever, but must handle timeout and retry/rollback correctly.

Complexity: each put/take is O(1), and the buffer uses O(5)=O(1) space. A transfer is O(1) work plus lock waiting under contention.