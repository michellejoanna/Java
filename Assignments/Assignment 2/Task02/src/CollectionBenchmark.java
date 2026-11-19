import java.util.*;

public class CollectionBenchmark {
    private static volatile long sink;
    private static long time(Runnable action) {
        long start = System.nanoTime(); action.run(); return System.nanoTime() - start;
    }
    private static void run(int n) {
        List<Integer> array = new ArrayList<>(), linked = new LinkedList<>();
        for (int i = 0; i < n; i++) { array.add(i); linked.add(i); }
        int operations = Math.min(n, 20000);
        long aGet = time(() -> { long x = 0; for (int i = 0; i < operations; i++) x += array.get(i % n); sink = x; });
        long lGet = time(() -> { long x = 0; for (int i = 0; i < operations; i++) x += linked.get(i % n); sink = x; });
        long aFront = time(() -> { for (int i = 0; i < operations; i++) { array.add(0, -i); array.remove(0); } });
        long lFront = time(() -> { for (int i = 0; i < operations; i++) { linked.add(0, -i); linked.remove(0); } });
        long aIter = time(() -> { long x = 0; for (int v : array) x += v; sink = x; });
        long lIter = time(() -> { long x = 0; for (int v : linked) x += v; sink = x; });
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) deque.addLast(i);
        long dEnds = time(() -> { for (int i = 0; i < operations; i++) { deque.addFirst(-i); deque.addLast(i); deque.removeFirst(); deque.removeLast(); } });
        System.out.printf("%-8d %8.3f %8.3f %8.3f %8.3f %8.3f %8.3f %8.3f%n",
                n, ms(aGet), ms(lGet), ms(aFront), ms(lFront), ms(aIter), ms(lIter), ms(dEnds));
    }
    private static double ms(long ns) { return ns / 1_000_000.0; }
    public static void main(String[] args) {
        System.out.println("n        A.get   L.get   A.front L.front A.iter  L.iter  DequeEnds (ms)");
        for (int n : new int[]{1000, 10000, 50000}) run(n);
        System.out.println("\nComplexity: ArrayList get O(1), end-add amortized O(1), front/middle insert O(n); "
                + "LinkedList indexed get O(n), known-node end operations O(1), middle search O(n); "
                + "ArrayDeque add/remove at either end amortized O(1), indexed access unavailable.");
    }
}