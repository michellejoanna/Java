import java.util.Locale;

public class StringBuildingBenchmark {
    private static final int FIELDS = 50;
    private static final int TICKETS = 10000;
    private static final int RUNS = 5;

    private static String buildWithString(int fields) {
        String ticket = "";
        for (int i = 1; i <= fields; i++) ticket += "FIELD-" + i + "|";
        return ticket;
    }
    private static String buildWithBuilder(int fields) {
        StringBuilder ticket = new StringBuilder();
        for (int i = 1; i <= fields; i++) ticket.append("FIELD-").append(i).append('|');
        return ticket.toString();
    }
    private static String buildWithBuffer(int fields) {
        StringBuffer ticket = new StringBuffer();
        for (int i = 1; i <= fields; i++) ticket.append("FIELD-").append(i).append('|');
        return ticket.toString();
    }
    private static long measure(int method, int fields, int tickets) {
        long start = System.nanoTime();
        long checksum = 0;
        for (int t = 0; t < tickets; t++) {
            String s = method == 0 ? buildWithString(fields)
                    : method == 1 ? buildWithBuilder(fields) : buildWithBuffer(fields);
            checksum += s.length();
        }
        if (checksum == 0) System.out.println(" "); // keep result observable
        return System.nanoTime() - start;
    }
    public static void main(String[] args) {
        int[][] workloads = {{5, 1}, {50, 10000}, {200, 2000}};
        String[] names = {"Short ticket", "High-volume booking", "Wide ticket"};
        String[] methods = {"String +", "StringBuilder", "StringBuffer"};
        System.out.println("Average execution time (ms), " + RUNS + " runs");
        for (int w = 0; w < workloads.length; w++) {
            System.out.println("\n" + names[w] + " (" + workloads[w][1] + " tickets, "
                    + workloads[w][0] + " fields)");
            for (int m = 0; m < 3; m++) {
                long total = 0;
                for (int r = 0; r < RUNS; r++) total += measure(m, workloads[w][0], workloads[w][1]);
                System.out.printf(Locale.US, "%-14s %.3f ms%n", methods[m], total / (double) RUNS / 1_000_000);
            }
        }
        System.out.println("\nObject note: String concatenation creates intermediate immutable strings; "
                + "builder/buffer reuse a mutable character sequence.");
        System.out.println("Recommendation: StringBuilder per ticket; StringBuffer only when "
                + "multiple threads truly share the same mutable log buffer.");
    }
}