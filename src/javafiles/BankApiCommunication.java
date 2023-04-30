
package javafiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

// binnen deze class gebeurt de communicatie met de api


public class BankApiCommunication {
    private String fromCtry;
    private String fromBank;

    private Gson gson = new GsonBuilder()
    .setPrettyPrinting()
    .create();
    
    public BankApiCommunication(String fromCtry, String fromBank){
        this.fromBank = fromBank;
        this.fromCtry = fromCtry;
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

    public String jsonBalancePacket(String toCtry, String toBank, String acctNo, String pin){
        JsonObject payload = new JsonObject();
        JsonObject head = new JsonObject();
        JsonObject body = new JsonObject();

        head.addProperty("fromCtry", fromCtry);
        head.addProperty("fromBank", fromBank);
        head.addProperty("toCtry", toCtry);
        head.addProperty("toBank", toBank);

        body.addProperty("acctNo", acctNo);
        body.addProperty("pin", pin);
        
        payload.add("head", head);
        payload.add("body", body);

        return gson.toJson(payload);
    }

    public String jsonWithdrawPacket(String toCtry, String toBank, String acctNo, String pin, int amount){
        String fromCtry = "LU";
        String fromBank = "BK";

        JsonObject payload = new JsonObject();
        JsonObject head = new JsonObject();
        JsonObject body = new JsonObject();

        head.addProperty("fromCtry", fromCtry);
        head.addProperty("fromBank", fromBank);
        head.addProperty("toCtry", toCtry);
        head.addProperty("toBank", toBank);

        body.addProperty("acctNo", acctNo);
        body.addProperty("pin", pin);
        body.addProperty("amount", amount);
        
        payload.add("head", head);
        payload.add("body", body);

        return gson.toJson(payload);
    }






}