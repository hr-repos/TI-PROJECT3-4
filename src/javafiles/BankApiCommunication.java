
package javafiles;

// binnen deze class gebeurt de communicatie met de api


public class BankApiCommunication {
    private String bankLand;
    private String bankCode;


    public BankApiCommunication(String bankCode, String bankLand){
        this.bankCode = bankCode;
        this.bankLand = bankLand;
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