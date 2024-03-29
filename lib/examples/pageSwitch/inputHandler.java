package lib.examples.pageSwitch;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.fazecast.jSerialComm.SerialPort;
import javafiles.GUI;

public class inputHandler {
  private SerialPort serialPort;
  private BufferedReader input;

  private final GUI scherm;
  private int count;
  private int triesLeft = 3;
  private int wrongTries;

  public inputHandler(GUI scherm) {
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
public void currentScreenZero(String inputLine) {
  if (inputLine.equals("pass found")) {
      updateSetPinNumbersEntered(0); 
  }
  else{}
}

//code for pinScreen
public void currentScreenOne(String inputLine) throws IOException {
  //checks what the input is
  if (inputLine.equals("plus")) {
      if (count < 5) {
          count++;
          updateSetPinNumbersEntered(count);
      }
  } else if (inputLine.equals("minus")) {
      if (count > 0) {
          count--;
          updateSetPinNumbersEntered(count);
      }
  } else if (inputLine.equals("wrong")) {
      if (triesLeft > 1) {
          count = 0;
          triesLeft--;
          wrongTries++;
          System.out.println("Je hebt nog " + triesLeft + " pogingen");
          scherm.attempsLeft(wrongTries);
          updateSetPinNumbersEntered(count);
      } else {
          SwingUtilities.invokeLater(() -> scherm.setHomeScreen());

      }   
  } else if (inputLine.equals("correct")) {
    //hier kan het volgende scherm
      SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
  } //else if (inputLine.equals("go")){
            //String codeLine = input.readLine();
            //System.out.println(codeLine);
            // codeLine is the iban of pass
            // To Do connect with database
  //}
}

//Options
public void currentScreenTwo(String inputLine) throws IOException, InterruptedException{
    if (inputLine.equals("a")){
        SwingUtilities.invokeLater(() -> scherm.setCheckSaldoScreen());
    }
    else if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
        sendText("done");
    }
    else if (inputLine.equals("c")){
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    }
    else if (inputLine.equals("d")){
        SwingUtilities.invokeLater(() -> scherm.setFastMoneyScreen());
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
public void currentScreenFour(String inputLine){
    if (inputLine != null){
        SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
    }
    else{}}

//Withdraw
public void currentScreenFive(String inputLine){
    if (inputLine.equals("d")){
        SwingUtilities.invokeLater(() -> scherm.setReceiptScreen());
    }
    else if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
    } else if (inputLine.matches("[0-9]")) {
        scherm.transactionAmount(inputLine);
        // System.out.println("i made it");
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    } else if (inputLine.equals("*")){
        scherm.transactionAmount(inputLine);
        SwingUtilities.invokeLater(() -> scherm.setWithdrawScreen());
    }
}

public void currentScreenSix(String inputLine) throws IOException, InterruptedException{
    if (inputLine.equals("b")){
        SwingUtilities.invokeLater(() -> scherm.setGoodbyeScreen());
        //sendText("done");

    }
    else if (inputLine.equals("d")){
        SwingUtilities.invokeLater(() -> scherm.setGoodbyeScreen());
        //sendText("receipt");
    } 
    
}

public void sendText(String text) throws IOException, InterruptedException{
            Scanner input = new Scanner(System.in);
            String str = input.nextLine();
            System.out.println(text);
            Thread.sleep(1500);
            serialPort.getOutputStream().write(str.getBytes());
            serialPort.getOutputStream().flush();
            input.close();
        }

public void currentScreenSeven(String inputLine){
    if (inputLine != null){
        SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
    }
    else{}
}

  public static void main(String[] args) {
    GUI scherm = new GUI();
    EventQueue.invokeLater(() -> {
        scherm.startup();
        scherm.updateTime();
    });
    
    inputHandler listener = new inputHandler(scherm);
    listener.initialize();
    Thread readDataThread = new Thread(() -> listener.readData());
    readDataThread.start();
  }
}
