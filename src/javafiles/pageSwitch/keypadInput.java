package javafiles.pageSwitch;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;

import com.fazecast.jSerialComm.SerialPort;
import javafiles.GUI;

public class keypadInput {
  private SerialPort serialPort;
  private BufferedReader input;

  private final GUI scherm;
  private int count;
  private int triesLeft = 3;
  private int wrongTries;

  public keypadInput(GUI scherm) {
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
                    
                }
                else {}
            }
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
      scherm.switchToScreen(1);   
  }
  else{}
}

//code for pinScreen
public void currentScreenOne(String inputLine) {
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
          scherm.switchToScreen(0);
      }   
  } else if (inputLine.equals("correct")) {
    //hier kan het volgende scherm
      SwingUtilities.invokeLater(() -> scherm.setLoggedInScreen());
  }
}

  public static void main(String[] args) {
    GUI scherm = new GUI();
    EventQueue.invokeLater(() -> {
        scherm.startup();
        scherm.updateTime();
    });
    
    keypadInput listener = new keypadInput(scherm);
    listener.initialize();
    Thread readDataThread = new Thread(() -> listener.readData());
    readDataThread.start();
  }
}
