package javafiles.pageSwitch;


import java.io.IOException;

import ArduinoToJavaCommuncation.ArduinoToJavaCommuncation;
import javafiles.GUI;
import javafiles.test;

public class pageSwitch {

    public static void main(String[] args) throws IOException, InterruptedException {
        GUI scherm = new GUI();
        switching change = new switching();
        ArduinoToJavaCommuncation aToJcomm = new ArduinoToJavaCommuncation();
        aToJcomm.start();
        scherm.startup();
        scherm.updateTime();
        // scherm.setCardDetectedScreen();      // tijdelijk om dit scherm te programmeren
        while (true){   // main loop
            scherm.updateTime();
            change.switchs();
        }
    }
}