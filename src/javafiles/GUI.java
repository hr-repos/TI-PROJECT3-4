package javafiles;

import java.awt.BorderLayout;
import java.awt.Color; // for using colors
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font; // for using fonts
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.JLabel;

public class GUI {
    private JFrame frame;
    private JLabel time, labelScanCard, labelWelkom, enterPinLabel, cardDetectedLabelRight, cardDetectedLabelLeft, cardDetectedLabel, pinNumbersEnterdLabel, attempsLabel, bankImageLabel, textMaakKeuzeLabel, textSaldoBekijkenLabel, textAfbrekenLabel, textAfbrekenLabel2, textGeldOpnemenLabel, textSnelOpnemenLabel, snel70Label, snel100Label, snel150Label, fastMoneyLabel, bankImageLabel2, bankImageLabel3, checkSaldoLabel, currentSaldoLabel, continueLabel, withdrawTextLabel, textAfbrekenLabel3, bankImageLabel4, withdrawContinuLabel, withdrawAmountLabel, receiptYesLabel, receiptNoLabel, receiptLabel, bankImageLabel5, goodbyeLabel, bankImageLabel6;
    private JPanel northPanel, centerPanel, westPanel, eastPanel, homescreen, cardDetectedScreen, cardDetectedScreenRight, cardDetectedScreenLeft, loggedInScreen, loggedInScreenLeft, loggedInScreenRight, bankImagePanel6, bankImagePanel5, fastMoneyLeft, fastMoneyRight, fastMoney, checkSaldo, withdrawPanel, withdrawPanelRight, withdrawPanelLeft, receiptPanel, receiptRightPanel, receiptLeftPanel, bankImagePanel4, goodbyePanel;
    private JButton backButton;
    private SimpleDateFormat formatter;
    private static ImageIcon bankImage = new ImageIcon("././img/banklogo.png");
    private static ImageIcon bankImageIcon = new ImageIcon("././img/banklogoRond.png");
    private String dateTime = "Hier moet de tijd komen";
    private String numbersPinEnterd = "_ - _ - _ - _";
    private String attempsLeft1 = "U heeft nog ";
    private String attempsLeft2 = " pogingen over";
    private String attempsLeft = " ";
    private String saldo = "Hier moet het saldo komen";
    private String amount = "0.00";
    private String amountString = "";

    private GridBagConstraints gbc = new GridBagConstraints();
    
    public static final int homeScreen = 0;
    public static final int cardScreen = 1;
    public static final int loggedinScreen = 2;
    public static final int quickOptionScreen = 3;
    public static final int checkSaldoScreen = 4;
    public static final int withdrawScreen = 5;
    public static final int receiptScreen = 6;
    public static final int goodbyeScreen = 7;

    private int currentScreen = homeScreen;

    public void switchToScreen(int screen) {
        // Switch to the specified screen
        currentScreen = screen;
    }

    public boolean isCurrentScreen(int screen) {
        // Check if the current screen matches the specified screen
        if (currentScreen == screen){
            return true;
        }
        return false;
    }

    private void clearScreen(){
        centerPanel.removeAll();
        westPanel.removeAll();
        eastPanel.removeAll();
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
        updateTime();
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
        centerPanel.setBackground(Color.white); 
        eastPanel = new JPanel();
        eastPanel.setLayout(new GridBagLayout());
        eastPanel.setBackground(Color.white); 

        westPanel = new JPanel(new GridBagLayout());
        westPanel.setBackground(Color.white); 
        
        formatter = new SimpleDateFormat("dd-MM-yyyy '  ' HH:mm   ");
        time = new JLabel();
        time.setFont(new Font(null, Font.BOLD, 25));
        time.setText(dateTime);
        time.setForeground(Color.WHITE);
        
        bankImageLabel = new JLabel(bankImage);
        bankImageLabel2 = new JLabel(bankImage);
        bankImageLabel3 = new JLabel(bankImage);
        bankImageLabel4 = new JLabel(bankImage);
        bankImageLabel5 = new JLabel(bankImage);
        bankImageLabel6 = new JLabel(bankImage);

        
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

        cardDetectedLabelLeft = new JLabel("Druk * om terug te gaan");
        cardDetectedLabelLeft.setVerticalTextPosition(JLabel.TOP);
        cardDetectedLabelLeft.setHorizontalTextPosition(JLabel.CENTER);
        cardDetectedLabelLeft.setFont(new Font(null, Font.PLAIN, 40));
        cardDetectedLabelLeft.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardDetectedLabelRight = new JLabel("Druk # om verder te gaan");
        cardDetectedLabelRight.setVerticalTextPosition(JLabel.TOP);
        cardDetectedLabelRight.setHorizontalTextPosition(JLabel.CENTER);
        cardDetectedLabelRight.setFont(new Font(null, Font.PLAIN, 40));
        cardDetectedLabelRight.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        cardDetectedScreenLeft = new JPanel();
        cardDetectedScreenLeft.setLayout(new BoxLayout(cardDetectedScreenLeft, BoxLayout.Y_AXIS));
        cardDetectedScreenLeft.add(cardDetectedLabelLeft, BorderLayout.NORTH);
        cardDetectedScreenLeft.setBackground(Color.white);

        cardDetectedScreenRight = new JPanel();
        cardDetectedScreenRight.setLayout(new BoxLayout(cardDetectedScreenRight, BoxLayout.Y_AXIS));
        cardDetectedScreenRight.add(cardDetectedLabelRight, BorderLayout.NORTH);
        cardDetectedScreenRight.setBackground(Color.white);


        // onderdelen voor het scherm wanneer er ingelogd is
        // wordt toegevoegd aan west panel
        textSaldoBekijkenLabel = new JLabel("<-- Saldo bekijken");
        textSaldoBekijkenLabel.setFont(new Font(null, Font.PLAIN, 40));
        textAfbrekenLabel = new JLabel("<-- Afbreken");
        textAfbrekenLabel.setFont(new Font(null, Font.PLAIN, 40));

        loggedInScreenLeft = new JPanel();
        loggedInScreenLeft.setLayout(new BoxLayout(loggedInScreenLeft, BoxLayout.Y_AXIS));
        loggedInScreenLeft.add(textSaldoBekijkenLabel);
        loggedInScreenLeft.add(Box.createVerticalStrut(300)); // add some vertical space between the labels
        loggedInScreenLeft.add(textAfbrekenLabel);
        loggedInScreenLeft.setBackground(Color.white);

        // wordt toegevoegd aan east panel
        textGeldOpnemenLabel = new JLabel("Geld opnemen -->");
        textGeldOpnemenLabel.setFont(new Font(null, Font.PLAIN, 40));
        textSnelOpnemenLabel = new JLabel("Snel opnemen -->");
        textSnelOpnemenLabel.setFont(new Font(null, Font.PLAIN, 40));

        loggedInScreenRight = new JPanel();
        loggedInScreenRight.setLayout(new BoxLayout(loggedInScreenRight, BoxLayout.Y_AXIS));
        loggedInScreenRight.setBackground(Color.white);
        loggedInScreenRight.add(textGeldOpnemenLabel);
        loggedInScreenRight.add(Box.createVerticalStrut(300)); // add some vertical space between the labels
        loggedInScreenRight.add(textSnelOpnemenLabel);

        frame.add(loggedInScreenLeft);
        frame.add(loggedInScreenRight);

        // wordt toegevoegd aan centerpanel
        textMaakKeuzeLabel = new JLabel("Welkom, maak uw keuze");
        textMaakKeuzeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textMaakKeuzeLabel.setFont(new Font(null, Font.PLAIN, 60));

        loggedInScreen = new JPanel();
        loggedInScreen.setLayout(new BorderLayout());
        loggedInScreen.setBackground(Color.white);
        loggedInScreen.add(bankImageLabel, BorderLayout.NORTH);
        loggedInScreen.add(textMaakKeuzeLabel, BorderLayout.CENTER);
        loggedInScreen.setBackground(Color.white);

        //Set panels for fast Options

        fastMoneyLabel = new JLabel("Hoeveel geld wilt u pinnen?");
        fastMoneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fastMoneyLabel.setFont(new Font(null, Font.PLAIN, 60));

        fastMoney = new JPanel();
        fastMoney.setLayout(new BorderLayout());
        fastMoney.setBackground(Color.white);
        fastMoney.add(bankImageLabel2, BorderLayout.NORTH);
        fastMoney.add(fastMoneyLabel, BorderLayout.CENTER);

        snel70Label = new JLabel("  70 euro -->");
        snel70Label.setFont(new Font(null, Font.PLAIN, 40));
        snel150Label = new JLabel("150 euro -->");
        snel150Label.setFont(new Font(null, Font.PLAIN, 40));

        fastMoneyRight = new JPanel();
        fastMoneyRight.setLayout(new BoxLayout(fastMoneyRight, BoxLayout.Y_AXIS));
        fastMoneyRight.setBackground(Color.white);
        fastMoneyRight.add(snel70Label);
        fastMoneyRight.add(Box.createVerticalStrut(300)); // add some vertical space between the labels
        fastMoneyRight.add(snel150Label);

        snel100Label = new JLabel("<-- 100 euro");
        snel100Label.setFont(new Font(null, Font.PLAIN, 40));
        textAfbrekenLabel2 = new JLabel("<-- Afbreken");
        textAfbrekenLabel2.setFont(new Font(null, Font.PLAIN, 40));

        fastMoneyLeft = new JPanel();
        fastMoneyLeft.setLayout(new BoxLayout(fastMoneyLeft, BoxLayout.Y_AXIS));
        fastMoneyLeft.setBackground(Color.white);
        fastMoneyLeft.add(snel100Label);
        fastMoneyLeft.add(Box.createVerticalStrut(300)); // add some vertical space between the labels
        fastMoneyLeft.add(textAfbrekenLabel2);

        //The panels for the saldo check
        checkSaldoLabel = new JLabel("Dit staat er op uw rekening");
        checkSaldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkSaldoLabel.setFont(new Font(null, Font.PLAIN, 60));

        continueLabel = new JLabel("Druk op een knop om verder te gaan");
        continueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueLabel.setFont(new Font(null, Font.PLAIN, 30));

        JPanel bankImagePanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankImagePanel3.setBackground(Color.WHITE);
        bankImagePanel3.add(bankImageLabel3);

        currentSaldoLabel = new JLabel();
        currentSaldoLabel.setText(saldo);
        currentSaldoLabel.setFont(new Font(null, Font.PLAIN, 30));
        currentSaldoLabel.setVerticalTextPosition(JLabel.BOTTOM);
        currentSaldoLabel.setHorizontalTextPosition(JLabel.CENTER);
        currentSaldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkSaldo = new JPanel();
        checkSaldo.setLayout(new BoxLayout(checkSaldo, BoxLayout.Y_AXIS));
        checkSaldo.setBackground(Color.white);
        checkSaldo.add(bankImagePanel3, BorderLayout.NORTH);
        checkSaldo.add(checkSaldoLabel, BorderLayout.CENTER);
        checkSaldo.add(Box.createVerticalStrut(100));
        checkSaldo.add(currentSaldoLabel, BorderLayout.SOUTH);
        checkSaldo.add(Box.createVerticalStrut(100));
        checkSaldo.add(continueLabel, BorderLayout.SOUTH);
       
        //Panels for the withdraw screen
        bankImagePanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankImagePanel4.setBackground(Color.WHITE);
        bankImagePanel4.add(bankImageLabel4);
        withdrawTextLabel = new JLabel("Hoeveel wilt u pinnen");
        withdrawTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawTextLabel.setFont(new Font(null, Font.PLAIN, 60));

        withdrawAmountLabel = new JLabel(amount);
        withdrawAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawAmountLabel.setFont(new Font(null, Font.PLAIN, 40));
        

        withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));
        withdrawPanel.setBackground(Color.white);
        withdrawPanel.add(bankImagePanel4);
        withdrawPanel.add(withdrawTextLabel);
        withdrawPanel.add(Box.createVerticalStrut(100));
        withdrawPanel.add(withdrawAmountLabel);

        withdrawContinuLabel = new JLabel("Ga door -->");
        withdrawContinuLabel.setFont(new Font(null, Font.PLAIN, 40));

        withdrawPanelRight = new JPanel();
        withdrawPanelRight.setLayout(new BoxLayout(withdrawPanelRight, BoxLayout.Y_AXIS));
        withdrawPanelRight.setBackground(Color.white);
        withdrawPanelRight.add(Box.createVerticalStrut(352));
        withdrawPanelRight.add(withdrawContinuLabel);

        textAfbrekenLabel3 = new JLabel("<-- Afbreken");
        textAfbrekenLabel3.setFont(new Font(null, Font.PLAIN, 40));

        withdrawPanelLeft = new JPanel();
        withdrawPanelLeft.setLayout(new BoxLayout(withdrawPanelLeft, BoxLayout.Y_AXIS));
        withdrawPanelLeft.setBackground(Color.white);
        withdrawPanelLeft.add(Box.createVerticalStrut(352));
        withdrawPanelLeft.add(textAfbrekenLabel3);

        receiptLabel = new JLabel("Wilt u een bon hebben?");
        receiptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiptLabel.setFont(new Font(null, Font.PLAIN, 60));

        bankImagePanel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankImagePanel5.setBackground(Color.WHITE);
        bankImagePanel5.add(bankImageLabel5);

        receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBackground(Color.white);
        receiptPanel.add(bankImagePanel5);
        receiptPanel.add(receiptLabel);

        receiptNoLabel = new JLabel("<-- Nee");
        receiptNoLabel.setFont(new Font(null, Font.PLAIN, 40));

        receiptYesLabel = new JLabel("   Ja -->");
        receiptYesLabel.setFont(new Font(null, Font.PLAIN, 40));

        receiptRightPanel = new JPanel();
        receiptRightPanel.setLayout(new BoxLayout(receiptRightPanel, BoxLayout.Y_AXIS));
        receiptRightPanel.setBackground(Color.white);
        receiptRightPanel.add(Box.createVerticalStrut(352));
        receiptRightPanel.add(receiptYesLabel);

        receiptLeftPanel = new JPanel();
        receiptLeftPanel.setLayout(new BoxLayout(receiptLeftPanel, BoxLayout.Y_AXIS));
        receiptLeftPanel.setBackground(Color.white);
        receiptLeftPanel.add(Box.createVerticalStrut(352));
        receiptLeftPanel.add(receiptNoLabel);

        bankImagePanel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankImagePanel6.setBackground(Color.WHITE);
        bankImagePanel6.add(bankImageLabel6);

        goodbyeLabel = new JLabel("Vaarwel en tot ziens");
        goodbyeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        goodbyeLabel.setFont(new Font(null, Font.PLAIN, 60));


        goodbyePanel = new JPanel();
        goodbyePanel.setLayout(new BoxLayout(goodbyePanel, BoxLayout.Y_AXIS));
        goodbyePanel.setBackground(Color.white);
        goodbyePanel.add(bankImagePanel6);
        goodbyePanel.add(goodbyeLabel);

    }

    private void startGui() {
        northPanel.add(time, BorderLayout.EAST);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(westPanel, BorderLayout.WEST);
    }

    public void setHomeScreen() {
        clearScreen();
        centerPanel.add(homescreen);
        switchToScreen(0);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setCardDetectedScreen() {
        clearScreen();
        centerPanel.add(cardDetectedScreen);
        westPanel.add(cardDetectedScreenLeft);
        eastPanel.add(cardDetectedScreenRight);
        switchToScreen(1);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setLoggedInScreen(){
        clearScreen();
        amount = "0.00";
        amountString = "";
        centerPanel.add(loggedInScreen);
        westPanel.add(loggedInScreenLeft, gbc);
        westPanel.setAlignmentY(JLabel.CENTER);
        eastPanel.add(loggedInScreenRight);
        eastPanel.setAlignmentY(JLabel.CENTER);
        switchToScreen(2);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setFastMoneyScreen(){
        clearScreen();
        centerPanel.add(fastMoney);
        eastPanel.add(fastMoneyRight);
        westPanel.add(fastMoneyLeft);
        switchToScreen(3);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setCheckSaldoScreen(){
        clearScreen();
        centerPanel.add(checkSaldo);
        switchToScreen(4);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setWithdrawScreen(){
        withdrawAmountLabel.setText(amount);
        clearScreen(); 
        westPanel.add(withdrawPanelLeft);
        centerPanel.add(withdrawPanel);
        eastPanel.add(withdrawPanelRight);
        switchToScreen(5);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setReceiptScreen(){
        clearScreen();
        centerPanel.add(receiptPanel);
        eastPanel.add(receiptRightPanel);
        westPanel.add(receiptLeftPanel);
        switchToScreen(6);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setGoodbyeScreen(){
        clearScreen();
        centerPanel.add(goodbyePanel);
        switchToScreen(7);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void setPinNumbersEntered(int totalNumbersEnterd){
        if (totalNumbersEnterd == 0) {
            numbersPinEnterd = "_ - _ - _ - _";
        }
        else if (totalNumbersEnterd == 1) {
            numbersPinEnterd = "X - _ - _ - _";
        }
        else if (totalNumbersEnterd == 2) {
            numbersPinEnterd = "X - X - _ - _";
        }
        else if (totalNumbersEnterd == 3) {
            numbersPinEnterd = "X - X - X - _";
        }
        else if (totalNumbersEnterd == 4) {
            numbersPinEnterd = "X - X - X - X";
        }
        else {
            System.out.println("ERROR: Aantal ingetoetste getallen moet vallen binnen 0 t/m 4");
            System.out.println("No changes were made!");
        }
        pinNumbersEnterdLabel.setText(numbersPinEnterd);
    }

    public void attempsLeft(int attemps){
        int attempsRemaining = 3 - attemps;

        String attempsLeft = attempsLeft1 + attempsRemaining + attempsLeft2;
        attempsLabel.setText(attempsLeft);
    }

    public void retrieveSaldo(Double moneyOnAcc){
        String moneyOnAccString = String.format("%.2f", moneyOnAcc);
        currentSaldoLabel.setText(moneyOnAccString);
    }

    public void transactionAmount(String amountInput) {
        if (amountInput.equals("*")) {
            String newAmountString = amountString.substring(0, amountString.length() - 1);
            if (newAmountString.length() == 0) {
                amount = "0.00";
                amountString = "";
            }
            else {
            double amountDouble = Double.parseDouble(newAmountString);
            amountDouble /= 100.0;
            amount = String.format("%.2f", amountDouble); // Format the amount to 2 decimal places
            withdrawAmountLabel.setText(amount);
            amountString = newAmountString;
            }
        }
        else {
        amountString += amountInput;
        try {
            double amountDouble = Double.parseDouble(amountString);
            amountDouble /= 100.0;
            amount = String.format("%.2f", amountDouble); // Format the amount to 2 decimal places

            withdrawAmountLabel.setText(amount);
        } catch (NumberFormatException e) {
            // Handle invalid input
            withdrawAmountLabel.setText("Invalid input");
        }
    }
    }
}
