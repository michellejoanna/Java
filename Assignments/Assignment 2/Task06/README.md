# Task 6 – Priority, Order and Fairness in a Queue

PriorityQueue comparator chooses lower severity numbers first and arrival counter ascending for ties.

## Run
```bash
javac Triage.java
java Triage
```

Expected output:
```text
Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)
```
A plain FIFO queue cannot select a later-arriving critical patient first. PriorityQueue offer/poll are O(log n), peek O(1), storage O(n). Printing the queue itself does not guarantee sorted treatment order; poll a copy to display that order. A monotonically increasing arrival counter preserves FIFO among equal severities. Lower-severity patients can starve during continuous emergencies; a clinically approved maximum-wait promotion or reserved service policy can reduce starvation. Sorted LinkedList insertion is O(n), removal O(1); five FIFO queues have O(1) admission/removal and constant five-level scanning.