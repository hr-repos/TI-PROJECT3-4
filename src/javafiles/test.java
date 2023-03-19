package javafiles;

import java.awt.BorderLayout;
import java.awt.Color; // for using colors
import java.awt.Dimension;
import java.awt.Font; // for using fonts
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class test {
    static ImageIcon bankImage = new ImageIcon("././img/banklogo.png");
    static ImageIcon bankImageIcon = new ImageIcon("././img/banklogoRond.png");

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        JLabel homescreenlabel1 = new JLabel();
        homescreenlabel1.setText("Voer uw pas in");
        homescreenlabel1.setFont(new Font(null, Font.PLAIN, 35));
        homescreenlabel1.setIcon(bankImage);
        homescreenlabel1.setVerticalTextPosition(JLabel.BOTTOM);
        homescreenlabel1.setHorizontalTextPosition(JLabel.CENTER);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel5.add(homescreenlabel1);

        panel1.setBackground(new Color(110, 136, 241));
        panel2.setBackground(Color.green);
        panel3.setBackground(Color.yellow);
        panel4.setBackground(Color.magenta);
        panel5.setBackground(Color.white);

        panel1.setPreferredSize(new Dimension(100, 50));
        panel2.setPreferredSize(new Dimension(100, 100));
        panel3.setPreferredSize(new Dimension(100, 100));
        panel4.setPreferredSize(new Dimension(100, 100));
        panel5.setPreferredSize(new Dimension(100, 100));

        frame.add(panel1, BorderLayout.NORTH);
        // frame.add(panel2,BorderLayout.WEST);
        // frame.add(panel3,BorderLayout.EAST);
        // frame.add(panel4,BorderLayout.SOUTH);
        frame.add(panel5, BorderLayout.CENTER);
    }
}
