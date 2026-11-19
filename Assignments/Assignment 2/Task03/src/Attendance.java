import java.util.*;

class StudentBad {
    final String regNo, name;
    StudentBad(String regNo, String name) { this.regNo = regNo; this.name = name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentBad)) return false;
        return regNo.equals(((StudentBad) o).regNo);
    }
    // Deliberately missing hashCode(): demonstrates the contract bug.
}
class Student {
    private final String regNo, name;
    Student(String regNo, String name) { this.regNo = regNo; this.name = name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return regNo.equals(((Student) o).regNo);
    }
    @Override public int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return regNo + " " + name; }
}
public class Attendance {
    public static void main(String[] args) {
        Set<StudentBad> broken = new HashSet<>();
        broken.add(new StudentBad("192311001", "Arun"));
        broken.add(new StudentBad("192311002", "Divya"));
        broken.add(new StudentBad("192311001", "Arun"));
        System.out.println("Without hashCode override (may vary): " + broken.size());

        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));
        System.out.println("Unique students: " + present.size());

        Set<Student> stable = new HashSet<>();
        stable.add(new Student("192311003", "Kavi"));
        stable.add(new Student("192311003", "Kavi duplicate"));
        System.out.println("Repeated scan test: " + stable.size());

        Set<String> byTree = new TreeSet<>(Comparator.comparing(String::toString));
        byTree.add("192311001"); byTree.add("192311001");
        System.out.println("TreeSet registration numbers: " + byTree.size());
    }
}