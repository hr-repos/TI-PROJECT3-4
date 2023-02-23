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

        JLabel homescreenlabel1 = new JLabel();
        homescreenlabel1.setText("Voer uw pas in");
        homescreenlabel1.setFont(new Font(null, Font.PLAIN, 35));
        homescreenlabel1.setIcon(bankImage);
        homescreenlabel1.setVerticalTextPosition(JLabel.BOTTOM);
        homescreenlabel1.setHorizontalTextPosition(JLabel.CENTER);

        JLabel homescreenlabel2 = new JLabel();
        // code to be added for the date and time

        JPanel homescreen = new JPanel();
        homescreen.setBounds(500, 100, 500, 500);
        homescreen.add(homescreenlabel1);
        homescreen.setBackground(Color.white);
        
        // Frame waar de GUI in draait
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







        //Border border = BorderFactory.createLineBorder(Color.green, 3);

        // creating a label
        // JLabel label = new JLabel();
        // label.setText("Zie hier een bank");
        // label.setIcon(bankImage);
        // label.setHorizontalAlignment(JLabel.CENTER);
        // label.setVerticalTextPosition(JLabel.NORTH);
        // label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        // label.setBounds(0, 0, 500, 500);