package javafiles;           
public class Main {

    public static void main(String[] args) {
        GUI scherm = new GUI();
        scherm.startup();
        scherm.updateTime();
        scherm.setCardDetectedScreen();      // tijdelijk om dit scherm te programmeren
        while (true){   // main loop
            scherm.updateTime();
            
        }
    }
}