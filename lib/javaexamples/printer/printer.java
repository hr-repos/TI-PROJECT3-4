package printer;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class printer {

    public static void test() throws IOException, InterruptedException{
        SerialPort sp = SerialPort.getCommPort("COM6");
        sp.setComPortParameters(19200, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
    
        if(!sp.openPort()) {
            System.out.println("\nCOM port NOT available\n"); return;
        }
    
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("\nEnter a value (0 to exit): ");
            String value = input.nextLine();
            if(value.equals("0")) break;
            System.out.println("value = " + value);
            Thread.sleep(1500);
            sp.getOutputStream().write(value.getBytes());
        }
        input.close();
    }
    
    
    public static void main(String[] args) throws IOException, InterruptedException{
        test();
    }
}
