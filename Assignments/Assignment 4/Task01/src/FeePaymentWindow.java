import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

class FeePaymentService {
    String pay(String reg, BigDecimal amount) {
        if (reg == null || reg.isBlank()) throw new IllegalArgumentException("Register number is required.");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive.");
        return "PAY-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
}
public class FeePaymentWindow extends JFrame {
    private final JTextField reg=new JTextField(12), amount=new JTextField(10);
    private final JLabel status=new JLabel("Enter payment details");
    private final JButton receipt=new JButton("Receipt");
    private String receiptId;
    private final FeePaymentService service=new FeePaymentService();
    public FeePaymentWindow() {
        super("Student Fee Payment");
        JPanel form=new JPanel(new GridLayout(2,2,8,8));
        form.add(new JLabel("Register number")); form.add(reg);
        form.add(new JLabel("Amount (INR)")); form.add(amount);
        JButton pay=new JButton("Pay");
        JPanel actions=new JPanel(); actions.add(pay); actions.add(receipt);
        add(status,BorderLayout.NORTH); add(form,BorderLayout.CENTER); add(actions,BorderLayout.SOUTH);
        receipt.setEnabled(false); pay.addActionListener(e->pay()); receipt.addActionListener(e->showReceipt());
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(400,180); setLocationRelativeTo(null);
    }
    private void pay() {
        final BigDecimal value;
        try {
            if(reg.getText().trim().isEmpty()) throw new IllegalArgumentException("Register number is required.");
            value=new BigDecimal(amount.getText().trim());
            if(value.signum()<=0) throw new IllegalArgumentException("Amount must be positive.");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Invalid input",JOptionPane.ERROR_MESSAGE); return;
        }
        setBusy(true);
        new SwingWorker<String,Void>() {
            protected String doInBackground(){return service.pay(reg.getText().trim(),value);}
            protected void done(){try{receiptId=get();receipt.setEnabled(true);status.setText("Payment successful: "+receiptId);}
                catch(Exception ex){status.setText("Payment failed");JOptionPane.showMessageDialog(FeePaymentWindow.this,ex.getMessage());}
                finally{setBusy(false);}}
        }.execute();
    }
    private void showReceipt(){if(receiptId!=null)JOptionPane.showMessageDialog(this,"Receipt "+receiptId+"\nStudent "+reg.getText()+"\nINR "+amount.getText());}
    private void setBusy(boolean b){setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new FeePaymentWindow().setVisible(true));}
}
