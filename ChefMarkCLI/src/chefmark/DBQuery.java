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

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

}
