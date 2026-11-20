import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CurrencyConverter extends JFrame implements ActionListener {
 private final JTextField amount=new JTextField(12);private final JLabel result=new JLabel("Result: —");
 public CurrencyConverter(){super("Currency Converter – INR to USD (demo rate)");JPanel p=new JPanel(new FlowLayout());
  p.add(new JLabel("Amount INR"));p.add(amount);
  JButton a=new JButton("Frame listener"),b=new JButton("Named inner"),c=new JButton("Anonymous"),d=new JButton("Lambda");
  p.add(a);p.add(b);p.add(c);p.add(d);p.add(result);add(p);
  a.setActionCommand("CONVERT");a.addActionListener(this);b.addActionListener(new NamedHandler());
  c.addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){convert();}});
  d.addActionListener(e->convert());amount.addActionListener(e->convert());
  addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){dispose();}});
  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);setSize(650,170);setLocationRelativeTo(null);
 }
 @Override public void actionPerformed(ActionEvent e){if("CONVERT".equals(e.getActionCommand()))convert();}
 private class NamedHandler implements ActionListener{@Override public void actionPerformed(ActionEvent e){convert();}}
 private void convert(){try{double v=Double.parseDouble(amount.getText().trim());if(!Double.isFinite(v)||v<0)throw new NumberFormatException();
  String s=String.format("INR %.2f = USD %.2f (demo rate: 83 INR/USD)",v,v/83.0);result.setText(s);JOptionPane.showMessageDialog(this,s,"Conversion",JOptionPane.INFORMATION_MESSAGE);
 }catch(NumberFormatException ex){result.setText("Invalid amount");JOptionPane.showMessageDialog(this,"Enter a valid non-negative amount.","Invalid amount",JOptionPane.ERROR_MESSAGE);}}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new CurrencyConverter().setVisible(true));}
}
