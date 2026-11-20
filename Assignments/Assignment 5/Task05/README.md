# Task A5 – Design for Correctness and Safety

## A5.1 Transaction design
For the ₹500 transfer from hostel wallet to canteen wallet, both updates must succeed or neither may persist.

Pseudocode: save old autoCommit; set autoCommit false; debit ₹500 only if balance is sufficient; require exactly one row updated; credit ₹500; require exactly one row updated; commit. On error rollback. Finally restore old autoCommit.

Two independent auto-committed updates are unsafe: a crash after debit but before credit loses money. A transaction adds logging/locking overhead but provides atomicity and consistency.

## A5.2 Injection analysis
Never concatenate user input into SQL. Use PreparedStatement placeholders and bind values. An input such as ' OR '1'='1' -- can change a concatenated query; as a bound parameter it is treated as literal data. Regex escaping is weaker because SQL dialects, encodings and quoting contexts vary.

## Complexity
Two indexed wallet updates are approximately O(log n) each, plus transaction overhead.

## Run
Requires local MySQL database assignment5 and Connector/J. Configure DB_URL, DB_USER and DB_PASSWORD, then run WalletTransactionDemo.java.