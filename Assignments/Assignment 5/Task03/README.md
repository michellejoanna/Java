# Task A3 – Discover the Data-Access Design Pattern

## Repeated pattern
StudentDB, CourseDB and MarksDB repeat: obtain connection, prepare/build SQL, bind values, execute query/update, map ResultSet to objects, close resources and handle SQL errors.

## Generic DAO design
Define Dao<T,K> with insert, findById, findAll, update and delete. Implement it separately for Student, Course and Marks. Each implementation owns table-specific SQL and row mapping; services depend on the interface.

## Benefits and complexity
Connection/resource handling and CRUD structure are centralized. A renamed column is changed in one mapper/DAO rather than scattered UI/service code. An indexed database lookup is typically O(log n); listing is O(n). The in-memory sample map has expected O(1) lookup and O(n) listing.

## Run
Compile and run GenericDaoPatternDemo.java.