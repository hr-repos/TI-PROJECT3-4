package imageswitch;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
	  
        //Declaratie van de JFram, een JPanel met een CardLayout, twee Panels om als cards te fungeren, en zet alle LayoutManagers.
        JFrame frame = new JFrame("Demoframe");
        frame.setLayout(new FlowLayout());
        JPanel cards = new JPanel(new CardLayout());
        JPanel card1 = new ImagePanel("muppet1.PNG");
        JPanel card2 = new ImagePanel("muppet2.PNG");
        card1.setLayout(new FlowLayout());
        card2.setLayout(new FlowLayout());
        
        //Declaratie van components behorende bij panels.
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JTextField txt = new JTextField("start tekst");
        JLabel label1 = new JLabel("This is card 1");
        JLabel label2 = new JLabel("This is card 2");
        
        //Toevoegen van ActionListeners voor beide knoppen; code word uitgevoerd wanneer op de knop geklikt wordt. 
        button1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.last(cards);
            txt.setText("Switched to card 2");
          }
        });
        button2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.first(cards);
            txt.setText("Switched to card 1");
          }
        });
        
        //Zet components in een lijst voor card1 en een lijst voor card2, voeg via for-loop components aan hun respectievelijke card toe.
        Component[] comp1 = { label1, button1 };
        Component[] comp2 = { label2, button2 };
        for (Component comp : comp1) {
          card1.add(comp);
        }
        for (Component comp : comp2) {
          card2.add(comp);
        }
        
        //Voeg card1 en card2 toe aan de panel met de CardLayout.
        cards.add(card1);
        cards.add(card2);
        
        //Voeg de panel met de cardlayout en overige components toe aan de JFrame.
        frame.add(cards);
        frame.add(txt);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
      }
}
