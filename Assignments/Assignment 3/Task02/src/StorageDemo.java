import java.io.*;

class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}
class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String m) { super(m); }
}
class StudentNotRegisteredException extends ExamException {
    StudentNotRegisteredException(String m) { super(m); }
}
class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String m, Throwable cause) { super(m, cause); }
}
class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) throw new IOException("Disk quota exceeded");
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}
class ExamService {
    private final Storage storage = new Storage();
    void submit(String file, long sizeKb, boolean windowOpen, boolean registered)
            throws ExamException {
        if (!windowOpen) throw new ExamWindowClosedException("Exam submission window has closed");
        if (!registered) throw new StudentNotRegisteredException("Student is not registered for this course");
        storage.save(file, sizeKb);
    }
}
class ExamController {
    private final ExamService service = new ExamService();
    void handle(String file, long size, boolean open, boolean registered) {
        try {
            service.submit(file, size, open, registered);
            System.out.println("Submission accepted.");
        } catch (ExamWindowClosedException | StudentNotRegisteredException e) {
            System.out.println("Submission rejected: " + e.getMessage());
        } catch (SubmissionStorageException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
            e.printStackTrace(System.out);
        } catch (ExamException e) {
            System.out.println("Exam error: " + e.getMessage());
        }
    }
}
public class StorageDemo {
    public static void main(String[] args) {
        ExamController c = new ExamController();
        c.handle("answer01.pdf", 120, true, true);
        c.handle("answer02.pdf", 800, true, true);
        c.handle("answer03.pdf", 120, false, true);
        c.handle("answer04.pdf", 120, true, false);
    }
}