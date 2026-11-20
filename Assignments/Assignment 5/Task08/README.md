# Task B3 – Statement vs PreparedStatement vs Batch

The program compares Statement with individual insert strings, PreparedStatement executed once per row, and PreparedStatement.addBatch()/executeBatch() in chunks of 1,000 rows. It tests 10, 1,000, 10,000 and 100,000 rows; the extra 10-row run checks small-data correctness.

The batch path disables auto-commit, commits only after all batches succeed, rolls back on error and restores the original setting. The rollback test inserts a negative amount halfway through. The database schema must enforce amount >= 0 so the invalid row fails.

Individual Statement calls have high network/parse overhead. PreparedStatement reuses a parameterized statement. Batching reduces round trips and uses O(batch size) memory. LOAD DATA may be faster for large trusted files but needs strict staging and validation.

## Run
Requires MySQL and Connector/J. Configure DB_URL, DB_USER and DB_PASSWORD. Run BatchInsertBenchmark and record measured results for all data sizes.