# Assignment 5 – Implementation and Experimental Report

## Objective
Design, implement and evaluate Java persistence using delimited and binary files, buffered I/O, object serialization, JDBC, transactions, PreparedStatement and batch processing.

## Design summary
- Text files are portable and inspectable; use explicit escaping and per-line validation.
- Binary streams store typed fields compactly but require a stable field order and version strategy.
- Buffered I/O reduces repeated low-level reads while preserving O(n) scan complexity.
- A generic DAO separates persistence operations from business logic.
- Serialization persists stable domain state; passwords and live resources are transient.
- JDBC transactions protect multi-step wallet updates and batch imports.
- PreparedStatement prevents SQL injection by binding values separately from SQL syntax.

## Experimental results
Fill this table with measurements from your own machine and MySQL instance. Do not invent results.

| Experiment | Input size | Method | Time (ms) | Notes |
|---|---:|---|---:|---|
| Attendance scan | 1 MB | byte-at-a-time | — | |
| Attendance scan | 1 MB | buffered | — | |
| Attendance scan | 10 MB | byte-at-a-time | — | |
| Attendance scan | 10 MB | buffered | — | |
| Attendance scan | 50 MB | byte-at-a-time | — | |
| Attendance scan | 50 MB | buffered | — | |
| Pass lookup | 1,000 / 10,000 / 100,000 | sequential scan | — | |
| JDBC insert | 1,000 / 10,000 / 100,000 | Statement, PreparedStatement, batch | — | |

## Final decision
Use MySQL as the authoritative shared store for the 12-campus office. Retain validated text exports for exchange and recovery. Apply PreparedStatement for user-supplied values, transactions for multi-row operations, indexes for pass-ID lookup and constraints for invalid data prevention.

## Submission checklist
- [ ] Add student name and register number.
- [ ] Compile and execute every Java program individually.
- [ ] Run JDBC programs with local MySQL and Connector/J.
- [ ] Capture genuine terminal screenshots.
- [ ] Fill the measurement table with actual observations.
- [ ] Include input/output files and migration failure report.
- [ ] Commit changes progressively and submit the GitHub link.