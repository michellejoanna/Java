import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BusPassFileFormatDesign {
    record Pass(String passId,String registerNo,String name,int routeNo,String boardingStop,double amount) {}
    static final Path TEXT=Path.of("bus_passes.csv"), BINARY=Path.of("bus_passes.bin");
    static String esc(String s){return s.replace("\\","\\\\").replace(",","\\,").replace("\n","\\n").replace("\r","\\r");}
    static String encode(Pass p){return String.join(",",esc(p.passId()),esc(p.registerNo()),esc(p.name()),""+p.routeNo(),esc(p.boardingStop()),""+p.amount());}
    static List<String> split(String line){
        List<String> a=new ArrayList<>();StringBuilder b=new StringBuilder();boolean e=false;
        for(char c:line.toCharArray()){if(e){b.append(c=='n'?'\n':c=='r'?'\r':c);e=false;}else if(c=='\\')e=true;else if(c==','){a.add(b.toString());b.setLength(0);}else b.append(c);}
        if(e)b.append('\\');a.add(b.toString());return a;
    }
    static Pass decode(String s){List<String> f=split(s);if(f.size()!=6)throw new IllegalArgumentException("Six fields required");return new Pass(f.get(0),f.get(1),f.get(2),Integer.parseInt(f.get(3)),f.get(4),Double.parseDouble(f.get(5)));}
    static void addText(Pass p)throws IOException{Files.writeString(TEXT,encode(p)+System.lineSeparator(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);}
    static Optional<Pass> find(String id)throws IOException{if(!Files.exists(TEXT))return Optional.empty();try(BufferedReader r=Files.newBufferedReader(TEXT)){String s;while((s=r.readLine())!=null)try{Pass p=decode(s);if(p.passId().equals(id))return Optional.of(p);}catch(RuntimeException ex){System.err.println("Skipped damaged row: "+s);}}return Optional.empty();}
    static void list()throws IOException{if(!Files.exists(TEXT))return;try(BufferedReader r=Files.newBufferedReader(TEXT)){String s;while((s=r.readLine())!=null)try{System.out.println(decode(s));}catch(RuntimeException ex){System.err.println("Skipped: "+s);}}}
    static void addBinary(Pass p)throws IOException{try(DataOutputStream o=new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(BINARY,StandardOpenOption.CREATE,StandardOpenOption.APPEND)))){o.writeUTF(p.passId());o.writeUTF(p.registerNo());o.writeUTF(p.name());o.writeInt(p.routeNo());o.writeUTF(p.boardingStop());o.writeDouble(p.amount());}}
    public static void main(String[] args)throws Exception{Files.deleteIfExists(TEXT);Files.deleteIfExists(BINARY);addText(new Pass("P1001","192311001","Arun",5,"Main Gate",3500));addText(new Pass("P1002","192311002","Divya",7,"Library",3800));addBinary(new Pass("P1001","192311001","Arun",5,"Main Gate",3500));System.out.println("FIND: "+find("P1002").orElse(null));list();System.out.println("Text: "+TEXT.toAbsolutePath());}
}