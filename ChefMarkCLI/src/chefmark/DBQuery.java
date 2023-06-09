package chefmark;

import java.sql.*;

public class DBQuery {
    private DBConnection dbConnection;
    private Connection connection;
    private Statement statement;


    public DBQuery(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void connect() throws SQLException {
        String url = dbConnection.getURL();
        String user = dbConnection.getUsername();
        String password = dbConnection.getPassword();

        connection = DriverManager.getConnection(url, user, password);

        statement = connection.createStatement();
    }

    public ResultSet create(User user) {
        return executeQuery("INSERT INTO USER VALUES(" + user.getUsername() + ", " + user.getPassword() + ", " + user.getEmail());
    }

    public ResultSet read(User user) {
        return executeQuery("SELECT * FROM USER WHERE USERNAME = '" + user.getUsername() + "'");
    }

    public ResultSet update(User user) {
        return executeQuery("UPDATE USER SET userPassword = '" + user.getPassword() + "', email = '" + user.getEmail() + "' WHERE USERNAME = '" + user.getUsername() + "'");
    }

    public ResultSet delete(User user) {
        return executeQuery("DELETE FROM USER WHERE USERNAME = '" + user.getUsername() + "'"); // handles foreign keys
    }

    private ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

}
