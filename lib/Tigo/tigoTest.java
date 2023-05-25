package lib.Tigo;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.fazecast.jSerialComm.SerialPort;
import javafiles.GUI;
import BankApi.*;
import java.sql.*;

public class tigoTest {
  private SerialPort serialPort;
  private BufferedReader input;

  private final GUI scherm;
  private int count;
  private int triesLeft = 3;
  private int wrongTries;
  private String codeString ="";
  private String amountString = "";
  private int codeInt;
  private int withdrawAmount;
  String iban = "";
  String land = "";
  String bank = "";
  String pass = "";

  static String url = "jdbc:mysql://145.24.222.188:8649/bank";
   static String username = "sqluser";
   static String password = "q76^UQT7!BcR";

  BankApiCommunication bankApi = new BankApiCommunication("LU", "BK");

  public tigoTest(GUI scherm) {
    this.scherm = scherm;
  }

  //code to initialize java and arduino
  public void initialize() {
    serialPort = SerialPort.getCommPort("COM8"); // Personal COM 6 for uno and 8 for mega
    serialPort.setBaudRate(9600);
    serialPort.openPort();
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 500, 0); // set timeout to 500ms
    input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
  }

  public void readData() {
    while (true) {
        try {
            // Check if there are bytes available to be read from the input buffer
            if (serialPort.bytesAvailable() > 0) {
                String inputLine = input.readLine();
                System.out.println(inputLine);
                //checks what screen is currently visible
                if (scherm.isCurrentScreen(0)) {
                    currentScreenZero(inputLine);
                } else if (scherm.isCurrentScreen(1)) {
                    currentScreenOne(inputLine);
                } else if (scherm.isCurrentScreen(2)){
                    currentScreenTwo(inputLine);
                }   else if (scherm.isCurrentScreen(3)){
                    currentScreenThree(inputLine);
                }  else if (scherm.isCurrentScreen(4)){
                    currentScreenFour(inputLine);
                } else if (scherm.isCurrentScreen(5)){
                    currentScreenFive(inputLine);
                } else if (scherm.isCurrentScreen(6)){
                    currentScreenSix(inputLine);
                } else if (scherm.isCurrentScreen(7)){
                    currentScreenSeven(inputLine);}
            }
                else {}
            
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}

public void updateSetPinNumbersEntered(int count){
  scherm.setPinNumbersEntered(count);
  SwingUtilities.invokeLater(() -> scherm.setCardDetectedScreen());
}

//code for homeScreen
public void currentScreenZero(String inputLine) throws IOException {
  if (inputLine.equals("pass found")) {
    iban = input.readLine();
    land = input.readLine();
    bank = input.readLine();
    
    System.out.println(iban + " is the iban");
    System.out.println(land + " is the land");
    System.out.println(bank + " is the bank");
    updateSetPinNumbersEntered(0);
  }
  else{}
}

//code for pinScreen
public void currentScreenOne(String inputLine) throws IOException {
  //checks what the input is
  if (inputLine.matches("[0-9]")) {
      if (count < 4) {
          count++;
          updateSetPinNumbersEntered(count);
          logIn(inputLine);
      }
  } else if (inputLine.equals("back")) {
      if (count > 0) {
          count--;
          updateSetPinNumbersEntered(count);
          logIn(inputLine);
      } 
  } else if (inputLine.equals("check")) {
      logIn(inputLine);
  } 
}

//Options
public void currentScreenTwo(String inputLine) throws IOException, InterruptedException{
    if (inputLine.equals("a")){
        double balance = handleBalanceRequest(land, bank, iban, pass);
        SwingUtilities.invokeLater(() -> scherm.setCheckSaldoScreen());
        System.out.println(balance + " is the balance");
        scherm.retrieveSaldo(balance);
    }
    else if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
        sendText("done");
    }
    else if (inputLine.equals("c")){   
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    }
    else if (inputLine.equals("d")){
        withdrawAmount = 70;
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
}

//Quick option
public void currentScreenThree(String inputLine){
    if (inputLine.equals("a")){
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
    else if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
    }
    else if (inputLine.equals("c")){
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
    else if (inputLine.equals("d")){
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
}

//Saldo check
public void currentScreenFour(String inputLine) throws InterruptedException{
    if (inputLine != null){
        
        SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
    }
    else{}}

//Withdraw
public void currentScreenFive(String inputLine){
    if (inputLine.equals("d")){
        System.out.println(withdrawAmount + " is the withdraw amount");
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
    else if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
    } else if (inputLine.matches("[0-9]")) {
        amountString += inputLine;
        withdrawAmount =+ Integer.parseInt(amountString);
        scherm.transactionAmount(amountString);
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    } else if (inputLine.equals("back")){
        String newAmountString = amountString.substring(0, amountString.length() - 1);
            if (newAmountString.length() == 0) {
                withdrawAmount = 0;
                amountString = "";
            }
            else {
            amountString = newAmountString;
            }
        withdrawAmount = Integer.parseInt(amountString);
        scherm.transactionAmount(amountString);
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    }
}

public void currentScreenSix(String inputLine) throws IOException, InterruptedException{
    Double balanceAfterWithdraw = handleWithdrawRequest(land, bank, iban, pass ,withdrawAmount);
    System.out.println(balanceAfterWithdraw + " is the balance after withdraw");
    if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setGoodbyeScreen());
        sendText("done");

    }
    else if (inputLine.equals("d")){
        SwingUtilities.invokeLater(() -> scherm.setGoodbyeScreen());
        sendText("receipt");
    } 
    
}

public void sendText(String text) throws IOException, InterruptedException{
            try (Scanner input = new Scanner(System.in)) {
                String str = input.nextLine();
                System.out.println(text);
                Thread.sleep(1500);
                serialPort.getOutputStream().write(str.getBytes());
            }
            serialPort.getOutputStream().flush();
        }

public void currentScreenSeven(String inputLine){
    if (inputLine != null){
        SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
    }
    else{}
}

public void logIn(String inputLine){
        if (inputLine.equals("back")){
            
            String newAmountString = codeString.substring(0, codeString.length() - 1);
            if (newAmountString.length() == 0) {
                codeString = "";
            }
            else {
            codeString = newAmountString;
            }
        }
        else if (inputLine.equals("check") && count == 4){
        try {
            System.out.println(codeString);
            double accData = handleBalanceRequest(land, bank, iban, codeString);
            if (accData != -1.0) {
                pass = codeString;
                SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
            }
            else {
                wrongPin();
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
            System.out.println("Invalid input");
        }}
        else {
            codeString += inputLine;
        }
    }

    public void wrongPin(){
        if (triesLeft > 1) {
            count = 0;
            triesLeft--;
            wrongTries++;
            codeString = "";
            System.out.println("Je hebt nog " + triesLeft + " pogingen");
            scherm.attempsLeft(wrongTries);
            updateSetPinNumbersEntered(count);
        } else {
            SwingUtilities.invokeLater(() -> scherm.setHomeScreen());

        }   
    }

    
    
    Double handleBalanceRequest(String toCtry, String toBank, String acctNo, String pin){
        String apiResponse = bankApi.postApiRequest(toCtry, toBank, acctNo, pin, 0);
        if (apiResponse.equals("")){
            handleApiError("");
            return -1.0;
        }
        else if (apiResponse.equals(null)){
            handleApiError(apiResponse);
            return -1.0;
        } 
        else if (bankApi.checkIfError(apiResponse)){
            handleApiError(apiResponse);
            return -1.0;
        } 
        else {
            return bankApi.getBalanceFromJson(apiResponse);
        }
    }

    void handleApiError(String apiResponse){
        if (apiResponse.equals("LU: Account number not recognized")){
            // handle 
        } 
        else if (apiResponse.equals("LU: Account is blocked, contact BANK")){
            // handle 

        }
        else if (apiResponse.equals("LU: To many attempts, account is now blocked, contact BANK")){
            // handle 
        }
        else if (apiResponse.equals("LU: Not enough credit")){
            // handle 
        }
        else {
            System.out.println("F handleApiError: unexpected error: " + apiResponse);
        }
    }

    Double handleWithdrawRequest(String toCtry, String toBank, String acctNo, String pin, int amount){
        String apiResponse = bankApi.postApiRequest(toCtry, toBank, acctNo, pin, amount);
        if (apiResponse.equals("")){
            System.out.println("handleWithdrawRequest: Empty api response");
            handleApiError("");
            return -1.0;
        }
        else if (apiResponse.equals(null)){
            System.out.println("handleWithdrawRequest: received null (api offline?)");
            handleApiError("");
            return -1.0;
        }
        else if (bankApi.checkIfError(apiResponse)){
            System.out.println("handleWithdrawRequest: predefined error");
            handleApiError(apiResponse);
            return -1.0;
        } 
        else {
            return bankApi.getBalanceAfterWithdrawFromJson(apiResponse);
        }
    }

  public static void main(String[] args) {
    GUI scherm = new GUI();
    
    EventQueue.invokeLater(() -> {
        scherm.startup();
        scherm.updateTime();
    });
    tigoTest listener = new tigoTest(scherm);
    listener.initialize();
    Thread readDataThread = new Thread(() -> listener.readData());
    readDataThread.start();
  }
}
