package javafiles;

import java.awt.BorderLayout;
import java.awt.Color; // for using colors
import java.awt.Dimension;
import java.awt.Font; // for using fonts
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

public class GUI {
    private JFrame frame;
    private JLabel time, labelScanCard, labelWelkom, enterPinLabel, cardDetectedLabel, pinNumbersEnterdLabel, attempsLabel;
    private JPanel northPanel, centerPanel, homescreen, cardDetectedScreen;
    private JButton backButton;
    private SimpleDateFormat formatter;
    private static ImageIcon bankImage = new ImageIcon("././img/banklogo.png");
    private static ImageIcon bankImageIcon = new ImageIcon("././img/banklogoRond.png");
    private String dateTime = "Hier moet de tijd komen";
    private String numbersPinEnterd = "_ - _ - _ - _";
    private String attempsLeft1 = "U heeft nog ";
    private String attempsLeft2 = " pogingen over";
    private String attempsLeft = "U heeft nog 3 pogingen over";

    public static final int homeScreen = 0;
    public static final int cardScreen = 1;

    private int currentScreen = homeScreen;

    public void switchToScreen(int screen) {
        // Switch to the specified screen
        currentScreen = screen;
    }

    public boolean isCurrentScreen(int screen) {
        // Check if the current screen matches the specified screen
        return currentScreen == screen;
    }

    private void clearScreen(){
        centerPanel.remove(homescreen);
        centerPanel.remove(cardDetectedScreen);
    }

    public void updateTime(){
        Date date = new Date(System.currentTimeMillis());
        dateTime = (formatter.format(date));
        time.setText(dateTime);
        SwingUtilities.updateComponentTreeUI(time);
    }
    
    public void startup(){
        initialize();
        startGui();
        setHomeScreen();
    }

    private void initialize(){
        // onderdelen die in meerdere schermen nodig zijn
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 800);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(110, 136, 241)); // north panel
        northPanel.setPreferredSize(new Dimension(100, 50));
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.white); // center panel
        centerPanel.setPreferredSize(new Dimension(100, 100));
        
        formatter = new SimpleDateFormat("dd-MM-yyyy '  ' HH:mm   ");
        time = new JLabel();
        time.setFont(new Font(null, Font.BOLD, 25));
        time.setText(dateTime);
        time.setForeground(Color.WHITE);
        
        backButton = new JButton();

        
        // onderdelen voor het hoofdscherm
        labelScanCard = new JLabel("Scan uw pas om te beginnen");
        labelScanCard.setFont(new Font(null, Font.PLAIN, 35));
        labelScanCard.setIcon(bankImage);
        labelScanCard.setVerticalTextPosition(JLabel.BOTTOM);
        labelScanCard.setHorizontalTextPosition(JLabel.CENTER);

        labelWelkom = new JLabel("Welkom bij Bank");
        labelWelkom.setVerticalTextPosition(JLabel.TOP);
        labelWelkom.setHorizontalTextPosition(JLabel.CENTER);
        labelWelkom.setFont(new Font(null, Font.PLAIN, 60));

        homescreen = new JPanel(new BorderLayout());
        homescreen.add(labelScanCard, BorderLayout.CENTER);
        homescreen.add(labelWelkom, BorderLayout.NORTH);
        homescreen.setBackground(Color.white);


        // onderdelen voor het scherm wanneer een pas gedetecteerd is
        enterPinLabel = new JLabel("Voer uw pincode in");
        enterPinLabel.setFont(new Font(null, Font.PLAIN, 35));
        enterPinLabel.setIcon(bankImage);
        enterPinLabel.setVerticalTextPosition(JLabel.BOTTOM);
        enterPinLabel.setHorizontalTextPosition(JLabel.CENTER);
        enterPinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardDetectedLabel = new JLabel("Uw pas is gedetecteerd");
        cardDetectedLabel.setVerticalTextPosition(JLabel.TOP);
        cardDetectedLabel.setHorizontalTextPosition(JLabel.CENTER);
        cardDetectedLabel.setFont(new Font(null, Font.PLAIN, 60));
        cardDetectedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        pinNumbersEnterdLabel = new JLabel();
        pinNumbersEnterdLabel.setText(numbersPinEnterd);
        pinNumbersEnterdLabel.setFont(new Font(null, Font.PLAIN, 30));
        pinNumbersEnterdLabel.setVerticalTextPosition(JLabel.BOTTOM);
        pinNumbersEnterdLabel.setHorizontalTextPosition(JLabel.CENTER);
        pinNumbersEnterdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        attempsLabel = new JLabel();
        attempsLabel.setText(attempsLeft);
        attempsLabel.setFont(new Font(null, Font.PLAIN, 30));
        attempsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        attempsLabel.setHorizontalTextPosition(JLabel.CENTER);
        attempsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardDetectedScreen = new JPanel();
        cardDetectedScreen.setLayout(new BoxLayout(cardDetectedScreen, BoxLayout.Y_AXIS));
        cardDetectedScreen.add(cardDetectedLabel, BorderLayout.NORTH);
        cardDetectedScreen.add(enterPinLabel, BorderLayout.CENTER);
        cardDetectedScreen.add(pinNumbersEnterdLabel, BorderLayout.SOUTH);
        cardDetectedScreen.add(attempsLabel, BorderLayout.SOUTH);
        cardDetectedScreen.setBackground(Color.white);


    }

    private void startGui() {
        northPanel.add(time, BorderLayout.EAST);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
    }

    public void setHomeScreen() {
        clearScreen();
        centerPanel.add(homescreen);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setCardDetectedScreen() {
        centerPanel.removeAll();
        centerPanel.add(cardDetectedScreen);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setPinNumbersEntered(int totalNumbersEnterd){
        if (totalNumbersEnterd == 0) {
            numbersPinEnterd = "_ - _ - _ - _";
            pinNumbersEnterdLabel.setText(numbersPinEnterd);
        }
        else if (totalNumbersEnterd == 1) {
            numbersPinEnterd = "X - _ - _ - _";
            pinNumbersEnterdLabel.setText(numbersPinEnterd);
        }
        else if (totalNumbersEnterd == 2) {
            numbersPinEnterd = "X - X - _ - _";
            pinNumbersEnterdLabel.setText(numbersPinEnterd);
        }
        else if (totalNumbersEnterd == 3) {
            numbersPinEnterd = "X - X - X - _";
            pinNumbersEnterdLabel.setText(numbersPinEnterd);
        }
        else if (totalNumbersEnterd == 4) {
            numbersPinEnterd = "X - X - X - X";
            pinNumbersEnterdLabel.setText(numbersPinEnterd);
        }
        else {
            System.out.println("ERROR: Aantal ingetoetste getallen moet vallen binnen 0 t/m 4");
            System.out.println("No changes were made!");

        }
    }

    public void attempsLeft(int attemps){
        int attempsRemaining = 3 - attemps;

        String attempsLeft = attempsLeft1 + attempsRemaining + attempsLeft2;
        attempsLabel.setText(attempsLeft);
    }
}
