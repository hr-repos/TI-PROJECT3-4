
package javafiles;

// binnen deze class gebeurt de communicatie met de api


public class BankApiCommunication {
    private String bankLand;
    private String bankCode;


    public BankApiCommunication(String bankCode, String bankLand){
        this.bankCode = bankCode;
        this.bankLand = bankLand;
    }

    // controleerd of het rekeningnummer van onze bank is en returnt true of false
    private boolean checkIfLocalAccount(String acctNo){
        int offset = Math.min(9, acctNo.length());
        String text = acctNo.substring(0, offset);
        return text.equals("LUX01BANK");
    }

    // true goedgekeurd, // false niet goedgekeurd
    boolean withdraw(String acctNo, String pin, int bedrag ) {
        return true;

    }

    


    // return het bedrag dat op de rekening staat
    public int getBalance(String acctNo, String pin){
        
        return 100;

    }




}