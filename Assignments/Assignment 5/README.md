# Assignment 5 – CO5: Data Persistence Engineering (Files and Databases)

Programming in Java | 50 marks | One week

This assignment is divided into nine task folders. Each task has a README with design, analysis, run instructions and a Java source implementation.

| Folder | Handout task | Coverage |
|---|---|---|
| Task01 | A1 – Design a File Format | Text and binary bus-pass records |
| Task02 | A2 – Don't Read What You Don't Need | Buffered I/O and benchmark |
| Task03 | A3 – Data-Access Design Pattern | Generic DAO |
| Task04 | A4 – What Should Be Serialized? | Object graph and transient fields |
| Task05 | A5 – Correctness and Safety | JDBC transaction and injection |
| Task06 | B1 – Implement and Instrument File Store | Add/find/list and measurements |
| Task07 | B2 – Adversarial Input | SQL injection and damaged records |
| Task08 | B3 – Statement vs PreparedStatement vs Batch | JDBC performance and rollback |
| Task09 | B4 – Final Engineering Decision | Migration and DatabaseMetaData |

## Compile and run
Use JDK 17 or later. From each task's src folder run javac FileName.java followed by java ClassName.

For JDBC tasks (Task05, Task07, Task08, Task09), install MySQL Connector/J, create database assignment5 and configure DB_URL, DB_USER and DB_PASSWORD environment variables. Example URL: jdbc:mysql://localhost:3306/assignment5. Add the Connector/J JAR to the classpath. Do not commit database credentials.

## Submission evidence
Run all programs individually. Capture genuine output screenshots, record benchmark results from your own machine, and include input/output files. JDBC programs require a local MySQL server. Add your name, register number, Java version and repository link before submission.