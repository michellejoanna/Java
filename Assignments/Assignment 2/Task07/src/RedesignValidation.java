import java.util.Locale;

public class RedesignValidation {
    private static final int RUNS = 7;
    private static String original(int n) {
        String text = "";
        for (int i = 0; i < n; i++) text += "field-" + i + ";";
        return text;
    }
    private static String redesigned(int n) {
        StringBuilder text = new StringBuilder(n * 10);
        for (int i = 0; i < n; i++) text.append("field-").append(i).append(';');
        return text.toString();
    }
    private static long measure(boolean builder, int n) {
        long start = System.nanoTime();
        String result = builder ? redesigned(n) : original(n);
        long elapsed = System.nanoTime() - start;
        if (result.isEmpty()) throw new IllegalStateException();
        return elapsed;
    }
    private static double average(boolean builder, int n) {
        for (int i = 0; i < 3; i++) measure(builder, n);
        long sum = 0;
        for (int i = 0; i < RUNS; i++) sum += measure(builder, n);
        return sum / (double) RUNS / 1_000_000.0;
    }
    public static void main(String[] args) {
        System.out.println("Average of " + RUNS + " runs after warm-up (ms)");
        System.out.println("Fields   Original(+)   Redesigned(StringBuilder)");
        for (int n : new int[]{100, 500, 1000, 2000}) {
            System.out.printf(Locale.US, "%-8d %-13.3f %.3f%n",
                    n, average(false, n), average(true, n));
        }
        System.out.println("Diagnosis: repeated immutable concatenation copies prior text.");
        System.out.println("Redesign: append into a pre-sized mutable StringBuilder.");
    }
}