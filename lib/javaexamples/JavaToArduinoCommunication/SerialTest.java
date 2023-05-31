package JavaToArduinoCommunication;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class SerialTest {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        SerialPort sp = SerialPort.getCommPort("COM6"); // personal Computer
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        //-------------------------------------------------------------
        if(!sp.openPort()) {
            System.out.println("\nCOM port NOT available\n"); return;
        }
        //-------------------------------------------------------------
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("\nEnter string to print (or 'exit' to quit): ");
            String str = input.nextLine();
            if(str.equals("exit")) break;
            Thread.sleep(1500);
            sp.getOutputStream().write(str.getBytes());
        }
        input.close();
    }
}
