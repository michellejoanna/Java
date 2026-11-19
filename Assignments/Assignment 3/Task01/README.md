# Task 1 – Handling Failure in a Bank ATM Simulation

**Marks:** 6 | **Concepts:** Throwable hierarchy, checked/unchecked exceptions, multi-catch, finally.

## Run
```bash
javac ATMWithdrawal.java
java ATMWithdrawal
```

## Relevant hierarchy
```text
Object
 └─ Throwable
    ├─ Error (e.g. OutOfMemoryError – generally not caught by application code)
    └─ Exception
       ├─ IOException (checked)
       │  └─ FileNotFoundException (checked)
       ├─ RuntimeException (unchecked)
       │  ├─ NumberFormatException
       │  └─ IllegalArgumentException
       ├─ InvalidAmountException (checked)
       └─ InsufficientFundsException (checked)
```

## Failure and catch table
| Failure | Type | Checked? | Handling |
|---|---|---|---|
| Letters instead of number | NumberFormatException | No | Input multi-catch |
| Non-positive account | IllegalArgumentException | No | Input multi-catch |
| Negative/zero withdrawal | InvalidAmountException | Yes | Transaction multi-catch |
| Amount exceeds balance | InsufficientFundsException | Yes | Transaction multi-catch |
| Missing card reader file | FileNotFoundException / IOException | Yes | Device catch |

Narrower exceptions must be caught before broader supertypes. A catch(Exception) before catch(IOException) makes the latter unreachable and causes a compile-time error. finally executes on normal completion and exception propagation, making it suitable for cleanup; it cannot guarantee execution after JVM termination, process crash or power loss.

## Test evidence
The main method exercises six paths. Record the printed catch label and confirm the finally line appears for each handled case. To capture an uncaught stack trace, temporarily remove the relevant catch and run the failing input; each stack frame identifies a method and source line in the call path.

## One catch(Exception)?
It can be acceptable at a top-level application boundary for logging and preventing a user-facing crash, but it loses precise recovery decisions and can hide programming defects. Catch expected failures specifically.

## Alternative: validation/error codes
Use Scanner.hasNextInt()/hasNextDouble() and return a result enum or error code before processing. Both approaches take O(1) work per withdrawal and O(1) auxiliary space. Exception construction/capture adds stack-trace allocation and is relatively expensive; error-code checks avoid that cost but can spread validation branches through callers.