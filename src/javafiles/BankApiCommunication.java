
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
    Double withdraw(String toCtry, String toBank, String acctNo, String pin, int amount) {
        try {
            String apiResponse = postApiRequest(toCtry, toBank, acctNo, pin, amount);
            JsonObject jsonObject = gson.fromJson(apiResponse, JsonObject.class);
            // System.out.println(jsonObject);
            if (jsonObject.getAsJsonObject("body").get("succes").getAsBoolean() != true){
                return null;
            } else {
                return jsonObject.getAsJsonObject("body").get("balance").getAsDouble();
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    // return het bedrag dat op de rekening staat
    public Double getBalance(String toCtry, String toBank, String acctNo, String pin){
        try {
            String apiResponse = postApiRequest(toCtry, toBank, acctNo, pin, 0);
            JsonObject jsonObject = gson.fromJson(apiResponse, JsonObject.class);
            // System.out.println(jsonObject);

            return jsonObject.getAsJsonObject("body").get("balance").getAsDouble();
        } catch (Exception e) {
            System.out.println(e);
            return -1.0;
        }
    }

    private String createJsonPacket(String toCtry, String toBank, String acctNo, String pin, int amount){
        JsonObject payload = new JsonObject();
        JsonObject head = new JsonObject();
        JsonObject body = new JsonObject();

        head.addProperty("fromCtry", fromCtry);
        head.addProperty("fromBank", fromBank);
        head.addProperty("toCtry", toCtry);
        head.addProperty("toBank", toBank);

        body.addProperty("acctNo", acctNo);
        body.addProperty("pin", pin);
        if (amount != 0){
            body.addProperty("amount", amount);

        }
        payload.add("head", head);
        payload.add("body", body);

        return gsonPretty.toJson(payload);
    }

    private String postApiRequest(String toCtry, String toBank, String acctNo, String pin, int amount){
        String jsonRequestString = createJsonPacket(toCtry, toBank, acctNo, pin, amount);
        URL url;
        try {
            if (checkIfLocalAccount(acctNo) && amount == 0){
                url = new URL("http://localhost:8443/balance");
            } else if (checkIfLocalAccount(acctNo) && amount != 0){
                url = new URL("http://localhost:8443/withdraw");
            } else if (!checkIfLocalAccount(acctNo) && amount == 0){
                url = new URL("http://145.24.222.241:8443/balance");
            } else if (!checkIfLocalAccount(acctNo) && amount != 0) {
                url = new URL("http://145.24.222.241:8443/withdraw");
            } else {
                System.out.println("Could not get the right url");
                return "";
            }
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            // send json payload
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(jsonRequestString);
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