# Task 5 – Generic Sports Leaderboard

Implements the assignment comparator: points descending, goal difference descending, then team name A–Z. The generic Leaderboard accepts a Comparator<? super T>, returns a sorted copy, and accepts subtype lists through List<? extends T>.

## Run
```bash
javac LeaderboardDemo.java
java LeaderboardDemo
```

Expected first ranking:
```text
[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]
```
Bisons precedes Panthers because their points and goal difference tie, so alphabetical order decides. A Comparator is reusable for teams, players and colleges; Comparable is appropriate when a type has one natural order. One sort costs O(n log n) time; copying the list costs O(n) extra space. Three stable sorting passes can require three O(n log n) sorts.