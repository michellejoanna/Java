import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PassStore{
 public record Pass(String passId,String registerNo,String name,int routeNo,double amount){}
 private final Path file;public PassStore(Path p){file=p;}
 private static String esc(String s){return s.replace("\\","\\\\").replace(",","\\,").replace("\n","\\n");}
 private static List<String> split(String line){List<String>a=new ArrayList<>();StringBuilder b=new StringBuilder();boolean e=false;for(char c:line.toCharArray()){if(e){b.append(c=='n'?'\n':c);e=false;}else if(c=='\\')e=true;else if(c==','){a.add(b.toString());b.setLength(0);}else b.append(c);}if(e)b.append('\\');a.add(b.toString());return a;}
 private static Pass parse(String line){List<String>f=split(line);if(f.size()!=5)throw new IllegalArgumentException("Expected five fields");int route=Integer.parseInt(f.get(3));double amount=Double.parseDouble(f.get(4));if(f.get(0).isBlank()||route<0||!Double.isFinite(amount)||amount<0)throw new IllegalArgumentException("Invalid value");return new Pass(f.get(0),f.get(1),f.get(2),route,amount);}
 private static String encode(Pass p){return String.join(",",esc(p.passId()),esc(p.registerNo()),esc(p.name()),""+p.routeNo(),""+p.amount());}
 public void add(Pass p)throws IOException{Files.writeString(file,encode(p)+System.lineSeparator(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);}
 public Optional<Pass> find(String id)throws IOException{if(!Files.exists(file))return Optional.empty();try(BufferedReader r=Files.newBufferedReader(file)){String line;while((line=r.readLine())!=null)try{Pass p=parse(line);if(p.passId().equals(id))return Optional.of(p);}catch(RuntimeException e){System.err.println("Skipping damaged line: "+line);}}return Optional.empty();}
 public List<Pass> list()throws IOException{List<Pass>a=new ArrayList<>();if(!Files.exists(file))return a;try(BufferedReader r=Files.newBufferedReader(file)){String line;while((line=r.readLine())!=null)try{a.add(parse(line));}catch(RuntimeException e){System.err.println("Skipping damaged line: "+line);}}return a;}
 public static void main(String[] args)throws Exception{Path f=Path.of("passes.csv");Files.deleteIfExists(f);PassStore s=new PassStore(f);s.add(new Pass("P1001","192311001","Arun",5,3500));Files.writeString(f,"P1002,DAMAGED LINE\n",StandardOpenOption.APPEND);s.add(new Pass("P1003","192311003","Divya",7,3800));System.out.println("Find P1003: "+s.find("P1003").orElse(null));System.out.println("Find P1002: "+s.find("P1002").orElse(null));System.out.println("Valid records: "+s.list().size());}
}