
package javafiles;

import java.awt.geom.RoundRectangle2D.Float;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// binnen deze class gebeurt de communicatie met de api


public class BankApiCommunication {
    private String fromCtry;
    private String fromBank;

    private Gson gsonPretty = new GsonBuilder()
    .setPrettyPrinting()
    .create();

    private Gson gson = new Gson();
    
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
    public Double getBalance(String toCtry, String toBank, String acctNo, String pin){
        try {
            String apiResponse = postApiRequest(jsonBalancePacket(toCtry, toBank, acctNo, pin));
            JsonObject jsonObject = gson.fromJson(apiResponse, JsonObject.class);
            // System.out.println(jsonObject);

            return jsonObject.getAsJsonObject("body").get("balance").getAsDouble();
        } catch (Exception e) {
            System.out.println(e);
            return -1.0;
        }
    }

    private String jsonBalancePacket(String toCtry, String toBank, String acctNo, String pin){
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

        return gsonPretty.toJson(payload);
    }

    private String jsonWithdrawPacket(String toCtry, String toBank, String acctNo, String pin, int amount){
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

    private String postApiRequest(String payload){
        try {

            URL url = new URL("http://localhost:9999/balance");
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            // send json payload
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(payload);
            }
            
            // Read Response from API
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    System.out.println(line);
                    return line;
                }
                return "";
                
            }
        } catch (Exception e) {
            System.out.println("error in function postApiRequest");
            System.out.println(e);
            return "";
        }
    }






}