import java.util.*;
class Team {
 final String name; final int points,goalDiff;
 Team(String n,int p,int g){name=n;points=p;goalDiff=g;}
 public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T>{
 private final List<T> items=new ArrayList<>(); private final Comparator<? super T> order;
 Leaderboard(Comparator<? super T> c){order=c;}
 void add(T x){items.add(x);}
 void addAll(List<? extends T> xs){items.addAll(xs);}
 List<T> ranking(){List<T> copy=new ArrayList<>(items);copy.sort(order);return copy;}
}
public class LeaderboardDemo{
 public static void main(String[] args){
  Comparator<Team> byRank=Comparator.comparingInt((Team t)->t.points).reversed()
   .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed()).thenComparing(t->t.name);
  Leaderboard<Team> b=new Leaderboard<>(byRank);
  b.add(new Team("Panthers",9,4));b.add(new Team("Eagles",9,6));b.add(new Team("Cobras",6,1));b.add(new Team("Bisons",9,4));
  System.out.println(b.ranking());
  b.addAll(Arrays.asList(new Team("Falcons",6,3),new Team("Hawks",6,3)));
  System.out.println("Second data set: "+b.ranking());
 }
}