import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class BufferingBenchmark {
 static long countByte(Path p)throws IOException{long n=0;boolean any=false;try(InputStream in=Files.newInputStream(p)){int b;while((b=in.read())!=-1){any=true;if(b=='\n')n++;}}if(any&&Files.size(p)>0)try(RandomAccessFile r=new RandomAccessFile(p.toFile(),"r")){r.seek(r.length()-1);if(r.read()!='\n')n++;}return n;}
 static long countBuffered(Path p)throws IOException{long n=0;boolean any=false;try(InputStream in=new BufferedInputStream(Files.newInputStream(p),65536)){int b;while((b=in.read())!=-1){any=true;if(b=='\n')n++;}}if(any&&Files.size(p)>0)try(RandomAccessFile r=new RandomAccessFile(p.toFile(),"r")){r.seek(r.length()-1);if(r.read()!='\n')n++;}return n;}
 static long countWhole(Path p)throws IOException{byte[] a=Files.readAllBytes(p);long n=0;for(byte b:a)if(b=='\n')n++;if(a.length>0&&a[a.length-1]!='\n')n++;return n;}
 interface Job{void run()throws Exception;}
 static long ms(Job j)throws Exception{long t=System.nanoTime();j.run();return TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-t);}
 public static void main(String[] args)throws Exception{
  for(int mb:new int[]{1,10,50}){
   Path p=Path.of("attendance_"+mb+"mb.txt");String line="REGISTRATION-000000000000000000000000,PRESENT,2026-11-20\n";long target=mb*1024L*1024L,written=0;
   try(BufferedWriter w=Files.newBufferedWriter(p)){while(written<target){w.write(line);written+=line.getBytes(StandardCharsets.UTF_8).length;}}
   System.out.printf("%d MB file=%d bytes lines=%d byte=%dms buffered=%dms whole=%dms%n",mb,Files.size(p),countBuffered(p),ms(()->countByte(p)),ms(()->countBuffered(p)),ms(()->countWhole(p)));
  }
 }
}