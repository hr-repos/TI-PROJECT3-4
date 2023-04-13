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

  public keypadInput(GUI scherm) {
    this.scherm = scherm;
  }

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
                if (scherm.isCurrentScreen(0)) {
                    currentScreenZero(inputLine);
                    continue;
                } else if (scherm.isCurrentScreen(1)) {
                    currentScreenOne(inputLine);
                    continue;
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

public void currentScreenZero(String inputLine) {
  if (inputLine.equals("pass found")) {
      updateSetPinNumbersEntered(0);
      scherm.switchToScreen(1);   
  }
  else{}
}

public void currentScreenOne(String inputLine) {
  
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
          System.out.println("Je hebt nog " + triesLeft + " pogingen");
          updateSetPinNumbersEntered(count);
      } else {
          SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
      }   
  } else if (inputLine.equals("correct")) {
      SwingUtilities.invokeLater(() -> scherm.setCardDetectedScreen());
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
