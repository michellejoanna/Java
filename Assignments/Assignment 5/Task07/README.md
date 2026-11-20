# Task B2 – Engineer an Adversarial Input

## Test cases
SQL injection: use ' OR '1'='1' -- as a pass ID against a local test database. A concatenated Statement query may return unintended rows. PreparedStatement binds the whole string as a value.

Damaged file: include a row with the wrong number of fields and another with a non-numeric route or amount. The file store should reject the malformed row, report it and continue reading later valid rows.

## Why regex escaping is weaker
A regular expression cannot safely model all SQL dialects, encodings, quoting contexts and future query changes. PreparedStatement separates executable SQL from parameter data.

## Complexity
Binding is O(k) in input length; indexed lookup is database-index dependent, typically O(log n). File validation is O(n).

## Run
Compile and run AdversarialInputDemo.java. Configure local test MySQL for the live unsafe-vs-safe comparison. Never test against production.