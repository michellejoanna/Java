import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.concurrent.ExecutionException;
class RegistrationService {
 private final String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/campus_events");
 private final String user=System.getenv().getOrDefault("DB_USER","root"),pass=System.getenv().getOrDefault("DB_PASSWORD","");
 private Connection connect()throws SQLException{return DriverManager.getConnection(url,user,pass);}
 void register(String sid,String name,String email,int event)throws SQLException{
  if(sid.isBlank()||name.isBlank()||!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")||event<1)throw new IllegalArgumentException("Enter valid student ID, name, email and event ID.");
  try(Connection c=connect();PreparedStatement p=c.prepareStatement("INSERT INTO registrations(student_id,student_name,email,event_id) VALUES(?,?,?,?)")){
   p.setString(1,sid);p.setString(2,name);p.setString(3,email);p.setInt(4,event);p.executeUpdate();}
 }
 Object[][] page(String filter,int limit,int offset)throws SQLException{
  String sql="SELECT r.id,r.student_id,r.student_name,r.email,e.title,r.registered_at FROM registrations r JOIN events e ON e.id=r.event_id WHERE e.title LIKE ? ORDER BY r.id DESC LIMIT ? OFFSET ?";
  try(Connection c=connect();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,"%"+filter+"%");p.setInt(2,limit);p.setInt(3,offset);
   try(ResultSet rs=p.executeQuery()){java.util.List<Object[]> rows=new java.util.ArrayList<>();while(rs.next())rows.add(new Object[]{rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getTimestamp(6)});return rows.toArray(new Object[0][]);}}
 }
}
public class RegistrationFrame extends JFrame {
 private final RegistrationService service=new RegistrationService();
 private final JTextField sid=new JTextField(8),name=new JTextField(9),email=new JTextField(12),event=new JTextField("1",3),filter=new JTextField(10);
 private final JLabel status=new JLabel("Ready");private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Student ID","Name","Email","Event","Registered"},0);
 private int page=0;private static final int SIZE=20;
 public RegistrationFrame(){super("Campus Event Registration");
  JMenuBar bar=new JMenuBar();JMenu file=new JMenu("File");JMenuItem refresh=new JMenuItem("Refresh"),exit=new JMenuItem("Exit");refresh.addActionListener(e->load());exit.addActionListener(e->dispose());file.add(refresh);file.add(exit);bar.add(file);setJMenuBar(bar);
  JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT));form.add(new JLabel("Student ID"));form.add(sid);form.add(new JLabel("Name"));form.add(name);form.add(new JLabel("Email"));form.add(email);form.add(new JLabel("Event ID"));form.add(event);JButton add=new JButton("Register");form.add(add);
  JPanel fp=new JPanel();fp.add(new JLabel("Event title"));fp.add(filter);JButton apply=new JButton("Filter");fp.add(apply);
  JPanel north=new JPanel(new BorderLayout());north.add(form,BorderLayout.NORTH);north.add(fp,BorderLayout.SOUTH);add(north,BorderLayout.NORTH);add(new JScrollPane(new JTable(model)),BorderLayout.CENTER);
  JButton prev=new JButton("Previous"),next=new JButton("Next");JPanel nav=new JPanel();nav.add(prev);nav.add(next);JPanel south=new JPanel(new BorderLayout());south.add(nav,BorderLayout.CENTER);south.add(status,BorderLayout.SOUTH);add(south,BorderLayout.SOUTH);
  add.addActionListener(e->register());apply.addActionListener(e->{page=0;load();});prev.addActionListener(e->{if(page>0){page--;load();}});next.addActionListener(e->{page++;load();});
  setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1000,500);setLocationRelativeTo(null);load();
 }
 private void register(){final int id;try{id=Integer.parseInt(event.getText().trim());}catch(Exception e){status.setText("Event ID must be numeric.");return;}
  final String a=sid.getText().trim(),b=name.getText().trim(),c=email.getText().trim();busy(true);
  new SwingWorker<Void,Void>(){protected Void doInBackground()throws Exception{service.register(a,b,c,id);return null;}
   protected void done(){busy(false);try{get();status.setText("Registration successful.");page=0;load();}
    catch(Exception ex){Throwable t=ex instanceof ExecutionException?ex.getCause():ex;String m=String.valueOf(t.getMessage());status.setText(m.toLowerCase().contains("duplicate")?"Already registered for this event.":"Registration failed: "+m);}}}.execute();
 }
 private void load(){final String f=filter.getText().trim();final int off=page*SIZE;busy(true);
  new SwingWorker<Object[][],Void>(){protected Object[][] doInBackground()throws Exception{return service.page(f,SIZE,off);}
   protected void done(){busy(false);try{Object[][] rows=get();model.setRowCount(0);for(Object[] r:rows)model.addRow(r);status.setText("Page "+(page+1)+" – "+rows.length+" records");}
    catch(Exception e){status.setText("Database unavailable: "+e.getMessage());}}}.execute();
 }
 private void busy(boolean b){setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new RegistrationFrame().setVisible(true));}
}
