import java.util.*;

interface Dao<T,K>{void insert(T x);Optional<T> findById(K id);List<T> findAll();boolean update(K id,T x);boolean delete(K id);}
record Student(String registerNo,String name){}
class StudentDao implements Dao<Student,String>{
 private final Map<String,Student> rows=new LinkedHashMap<>();
 public void insert(Student s){rows.put(s.registerNo(),s);}
 public Optional<Student> findById(String id){return Optional.ofNullable(rows.get(id));}
 public List<Student> findAll(){return new ArrayList<>(rows.values());}
 public boolean update(String id,Student s){if(!rows.containsKey(id))return false;rows.put(id,s);return true;}
 public boolean delete(String id){return rows.remove(id)!=null;}
}
public class GenericDaoPatternDemo{
 public static void main(String[] args){Dao<Student,String>d=new StudentDao();d.insert(new Student("192311001","Arun"));d.insert(new Student("192311002","Divya"));System.out.println("Find: "+d.findById("192311002").orElse(null));System.out.println("All: "+d.findAll());d.update("192311001",new Student("192311001","Arun Kumar"));d.delete("192311002");System.out.println("After update/delete: "+d.findAll());}
}