import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class LibrarySearchScreen extends JFrame {
 private final JTextField query=new JTextField(20);
 private final JComboBox<String> category=new JComboBox<>(new String[]{"All","Books","Journals","Theses"});
 private final JLabel status=new JLabel("Ready");
 private final DefaultTableModel model=new DefaultTableModel(new String[]{"Title","Author","Category","Availability"},0);
 private int page=1;
 public LibrarySearchScreen(){
  super("Campus Library Search");setLayout(new BorderLayout(8,8));
  JLabel title=new JLabel("Campus Library",SwingConstants.CENTER);title.setFont(title.getFont().deriveFont(Font.BOLD,20f));add(title,BorderLayout.NORTH);
  JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT));search.add(new JLabel("Search"));search.add(query);search.add(category);JButton go=new JButton("Search");search.add(go);
  JTable table=new JTable(model);JPanel center=new JPanel(new BorderLayout());center.add(search,BorderLayout.NORTH);center.add(new JScrollPane(table),BorderLayout.CENTER);add(center,BorderLayout.CENTER);
  JButton prev=new JButton("Previous"),next=new JButton("Next"),reserve=new JButton("Reserve");JPanel buttons=new JPanel();buttons.add(prev);buttons.add(next);buttons.add(reserve);
  JPanel south=new JPanel(new BorderLayout());south.add(buttons,BorderLayout.NORTH);south.add(status,BorderLayout.SOUTH);add(south,BorderLayout.SOUTH);
  go.addActionListener(e->search());query.addActionListener(e->search());prev.addActionListener(e->{if(page>1)page--;search();});next.addActionListener(e->{page++;search();});
  reserve.addActionListener(e->{int r=table.getSelectedRow();status.setText(r<0?"Select a result.":"Reservation requested: "+model.getValueAt(r,0));});
  setDefaultCloseOperation(EXIT_ON_CLOSE);setMinimumSize(new Dimension(500,330));setSize(850,550);setLocationRelativeTo(null);search();
 }
 private void search(){model.setRowCount(0);String q=query.getText().trim();model.addRow(new Object[]{q.isEmpty()?"Introduction to Java":q+" – A Study","Library Catalogue",category.getSelectedItem(),"Available"});model.addRow(new Object[]{"Data Structures","S. Kumar","Books","Issued"});model.addRow(new Object[]{"Journal of Computing","Editorial Board","Journals","Available"});status.setText("Page "+page+" | sample catalogue data");}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new LibrarySearchScreen().setVisible(true));}
}
