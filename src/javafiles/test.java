package javafiles;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
//w ww  .j  a v  a 2s  .c  o m
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * test
 */
public class test {
    
    public static void main(String[] args) {
        test2 test = new test2();
        test.createGUI();
    }
}

class test2{
    public void createGUI(){
        JFrame frame = new JFrame();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label1 = new JLabel("label1");
        JLabel label2 = new JLabel("label1daSfewqVFGDSAVGDSAFVSDAF");
        JLabel label3 = new JLabel("label1");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new BorderLayout());
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.add(label1);
        panel1.add(label2);
        panel1.add(label3);
        frame.add(panel1, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}



// public class test {
//   public static void main(String[] args) {
//     JFrame frame = new JFrame();
//     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     frame.getContentPane().setLayout(
//         new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

//     JPanel a = new JPanel();
//     a.setAlignmentX(Component.CENTER_ALIGNMENT);
//     a.setPreferredSize(new Dimension(100, 100));
//     a.setMaximumSize(new Dimension(100, 100)); // set max = pref
//     a.setBorder(BorderFactory.createTitledBorder("aa"));
//     JPanel b = new JPanel();
//     b.setAlignmentX(Component.CENTER_ALIGNMENT);
//     b.setPreferredSize(new Dimension(50, 50));
//     b.setMaximumSize(new Dimension(50, 50)); // set max = pref
//     b.setBorder(BorderFactory.createTitledBorder("bb"));

//     frame.getContentPane().add(a);
//     frame.getContentPane().add(b);
//     frame.pack();
//     frame.setVisible(true);
//   }
// }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// public class test {
//     public static void addComponentsToPane(Container pane) {
//         pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
 
//         addAButton("Button 1", pane);
//         addAButton("Button 2", pane);
//         addAButton("Button 3", pane);
//         addAButton("Long-Named Button 4", pane);
//         addAButton("5", pane);
//     }
 
//     private static void addAButton(String text, Container container) {
//         JButton button = new JButton(text);
//         button.setAlignmentX(Component.CENTER_ALIGNMENT);
//         container.add(button);
//     }
 
//     /**
//      * Create the GUI and show it.  For thread safety,
//      * this method should be invoked from the
//      * event-dispatching thread.
//      */
//     private static void createAndShowGUI() {
//         //Create and set up the window.
//         JFrame frame = new JFrame("BoxLayoutDemo");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
//         //Set up the content pane.
//         addComponentsToPane(frame.getContentPane());
 
//         //Display the window.
//         frame.pack();
//         frame.setVisible(true);
//     }
 
//     public static void main(String[] args) {
//         //Schedule a job for the event-dispatching thread:
//         //creating and showing this application's GUI.
//         javax.swing.SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 createAndShowGUI();
//             }
//         });
//     }
// }