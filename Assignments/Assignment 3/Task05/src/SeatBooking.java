import java.util.concurrent.CountDownLatch;
public class SeatBooking {
 private final boolean[] booked; private int bookingsMade=0;
 SeatBooking(int seats){booked=new boolean[seats];}
 public synchronized boolean book(int seat){
  if(seat<0||seat>=booked.length)throw new IllegalArgumentException("Invalid seat");
  if(booked[seat])return false; booked[seat]=true; bookingsMade++; return true;
 }
 private static int safe() throws InterruptedException{
  SeatBooking s=new SeatBooking(10);Thread[] users=new Thread[100];
  for(int i=0;i<100;i++){final int seat=i%10;users[i]=new Thread(()->s.book(seat));users[i].start();}
  for(Thread t:users)t.join();return s.bookingsMade;
 }
 private static int unsafe() throws InterruptedException{
  boolean[] booked={false};int[] sales={0};int n=10;
  CountDownLatch checked=new CountDownLatch(n),release=new CountDownLatch(1);Thread[] ts=new Thread[n];
  for(int i=0;i<n;i++){ts[i]=new Thread(()->{if(!booked[0]){checked.countDown();try{release.await();}catch(InterruptedException e){Thread.currentThread().interrupt();return;}booked[0]=true;sales[0]++;}});ts[i].start();}
  checked.await();release.countDown();for(Thread t:ts)t.join();return sales[0];
 }
 public static void main(String[] args)throws InterruptedException{
  System.out.println("Unsafe controlled race: bookings counted = "+unsafe());
  System.out.println("Seats sold: "+safe());
 }
}