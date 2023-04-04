package javafiles.pageSwitch;

import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;

import javafiles.GUI;

import java.util.Scanner;
import java.io.PrintWriter;

public class switching {
    static SerialPort chosenPort;
    
    public void switchs() throws IOException, InterruptedException{
        GUI scherm = new GUI();
        SerialPort sp = SerialPort.getCommPort("COM6");
    sp.setComPortParameters(9600, 8, 1, 0);
    sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
    
    String input = "";
    try (Scanner scan = new Scanner(System.in)) {
        PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
        		
                output.flush();
        		input = scan.nextLine();
                if (input == "a"){
                    scherm.setHomeScreen();
                }
                else if (input == "b"){
                    scherm.setCardDetectedScreen();
                }
                else {
                    System.out.println(input);
                }
        		output.print(input);
    }
			
            
    }
    }

