package lib.javaexamples.BankApi;


/**
 * test
 */
public class Example {
    BankApiCommunication comm = new BankApiCommunication("LU", "BK");
    
    public static void main(String[] args) {
        Example test = new Example();
        test.run();
    }

    public void run(){
        Double balance = handleBalanceRequest("LU", "BK", "LUX01BANK000005", "7869");
        Double balanceAfterWithdraw = handleWithdrawRequest("LU", "BK", "LUX01BANK000005", "7869", 20);
        System.out.println(balance);
        System.out.println(balanceAfterWithdraw);
    }
    
    Double handleBalanceRequest(String toCtry, String toBank, String acctNo, String pin){
        String apiResponse = comm.postApiRequest(toCtry, toBank, acctNo, pin, 0);
        if (apiResponse.equals("")){
            handleApiError("");
            return -1.0;
        }
        else if (apiResponse.equals(null)){
            handleApiError(apiResponse);
            return -1.0;
        } 
        else if (comm.checkIfError(apiResponse)){
            handleApiError(apiResponse);
            return -1.0;
        } 
        else {
            return comm.getBalanceFromJson(apiResponse);
        }
    }
    
    Double handleWithdrawRequest(String toCtry, String toBank, String acctNo, String pin, int amount){
        String apiResponse = comm.postApiRequest(toCtry, toBank, acctNo, pin, amount);
        if (apiResponse.equals("")){
            System.out.println("handleWithdrawRequest: Empty api response");
            handleApiError("");
            return -1.0;
        }
        else if (apiResponse.equals(null)){
            System.out.println("handleWithdrawRequest: received null (api offline?)");
            handleApiError("");
            return -1.0;
        }
        else if (comm.checkIfError(apiResponse)){
            System.out.println("handleWithdrawRequest: predefined error");
            handleApiError(apiResponse);
            return -1.0;
        } 
        else {
            return comm.getBalanceAfterWithdrawFromJson(apiResponse);
        }
    }

    void handleApiError(String apiResponse){
        if (apiResponse.equals("LU: Account number not recognized")){
            // handle 
        } 
        else if (apiResponse.equals("LU: Account is blocked, contact BANK")){
            // handle 

        }
        else if (apiResponse.equals("LU: To many attempts, account is now blocked, contact BANK")){
            // handle 
        }
        else if (apiResponse.equals("LU: Not enough credit")){
            // handle 
        }
        else {
            System.out.println("F handleApiError: unexpected error: " + apiResponse);
        }
    }
}