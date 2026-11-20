import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class DrawingPad extends JFrame {
 private final java.util.List<Point> points=new ArrayList<>();
 private final JLabel count=new JLabel("Clicks: 0");
 private final JPanel canvas=new JPanel(){
  @Override protected void paintComponent(Graphics g){super.paintComponent(g);g.setColor(new Color(20,100,190));for(Point p:points)g.fillOval(p.x-5,p.y-5,10,10);}
 };
 public DrawingPad(){super("Drawing Pad");canvas.setBackground(Color.WHITE);
  canvas.addMouseListener(new MouseAdapter(){@Override public void mouseClicked(MouseEvent e){points.add(e.getPoint());count.setText("Clicks: "+points.size());canvas.repaint();}});
  add(canvas,BorderLayout.CENTER);add(count,BorderLayout.SOUTH);setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(600,420);setLocationRelativeTo(null);}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new DrawingPad().setVisible(true));}
}
