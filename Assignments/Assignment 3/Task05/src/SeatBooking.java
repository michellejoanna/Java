import java.util.concurrent.CountDownLatch;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;
    private final Object seatLock = new Object();

    SeatBooking(int seats) { booked = new boolean[seats]; }

    // Complete Code: synchronized method makes check-and-book atomic.
    public synchronized boolean book(int seat) {
        validate(seat);
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    // Alternative: lock only the critical section using a shared lock object.
    public boolean bookWithBlock(int seat) {
        validate(seat);
        synchronized (seatLock) {
            if (booked[seat]) return false;
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
    }

    private void validate(int seat) {
        if (seat < 0 || seat >= booked.length) throw new IllegalArgumentException("Invalid seat");
    }

    private static int runSafe(boolean block) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                if (block) screen.bookWithBlock(seat);
                else screen.book(seat);
            });
            users[i].start();
        }
        for (Thread t : users) t.join();
        return screen.bookingsMade;
    }

    private static int runUnsafeRace() throws InterruptedException {
        boolean[] seatBooked = {false};
        int[] sales = {0};
        int contenders = 10;
        CountDownLatch checked = new CountDownLatch(contenders);
        CountDownLatch release = new CountDownLatch(1);
        Thread[] users = new Thread[contenders];
        for (int i = 0; i < contenders; i++) {
            users[i] = new Thread(() -> {
                if (!seatBooked[0]) {
                    checked.countDown();
                    try { release.await(); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                    seatBooked[0] = true;
                    sales[0]++;
                }
            });
            users[i].start();
        }
        checked.await();
        release.countDown();
        for (Thread t : users) t.join();
        return sales[0];
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Unsafe controlled race: bookings counted = " + runUnsafeRace());
        System.out.println("Synchronized method seats sold: " + runSafe(false));
        System.out.println("Synchronized block seats sold: " + runSafe(true));
    }
}