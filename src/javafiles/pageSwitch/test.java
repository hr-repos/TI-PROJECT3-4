package javafiles.pageSwitch;

import com.fazecast.jSerialComm.*;
import java.io.*;

import javafiles.*;
import ArduinoToJavaCommuncation.ArduinoToJavaCommuncation;

public class test {
    static GUI gui = new GUI();
  private SerialPort serialPort;
  private BufferedReader input;
  
  

  public void initialize() {
    SerialPort[] ports = SerialPort.getCommPorts();
    serialPort = ports[2]; // Select your port here
    serialPort.setBaudRate(9600);
    serialPort.openPort();
    input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
    serialPort.addDataListener(new SerialPortDataListener() {

      @Override
      public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

      @Override
      public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
          try {
            String inputLine = input.readLine();
            if (inputLine.equals("A")) {
              gui.setCardDetectedScreen();
              gui.updateTime();
            }
            else if (inputLine.equals("B")) {
                gui.setHomeScreen();
                gui.updateTime();
              }
          } catch (Exception e) {
            System.err.println(e.toString());
          }
        }
      }
    });
  }

  public static void main(String[] args) {
    
    ArduinoToJavaCommuncation aToJ = new ArduinoToJavaCommuncation();
    aToJ.start();
    gui.startup();
    gui.updateTime();
    test listener = new test();
    listener.initialize();
  }
}