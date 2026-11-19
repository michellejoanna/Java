# Task 4 – Thread Creation and the Thread Life Cycle

**Marks:** 7 | **Concepts:** Thread, Runnable, start, sleep, join, isAlive, priorities and daemon threads.

## Run
```bash
javac ThreadLifecycleDemo.java
java ThreadLifecycleDemo
```

Four stations run concurrently: two extend Thread and two implement Runnable. Their completion order varies. join() ensures main waits until all station threads finish before printing the final total. isAlive() reports whether a thread has started and not yet terminated.

## Life-cycle sketch
```text
NEW --start()--> RUNNABLE --scheduler--> RUNNING
                    ^                     |
                    |                     | sleep()/wait()/join()/lock wait
                    +------ notify/timeout/unlock
RUNNING --run() ends--> TERMINATED
```
Java's formal Thread.State combines RUNNING with RUNNABLE; BLOCKED, WAITING and TIMED_WAITING are separate states.

Calling run() directly is an ordinary method call on the current thread. start() asks the JVM to schedule a new thread and then invokes run() on that thread. Thread priorities are hints, not ordering guarantees. A daemon thread does not keep the JVM alive; if main and all non-daemon threads end, the JVM may exit before daemon work completes.

## Experiment record
Run at least ten times and record station finish order. Change priorities and compare; do not assume the scheduler will honor priority deterministically. To test daemon behaviour, set one station daemon before start and compare with a non-daemon version.

## Executor alternative
A fixed thread pool reuses p worker threads rather than creating a new Thread for each station/task. For n similarly sized scripts, idealized work time is about O(n/p) batches, with scheduling and I/O overhead. Manual thread-per-station uses O(p) thread stacks; executor pool also uses O(p) stacks but controls and reuses them.