package JavaToArduinoCommunication;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class TestArduinoCommunication {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        SerialPort sp = SerialPort.getCommPort("COM5");
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        //-------------------------------------------------------------
        if(!sp.openPort()) {
            System.out.println("\nCOM port NOT available\n"); return;
        }
        //-------------------------------------------------------------
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("\nEnter number of LED blinks (0 to exit): ");
            Integer blinks = input.nextInt();
            if(blinks == 0) break;
            Thread.sleep(1500);
            sp.getOutputStream().write(blinks.byteValue());
        }
        input.close();
    }
}
