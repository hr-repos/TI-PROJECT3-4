package lib.examples;

import java.sql.*;

public class database {
   static String url = "jdbc:mysql://145.24.222.188:8649/bank";
   static String username = "sqluser";
   static String password = "q76^UQT7!BcR";

  public static void main(String[] args) {
    //checkConnection();
    String test = retrieveData("LU01BK000000001");
    System.out.println(test + " is the code for LU01BK000000001");
  }

  public static void checkConnection() {
    try {
      Connection conn = DriverManager.getConnection(url, username, password);
      System.out.println("Connected to the database.");
      conn.close();
    } catch (SQLException e) {
      System.out.println("Failed to connect to the database.");
      e.printStackTrace();
    }
  }

  public static String retrieveData(String iban) {
    String code = null;
  
    try {
      Connection conn = DriverManager.getConnection(url, username, password);
      System.out.println("Connected to the database.");
  
      // create a statement object
      Statement stmt = conn.createStatement();
  
      // execute a query and get the result set
      ResultSet rs = stmt.executeQuery("SELECT * FROM test WHERE IBAN ='" + iban + "';");
  
      // iterate through the result set and retrieve the code
      if (rs.next()) {
        code = rs.getString("PINCODE");
      }
  
      // close the result set, statement, and connection
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("Failed to retrieve data from the database.");
      e.printStackTrace();
    }
  
    return code;
  }
  
}




