package javafiles;

import java.sql.*;

public class database {

   static String url = "jdbc:mysql://145.24.222.188:8649/bank";
   static String username = "sqluser";
   static String password = "q76^UQT7!BcR";

  public static void main(String[] args) {
    checkConnection();
    retrieveData();
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

  public static void retrieveData() {
    
    try {
      Connection conn = DriverManager.getConnection(url, username, password);
      System.out.println("Connected to the database.");

      // create a statement object
      Statement stmt = conn.createStatement();

      // execute a query and get the result set
      ResultSet rs = stmt.executeQuery("SELECT * FROM UserData");

      // iterate through the result set and print the data
      while (rs.next()) {
        int id = rs.getInt("id");
        String Name = rs.getString("name");
        String Country = rs.getString("country");
        int Birthyear = rs.getInt("Birthyear");
        System.out.println("ID: " + id + ", Name: " + Name + ", Email: " + Country + ", Birthyear: " + Birthyear);
      }

      // close the result set, statement, and connection
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("Failed to retrieve data from the database.");
      e.printStackTrace();
    }
  }
}




