# Task 2 – User-Defined Exceptions and Propagation in an Online Examination Portal

**Marks:** 7 | **Concepts:** custom checked exceptions, throw/throws, propagation and chaining.

## Run
```bash
javac StorageDemo.java
java StorageDemo
```

## Exception hierarchy
```text
Exception
 └─ ExamException (checked)
    ├─ ExamWindowClosedException
    ├─ StudentNotRegisteredException
    └─ SubmissionStorageException (wraps IOException)
```

Exam-window, registration and storage failures are checked because they are expected business/IO conditions that callers should handle. A programming defect such as an invalid internal state could instead use an unchecked exception.

## Layer propagation
Storage catches low-level IOException and throws SubmissionStorageException with the original cause. ExamService declares throws ExamException and propagates the domain exception. ExamController is the boundary that converts it to a safe student-facing message while logging technical cause details.

## Expected key output
```text
Saved answer01.pdf
Submission accepted.
Error: Could not store answer02.pdf
Cause: Disk quota exceeded
```
The program also tests closed exam window and unregistered student. The full chained stack trace is printed for storage failure. getCause() returns the IOException. If it is not passed to the constructor, the cause is null and the original low-level message/stack trace is lost.

## Checked vs unchecked alternative
If these exceptions extended RuntimeException, throws declarations would not be required and callers would not be forced by the compiler to catch or declare them. Runtime handling is still possible, but compile-time enforcement is lost.

Exception creation and stack-trace capture cost time proportional to stack depth and memory proportional to captured frames. Chaining retains a reference to the cause and its stack trace, increasing retained memory; it preserves diagnostic context.