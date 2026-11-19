import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentResultProcessor {
    static class Result {
        final String file; final boolean success; final String message; final int score;
        Result(String f,boolean ok,String m,int s){file=f;success=ok;message=m;score=s;}
    }
    static class ScoreTask implements Callable<Result> {
        private final Path file;
        ScoreTask(Path file){this.file=file;}
        public Result call() {
            try {
                if(!Files.exists(file)) throw new NoSuchFileException(file.toString());
                String raw=Files.readString(file).trim();
                if(raw.isEmpty() || !raw.matches("\\d{1,3}")) throw new IllegalArgumentException("Corrupt score data");
                int score=Integer.parseInt(raw);
                if(score<0||score>100)throw new IllegalArgumentException("Score outside 0..100");
                return new Result(file.getFileName().toString(),true,"OK",score);
            } catch(Exception e) {
                return new Result(file.getFileName().toString(),false,e.getClass().getSimpleName()+": "+e.getMessage(),0);
            }
        }
    }
    private static void createDataset(Path dir,int count) throws IOException {
        Files.createDirectories(dir);
        for(int i=0;i<count;i++){
            Path f=dir.resolve(String.format(Locale.ROOT,"score_%05d.txt",i));
            if(i%101==0){Files.deleteIfExists(f);continue;} // intentionally missing
            if(i%97==0){Files.writeString(f,"not-a-score");continue;} // intentionally corrupt
            Files.writeString(f,Integer.toString((i*17)%101));
        }
    }
    private static void run(Path dir,int count,int poolSize) throws Exception {
        ExecutorService pool=Executors.newFixedThreadPool(poolSize);
        ConcurrentLinkedQueue<Result> results=new ConcurrentLinkedQueue<>();
        List<Future<Result>> futures=new ArrayList<>(count);
        long start=System.nanoTime();
        try {
            for(int i=0;i<count;i++){
                Path file=dir.resolve(String.format(Locale.ROOT,"score_%05d.txt",i));
                futures.add(pool.submit(new ScoreTask(file)));
            }
            for(Future<Result> future:futures){
                try { results.add(future.get()); }
                catch(ExecutionException e) {
                    Throwable cause=e.getCause();
                    results.add(new Result("unknown",false,"Unexpected task failure: "+cause,0));
                }
            }
        } finally {
            pool.shutdown();
            if(!pool.awaitTermination(1,TimeUnit.MINUTES))pool.shutdownNow();
        }
        long elapsed=System.nanoTime()-start;
        int ok=0,failed=0,total=0,sum=0;
        for(Result r:results){total++;if(r.success){ok++;sum+=r.score;}else failed++;}
        System.out.printf(Locale.ROOT,"pool=%d files=%d success=%d failed=%d scoreTotal=%d timeMs=%.3f%n",
                poolSize,total,ok,failed,sum,elapsed/1_000_000.0);
        if(total!=count||ok+failed!=count)throw new IllegalStateException("Summary count mismatch");
    }
    public static void main(String[] args) throws Exception {
        int count=args.length>0?Integer.parseInt(args[0]):10000;
        Path dir=Paths.get(args.length>1?args[1]:"generated_scores");
        createDataset(dir,count);
        System.out.println("Generated test dataset at "+dir.toAbsolutePath());
        for(int p:new int[]{1,2,4,8})run(dir,count,p);
    }
}