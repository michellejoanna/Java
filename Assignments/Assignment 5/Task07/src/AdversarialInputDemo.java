import java.sql.*;
import java.nio.file.*;

public class AdversarialInputDemo{
 static final String URL=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/assignment5"),USER=System.getenv().getOrDefault("DB_USER","root"),PASS=System.getenv().getOrDefault("DB_PASSWORD","");
 static void unsafeSearch(Connection c,String id)throws SQLException{String q="SELECT * FROM bus_passes WHERE pass_id='"+id+"'";try(Statement s=c.createStatement();ResultSet r=s.executeQuery(q)){int n=0;while(r.next())n++;System.out.println("Unsafe rows returned: "+n);}}
 static void safeSearch(Connection c,String id)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT * FROM bus_passes WHERE pass_id=?")){p.setString(1,id);try(ResultSet r=p.executeQuery()){int n=0;while(r.next())n++;System.out.println("Safe rows returned: "+n);}}}
 public static void main(String[]args)throws Exception{
  Path f=Path.of("adversarial_passes.csv");Files.writeString(f,"P2001,R2001,Arun,5,3500.0\nP2002,R2002,Divya,NOT_A_ROUTE,3800.0\nP2003,R2003,Meena,7\n");
  System.out.println("Created malformed file: "+f.toAbsolutePath());String payload="' OR '1'='1' -- ";
  try(Connection c=DriverManager.getConnection(URL,USER,PASS)){try(Statement s=c.createStatement()){s.executeUpdate("CREATE TABLE IF NOT EXISTS bus_passes(pass_id VARCHAR(20) PRIMARY KEY,register_no VARCHAR(20),student_name VARCHAR(80),route_no INT,amount DECIMAL(10,2))");s.executeUpdate("INSERT IGNORE INTO bus_passes VALUES('P2001','R2001','Sample',5,100.00)");}unsafeSearch(c,payload);safeSearch(c,payload);}
  catch(SQLException e){System.out.println("DB test skipped; configure local MySQL: "+e.getMessage());}
  System.out.println("Malformed rows must be rejected individually; subsequent valid records remain readable.");
 }
}