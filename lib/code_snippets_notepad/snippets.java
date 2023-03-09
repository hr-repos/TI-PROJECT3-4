package lib.code_snippets_notepad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color; // for using colors
import java.awt.Font; // for using fonts
import javax.swing.*;

public class snippets {
    static String dateTime;
    static Date date;

    static void getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        date = new Date(System.currentTimeMillis());
        dateTime = (formatter.format(date));
    }

    public static void main(String[] args) {

        JLabel homescreenlabel2 = new JLabel();
        // code to be added for the date and time
        homescreenlabel2.setBackground(Color.blue);
        getCurrentDateTime();
        homescreenlabel2.setText(dateTime);
    }
}
