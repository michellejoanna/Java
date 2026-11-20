import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
public class HospitalAppointmentDesk extends JFrame {
 private final JTextField patient=new JTextField(12), date=new JTextField("2026-12-01",10);
 private final JComboBox<String> dept=new JComboBox<>(new String[]{"Cardiology","Neurology","Orthopaedics"});
 private final JComboBox<String> doctor=new JComboBox<>(), slot=new JComboBox<>();
 private boolean pending=false;
 private final Map<String,String[]> doctors=Map.of("Cardiology",new String[]{"Dr Anand","Dr Meera"},"Neurology",new String[]{"Dr Kumar","Dr Nisha"},"Orthopaedics",new String[]{"Dr Ravi","Dr Sara"});
 public HospitalAppointmentDesk(){
  super("Hospital Outpatient Appointment Desk");
  JPanel f=new JPanel(new GridLayout(5,2,8,8));
  f.add(new JLabel("Patient ID"));f.add(patient);f.add(new JLabel("Department"));f.add(dept);
  f.add(new JLabel("Doctor"));f.add(doctor);f.add(new JLabel("Date YYYY-MM-DD"));f.add(date);f.add(new JLabel("Time slot"));f.add(slot);
  JButton book=new JButton("Book appointment");add(new JLabel("Appointment Desk",SwingConstants.CENTER),BorderLayout.NORTH);add(f);add(book,BorderLayout.SOUTH);
  dept.addActionListener(e->refreshDoctors());doctor.addActionListener(e->refreshSlots());date.addActionListener(e->refreshSlots());book.addActionListener(e->book());
  addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){if(pending&&JOptionPane.showConfirmDialog(HospitalAppointmentDesk.this,"Booking is pending. Close?","Confirm",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;dispose();}});
  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);refreshDoctors();setSize(440,280);setLocationRelativeTo(null);
 }
 private void refreshDoctors(){doctor.setModel(new DefaultComboBoxModel<>(doctors.get((String)dept.getSelectedItem())));refreshSlots();}
 private void refreshSlots(){slot.removeAllItems();if(doctor.getSelectedItem()!=null){slot.addItem("09:00");slot.addItem("10:30");slot.addItem("14:00");}}
 private void book(){if(patient.getText().isBlank()||!date.getText().matches("\\d{4}-\\d{2}-\\d{2}")||slot.getSelectedItem()==null){JOptionPane.showMessageDialog(this,"Complete all fields with a valid date.");return;}
  if(JOptionPane.showConfirmDialog(this,"Confirm appointment?","Booking",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
  pending=true;JOptionPane.showMessageDialog(this,"Appointment booked. Reference APT-"+System.currentTimeMillis());pending=false;}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new HospitalAppointmentDesk().setVisible(true));}
}
