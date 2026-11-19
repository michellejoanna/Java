import java.util.*;
public class PharmacyInventory {
 static Map<String,Integer> make(int n,int type){
  Map<String,Integer> m= type==0?new HashMap<>():type==1?new TreeMap<>():new LinkedHashMap<>();
  for(int i=0;i<n;i++)m.put(String.format("MED%06d",i),100+i); return m;
 }
 static long lookup(Map<String,Integer> m,int n){long s=System.nanoTime(),v=0;for(int i=0;i<100000;i++)v+=m.getOrDefault(String.format("MED%06d",i%n),0);if(v==-1)System.out.println(v);return System.nanoTime()-s;}
 static long iterate(Map<String,Integer> m){long s=System.nanoTime(),v=0;for(int x:m.values())v+=x;if(v==-1)System.out.println(v);return System.nanoTime()-s;}
 static List<String> first(Map<String,Integer> m){List<String> a=new ArrayList<>();for(String k:m.keySet()){a.add(k);if(a.size()==3)break;}return a;}
 public static void main(String[] z){for(int n:new int[]{100,1000,10000}){Map<String,Integer> h=make(n,0),t=make(n,1),l=make(n,2);System.out.println("\nMedicines: "+n);System.out.printf("100k lookup ms H %.3f T %.3f L %.3f%n",lookup(h,n)/1e6,lookup(t,n)/1e6,lookup(l,n)/1e6);System.out.printf("Iteration ms H %.3f T %.3f L %.3f%n",iterate(h)/1e6,iterate(t)/1e6,iterate(l)/1e6);System.out.println("TreeMap code order: "+first(t));System.out.println("LinkedHashMap insertion order: "+first(l));}}
}