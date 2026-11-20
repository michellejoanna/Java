import java.io.*;
import java.util.*;

class Resident implements Serializable{
 private static final long serialVersionUID=2L;
 String registerNo,name,phoneNumber;
 transient String password;
 transient Object liveSession;
 Resident(String r,String n,String p,String phone){registerNo=r;name=n;password=p;phoneNumber=phone;}
}
class Room implements Serializable{
 private static final long serialVersionUID=1L;
 int roomNo;List<Resident> residents=new ArrayList<>();Room(int n){roomNo=n;}
}
class Hostel implements Serializable{
 private static final long serialVersionUID=1L;
 String hostelName;List<Room> rooms=new ArrayList<>();transient Object databaseConnection;
 Hostel(String n){hostelName=n;}
}
public class HostelSerializationDemo{
 public static void main(String[] args)throws Exception{Hostel h=new Hostel("SIMATS Hostel");Room r=new Room(101);r.residents.add(new Resident("192311001","Arun","secret","+91-9000000000"));h.rooms.add(r);
 try(ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("hostel.ser"))){o.writeObject(h);}
 try(ObjectInputStream in=new ObjectInputStream(new FileInputStream("hostel.ser"))){Hostel x=(Hostel)in.readObject();Resident s=x.rooms.get(0).residents.get(0);System.out.println(x.hostelName+" room "+x.rooms.get(0).roomNo);System.out.println(s.registerNo+" "+s.name+" phone="+s.phoneNumber);System.out.println("Transient password="+s.password+" connection="+x.databaseConnection);}
 }
}