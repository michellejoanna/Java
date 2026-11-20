# Assignment 4 – CO4 Design Report

## Task 2 – Handler pseudocode
REFRESH DOCTORS: read selected department; obtain matching doctors; replace doctor combo model; clear old slots; load slots for the selected doctor and date.

BOOK: validate patient, department, doctor, date and slot; check slot availability; show confirmation; persist booking; display reference. On failure, show message and retain form values.

## Task 6 – Layered architecture
RegistrationFrame → RegistrationService → MySQL database. The frame owns form controls, JTable, dialogs and status. The service owns validation and JDBC queries. Register uses a SwingWorker INSERT and relies on a unique database constraint for duplicate prevention. Filter uses a SwingWorker SELECT with LIMIT/OFFSET.

## Complexity
For N total registrations and page size L, fetching all rows transfers O(N) data and retains O(N) rows in the client. Pagination transfers O(L) per page and retains O(L) rows. Indexes help query performance, but exact runtime depends on the database plan and filter selectivity.
