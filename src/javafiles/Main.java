package javafiles;           


public class Main {


    public static void main(String[] args) {
        GUI scherm = new GUI();
        scherm.startup();
        scherm.updateTime();

        while (true){
            scherm.updateTime();
        }
    }
}







        //Border border = BorderFactory.createLineBorder(Color.green, 3);

        // creating a label
        // JLabel label = new JLabel();
        // label.setText("Zie hier een bank");
        // label.setIcon(bankImage);
        // label.setHorizontalAlignment(JLabel.CENTER);
        // label.setVerticalTextPosition(JLabel.NORTH);
        // label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        // label.setBounds(0, 0, 500, 500);