package javafiles;           
// for setting an png as top left icon
import javax.swing.*;
import java.awt.Color;          // for using colors
import java.awt.Font;           // for using fonts
import javax.swing.border.Border;

public class Main {
    void createFrame(){
        
    }

    public static void main(String[] args) {
        ImageIcon bankImage = new ImageIcon("././img/banklogo.png");
        ImageIcon bankImageIcon = new ImageIcon("././img/banklogoRond.png");
        //Border border = BorderFactory.createLineBorder(Color.green, 3);

        // creating a label
        // JLabel label = new JLabel();
        // label.setText("Zie hier een bank");
        // label.setIcon(bankImage);
        // label.setHorizontalAlignment(JLabel.CENTER);
        // label.setVerticalTextPosition(JLabel.NORTH);
        // label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        // label.setBounds(0, 0, 500, 500);
        JLabel banklogo = new JLabel();
        banklogo.setIcon(bankImage);
        banklogo.setHorizontalAlignment(JLabel.CENTER);
        //banklogo.setBackground(null);
        JPanel homescreen = new JPanel();
        homescreen.setBounds(0, 0, 500, 500);
        homescreen.add(banklogo);
        //homescreen.setBackground(Color.white);







        
        JFrame frame = new JFrame();
        frame.setIconImage(bankImageIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Bank GUI");
        frame.setResizable(true);
        frame.setVisible(true);   
        frame.setLayout(null); 
        frame.setSize(1900, 1000);
        frame.getContentPane().setBackground(new Color(255, 255, 255));      // 123, 50, 250
        //frame.add(label);
        frame.add(homescreen);
    }
}