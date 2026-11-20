# Task A1 – Design a File Format

## Requirement
Store transport-office bus-pass records and support add, find by pass ID, and list.

## Record layouts
Delimited text: passId, registerNo, name, routeNo, boardingStop, amount. Escape backslash, comma and newline so values do not corrupt the record layout. Binary: write the same fields in a fixed documented order using writeUTF, writeInt and writeDouble. The reader must use exactly the same order and types.

## Pseudocode
Add: validate record, encode fields, open append writer, write one record and newline, close.
Find: open buffered reader, read each line, parse and validate, compare pass ID, return match, close.
List: read line by line, parse valid rows, display them and report malformed rows without stopping.

Use BufferedReader and BufferedWriter for text. Use DataOutputStream over BufferedOutputStream for typed binary values.

## Complexity and alternative
Append is O(1) amortized per record. Find and list are O(n) time with O(1) extra scan memory. Binary is compact but less portable and harder to inspect; text is easier to debug and migrate. An indexed database improves repeated lookups.

## Run
Compile and run BusPassFileFormatDesign.java.