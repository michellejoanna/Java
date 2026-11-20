import java.nio.file.*;
public class FileStoreBenchmark{
 interface Job{void run()throws Exception;}
 static long ms(Job j)throws Exception{long t=System.nanoTime();j.run();return(System.nanoTime()-t)/1_000_000;}
 public static void main(String[]args)throws Exception{
  for(int mb:new int[]{1,10,50}){Path p=Path.of("attendance_"+mb+"mb.txt");try(var w=Files.newBufferedWriter(p)){for(int i=0;i<mb*25000;i++)w.write("REG"+i+",PRESENT,2026-11-20\n");}
   long buffered=ms(()->{try(var r=Files.newBufferedReader(p)){while(r.readLine()!=null){}}});
   long byteRead=ms(()->{try(var in=Files.newInputStream(p)){while(in.read()!=-1){}}});
   System.out.printf("%d MB buffered=%d ms byte-read=%d ms%n",mb,buffered,byteRead);
  }
  for(int n:new int[]{1000,10000,100000}){Path p=Path.of("passes_"+n+".csv");try(var w=Files.newBufferedWriter(p)){for(int i=0;i<n;i++)w.write("P"+i+",R"+i+",Name"+i+",5,3500.0\n");}long t=ms(()->{try(var lines=Files.lines(p)){lines.filter(x->x.startsWith("P"+(n-1)+",")).findFirst();}});System.out.println("Find last in "+n+" rows: "+t+" ms");}
 }
}