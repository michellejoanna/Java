import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;
    private final boolean useMethodLock;
    private final boolean useBlockLock;

    SeatBooking(int seats, boolean methodLock, boolean blockLock) {
        booked = new boolean[seats];
        useMethodLock = methodLock;
        useBlockLock = blockLock;
    }

    // Synchronized method version.
    public synchronized boolean bookSynchronizedMethod(int seat) {
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    // Synchronized block version: only the check-and-update critical section is locked.
    public boolean bookSynchronizedBlock(int seat) {
        synchronized (booked) {
            if (booked[seat]) return false;
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
    }

    // Deliberately unsafe version, with a test barrier to expose the race.
    public boolean bookUnsafe(int seat, CountDownLatch checked, CountDownLatch release)
            throws InterruptedException {
        if (booked[seat]) return false;
        checked.countDown();
        release.await();
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public boolean book(int seat) {
        if (useMethodLock) return bookSynchronizedMethod(seat);
        if (useBlockLock) return bookSynchronizedBlock(seat);
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public int getBookingsMade() { return bookingsMade; }

    private static int runSafe(boolean method) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10, method, !method);
        Thread[] users = new Thread[100];
        for (int i=0;i<users.length;i++) {
            final int seat=i%10;
            users[i]=new Thread(() -> screen.book(seat));
            users[i].start();
        }
        for(Thread t:users)t.join();
        return screen.getBookingsMade();
    }

    private static int runUnsafeRace() throws InterruptedException {
        SeatBooking screen = new SeatBooking(1, false, false);
        int contenders=10;
        CountDownLatch checked=new CountDownLatch(contenders);
        CountDownLatch release=new CountDownLatch(1);
        Thread[] users=new Thread[contenders];
        for(int i=0;i<contenders;i++){
            users[i]=new Thread(() -> {
                try { screen.bookUnsafe(0,checked,release); }
                catch(InterruptedException e){Thread.currentThread().interrupt();}
            });
            users[i].start();
        }
        checked.await();
        release.countDown();
        for(Thread t:users)t.join();
        return screen.getBookingsMade();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Unsafe controlled race: bookings counted = " + runUnsafeRace()
                + " (can exceed 1)");
        System.out.println("Synchronized method: Seats sold = " + runSafe(true));
        System.out.println("Synchronized block:  Seats sold = " + runSafe(false));
    }
}