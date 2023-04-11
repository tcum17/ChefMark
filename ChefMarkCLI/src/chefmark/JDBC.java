package chefmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

  public static void main(String[] args) {
    // Set the JDBC URL to connect to your instance using the Cloud SQL JDBC Socket Factory.
    String jdbcUrl = "jdbc:mysql:///test?cloudSqlInstance=chefmark-382801:us-central1:chefmark&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=test&password=password";
    Connection connection = null;

    
    // Set the name of the user and password for your MySQL instance.
    String user = "admin";
    String password = "password";

   // Connection connection = null;

    try {
      // Load the JDBC driver for MySQL.
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Connect to the MySQL instance using the JDBC URL, user name, and password.
      connection = DriverManager.getConnection(jdbcUrl);

      // Use the connection to execute SQL statements, for example:
      // Statement statement = connection.createStatement();
      // ResultSet resultSet = statement.executeQuery("SELECT * FROM my_table");
      // ...
    } catch (SQLException e) {
      System.err.println("Unable to connect to the MySQL instance: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.println("Unable to load the JDBC driver for MySQL: " + e.getMessage());
    } finally {
      // Close the connection when you're finished with it.
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          // Ignore any errors that occur while closing the connection.
        }
      }
    }
  }
}
