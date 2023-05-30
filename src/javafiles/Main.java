package javafiles;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        GUI scherm = new GUI();
        
        
        EventQueue.invokeLater(() -> {
            scherm.startup();
            scherm.updateTime();
        });
        communication listener = new communication(scherm);
        listener.initialize();
        Thread readDataThread = new Thread(() -> listener.readData());
        readDataThread.start();
      }
}