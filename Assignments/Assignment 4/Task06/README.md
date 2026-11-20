# Task 6 – Campus Event Registration Desktop Application (13 marks)

Users: students and event coordinators. UI: registration form, event filter, JTable, paging controls and File menu. Data: student ID, name, email, event ID and timestamp. Validate fields; enforce duplicate prevention with a database UNIQUE(student_id,event_id) constraint; use prepared statements; perform JDBC off the EDT with SwingWorker.

Class responsibilities: RegistrationFrame owns widgets and feedback; RegistrationService validates and executes JDBC; MySQL stores events and registrations. Register flow: validate → background INSERT → report success/duplicate/error → refresh page. Filter flow: background SELECT with WHERE, LIMIT and OFFSET → update JTable in done().

Loading all 100,000 rows uses O(N) transfer and memory. Pagination with page size L transfers O(L) rows and keeps UI memory O(L); indexes improve filtering. Query time depends on indexes and selectivity.

Setup: execute sql/schema.sql in MySQL. Add MySQL Connector/J. Set DB_URL (default jdbc:mysql://localhost:3306/campus_events), DB_USER and DB_PASSWORD environment variables. Compile with Connector/J on classpath, then run RegistrationFrame. Capture normal, invalid and duplicate registration evidence.
