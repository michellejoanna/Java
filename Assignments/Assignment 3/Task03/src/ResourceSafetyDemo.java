import java.io.*;
import java.nio.file.*;
import java.util.*;

class TrackingReader implements AutoCloseable {
    private static int openCount = 0;
    private final BufferedReader reader;
    TrackingReader(Path path) throws IOException { reader = Files.newBufferedReader(path); openCount++; }
    String readLine() throws IOException { return reader.readLine(); }
    static int openCount() { return openCount; }
    public void close() throws IOException { reader.close(); openCount--; }
}
public class ResourceSafetyDemo {
    private static void manual(Path[] files, Path output) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(output);
        try {
            for (Path file : files) {
                BufferedReader reader = Files.newBufferedReader(file);
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("CORRUPT")) throw new IOException("Corrupt store file: " + file);
                        writer.write(line); writer.newLine();
                    }
                } finally { reader.close(); }
            }
        } finally { writer.close(); }
    }
    private static void automatic(Path[] files, Path output) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (Path file : files) {
                System.out.println("Reading " + file.getFileName());
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("CORRUPT")) throw new IOException("Corrupt store file: " + file);
                        writer.write(line); writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Failed file " + file.getFileName() + ": " + e.getMessage());
                    throw e;
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("sales-demo");
        Path a = dir.resolve("storeA.txt"), b = dir.resolve("storeB.txt"), c = dir.resolve("storeC.txt");
        Files.write(a, Arrays.asList("Store A: 1200", "Store A: 900"));
        Files.write(b, Arrays.asList("Store B: 800", "CORRUPT RECORD"));
        Files.write(c, Arrays.asList("Store C: 1500"));
        Path[] inputs = {a,b,c};
        Path manualOut = dir.resolve("manual-report.txt"), autoOut = dir.resolve("auto-report.txt");
        try { manual(inputs, manualOut); } catch (IOException e) { System.out.println("Manual version caught: " + e.getMessage()); }
        try { automatic(inputs, autoOut); } catch (IOException e) { System.out.println("Try-with-resources caught: " + e.getMessage()); }
        System.out.println("Manual partial report exists: " + Files.exists(manualOut));
        System.out.println("Automatic partial report exists: " + Files.exists(autoOut));
        System.out.println("Temporary test directory: " + dir);
        System.out.println("Both approaches close resources here because manual close is protected by finally.");
    }
}