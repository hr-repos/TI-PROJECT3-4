package javafiles.pageSwitch;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;

import com.fazecast.jSerialComm.SerialPort;
import javafiles.GUI;

public class theSwitch {
  private SerialPort serialPort;
  private BufferedReader input;

  private final GUI scherm;

  public theSwitch(GUI scherm) {
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
                if (inputLine.equals("a")) {
                    SwingUtilities.invokeLater(() -> scherm.setHomeScreen());
                } else if (inputLine.equals("b")) {
                  scherm.setPinNumbersEntered(3);
                    SwingUtilities.invokeLater(() -> scherm.setCardDetectedScreen());
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
