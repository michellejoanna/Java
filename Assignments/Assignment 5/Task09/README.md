# Task B4 – Final Engineering Decision

## B4.1 Migration
BusPassMigration reads CSV records, validates each row, binds accepted values through PreparedStatement batches and commits the migration transaction. Rejected lines/reasons are written to migration_failures.txt. A database transaction failure triggers rollback.

## B4.2 Metadata
DatabaseMetaData reports database product/version, JDBC driver/version and available tables. This is useful when the application moves to another server because it can detect environment differences.

## B4.3 Production decision
For 12 campuses with concurrent counters, indexed searches, integrity rules and safe updates, use a central relational database (MySQL), not independent text or serialized files. Files remain useful for import/export and offline staging. A database provides transactions, constraints, indexes and controlled concurrent access.

Separate serialized files per campus isolate data but complicate global search/reporting and concurrent counters. A central database with campus IDs and indexed keys is simpler to manage consistently.

## Run
Create database assignment5, configure MySQL and Connector/J, then run BusPassMigration with a CSV path. Inspect migration_failures.txt and metadata output. Save genuine screenshots.