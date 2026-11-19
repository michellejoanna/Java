import java.util.*;

public class CollectionBenchmark {
    private static volatile long sink;
    private static long time(Runnable action) {
        long start = System.nanoTime(); action.run(); return System.nanoTime() - start;
    }
    private static void run(int n) {
        List<Integer> array = new ArrayList<>(), linked = new LinkedList<>();
        for (int i = 0; i < n; i++) { array.add(i); linked.add(i); }
        int ops = Math.min(n, 10000);
        long aEnd = time(() -> { for (int i=0;i<ops;i++) array.add(i); });
        long lEnd = time(() -> { for (int i=0;i<ops;i++) linked.add(i); });
        long aFront = time(() -> { for (int i=0;i<ops;i++) { array.add(0,-i); array.remove(0); } });
        long lFront = time(() -> { for (int i=0;i<ops;i++) { linked.add(0,-i); linked.remove(0); } });
        int midA=array.size()/2, midL=linked.size()/2;
        long aMiddle = time(() -> { for (int i=0;i<ops;i++) { int p=array.size()/2; array.add(p,-i); array.remove(p); } });
        long lMiddle = time(() -> { for (int i=0;i<ops;i++) { int p=linked.size()/2; linked.add(p,-i); linked.remove(p); } });
        long aGet = time(() -> { long x=0; for(int i=0;i<ops;i++) x+=array.get(i%array.size()); sink=x; });
        long lGet = time(() -> { long x=0; for(int i=0;i<ops;i++) x+=linked.get(i%linked.size()); sink=x; });
        long aIter = time(() -> { long x=0; for(int v:array)x+=v; sink=x; });
        long lIter = time(() -> { long x=0; for(int v:linked)x+=v; sink=x; });
        Deque<Integer> deque=new ArrayDeque<>(); for(int i=0;i<n;i++)deque.addLast(i);
        long dEnds=time(() -> { for(int i=0;i<ops;i++){deque.addFirst(-i);deque.addLast(i);deque.removeFirst();deque.removeLast();} });
        System.out.printf("%-7d %7.2f %7.2f %7.2f %7.2f %7.2f %7.2f %7.2f %7.2f %7.2f%n",
          n,ms(aEnd),ms(lEnd),ms(aFront),ms(lFront),ms(aMiddle),ms(lMiddle),ms(aGet),ms(lGet),ms(dEnds));
        if(midA<0||midL<0)System.out.println();
        // Full traversal timings are also reported below.
        System.out.printf("        full iteration: ArrayList %.2f ms | LinkedList %.2f ms%n",ms(aIter),ms(lIter));
    }
    private static double ms(long ns){return ns/1_000_000.0;}
    public static void main(String[] args){
        System.out.println("n       A.end L.end A.front L.front A.mid L.mid A.get L.get DequeEnds (ms)");
        for(int n:new int[]{1000,10000,50000})run(n);
        System.out.println("Expected: ArrayList indexed get O(1), end-add amortized O(1), front/middle insert O(n);");
        System.out.println("LinkedList end operations O(1), indexed get and locating a middle position O(n);");
        System.out.println("ArrayDeque end add/remove amortized O(1), but no indexed or middle access.");
    }
}