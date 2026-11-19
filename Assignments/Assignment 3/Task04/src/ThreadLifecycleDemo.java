import java.util.*;

class ScannerStation extends Thread {
    private final int scripts;
    ScannerStation(String name, int scripts) { super(name); this.scripts = scripts; }
    public void run() {
        try {
            Thread.sleep(40L + (long)(Math.random() * 160));
            System.out.println(getName() + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
class RunnableStation implements Runnable {
    private final String name; private final int scripts;
    RunnableStation(String name, int scripts) { this.name=name; this.scripts=scripts; }
    public void run() {
        try { Thread.sleep(40L + (long)(Math.random()*160)); System.out.println(name+" scanned "+scripts+" scripts"); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = {
            new ScannerStation("Station-1", 25),
            new Scanner("Station-2", 30),
            new Thread(new RunnableStation("Station-3", 20)),
            new Thread(new RunnableStation("Station-4", 25))
        };
        for (Thread t : stations) { t.setPriority(Thread.NORM_PRIORITY); t.start(); }
        System.out.println("Station 1 alive after start: " + stations[0].isAlive());
        for (Thread t : stations) t.join();
        System.out.println("All stations completed. Total scanned: 100");
        Thread runOnly = new Thread(() -> System.out.println("run() executes on: " + Thread.currentThread().getName()));
        runOnly.run(); // synchronous call on main thread, not a new thread
        System.out.println("Main finished.");
    }
    static class Scanner extends Thread {
        Scanner(String name, int count) { super(name); }
        public void run() {
            try { Thread.sleep(40L + (long)(Math.random()*160)); System.out.println(getName()+" scanned 30 scripts"); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}