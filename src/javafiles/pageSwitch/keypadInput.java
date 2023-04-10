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

  public keypadInput(GUI scherm) {
    this.scherm = scherm;
  }

  public void initialize() {
    serialPort = SerialPort.getCommPort("COM6"); // Personal COM
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
                if (inputLine.equals("plus")) {
                    count++;
                    scherm.setPinNumbersEntered(count);
                    SwingUtilities.invokeLater(() -> scherm.setCardDetectedScreen());
                } else if (inputLine.equals("minus")) {
                    count--;
                    scherm.setPinNumbersEntered(count);
                    SwingUtilities.invokeLater(() -> scherm.setCardDetectedScreen());
                } else if (inputLine.equals("wrong")) {
                    SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
                } else if (inputLine.equals("correct")) {
                    SwingUtilities.invokeLater(() -> scherm.setPinNumbersEntered(5));
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}

  public static void main(String[] args) {
    GUI scherm = new GUI();
    EventQueue.invokeLater(() -> {
        scherm.startup();
        scherm.updateTime();
    });
    theSwitch listener = new theSwitch(scherm);
    listener.initialize();
    Thread readDataThread = new Thread(() -> listener.readData());
    readDataThread.start();
  }
}
