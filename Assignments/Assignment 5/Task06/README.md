# Task B1 – Implement and Instrument the File Store

PassStore uses the five-field format in the handout's code skeleton: pass ID, register number, name, route number and amount. It provides add, find and list. Each line is validated independently; a damaged line is reported and skipped, so it does not prevent later valid records from being found.

## Validation
1. Add a normal pass and confirm it appears in list.
2. Find an existing ID and confirm all five values.
3. Find a missing ID and expect no result.
4. Insert wrong-field-count and non-numeric records; confirm they are skipped and later valid records remain readable.

The implementation uses buffered character I/O and one delimited record per line. File search is O(n). FileStoreBenchmark generates 1, 10 and 50 MB attendance logs and measures scans and lookup growth. Run more than once and report median measurements.

## Run
Compile PassStore.java and FileStoreBenchmark.java. Run PassStore, then FileStoreBenchmark. Save actual timing output/screenshots.