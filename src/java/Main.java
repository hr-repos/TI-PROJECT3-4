package java;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Workshop frame");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame flowLayoutPanel = new JFrame();
        JFrame boxLayoutPanel = new JFrame();
        JFrame gridLayoutPanel = new JFrame();

        flowLayoutPanel.setLayout(new FlowLayout());
        boxLayoutPanel.setLayout(new BoxLayout(boxLayoutPanel, BoxLayout.Y_AXIS));
        gridLayoutPanel.setLayout(new GridLayout(3, 2));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton[] buttons1 = {
            new JButton("button 1"),
            new JButton("button 2"),
            new JButton("button 3"),
            new JButton("button 4"),
            new JButton("button 5")
        };

        JButton[] buttons2 = {
            new JButton("button 1"),
            new JButton("button 2"),
            new JButton("button 3"),
            new JButton("button 4"),
            new JButton("button 5")
        };

        JButton[] buttons3 = {
            new JButton("button 1"),
            new JButton("button 2"),
            new JButton("button 3"),
            new JButton("button 4"),
            new JButton("button 5")
        };

        for (JButton b : buttons1){
            flowLayoutPanel.add(b);
        }

        for (JButton b : buttons2){
            flowLayoutPanel.add(b);
        }

        for (JButton b : buttons3){
            flowLayoutPanel.add(b);
        }

        mainPanel.add(flowLayoutPanel);
        mainPanel.add(boxLayoutPanel);
        mainPanel.add(gridLayoutPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
}