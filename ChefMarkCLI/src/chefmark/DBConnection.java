package chefmark;

import java.sql.Connection;
<<<<<<< HEAD
=======
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
>>>>>>> e447491204d5e355c3096bd2e8d91169f3a03ec5

public class DBConnection {

    private Connection connection;
    private String jdbc_driver;
    private String db_URL;
    private String user;
    private String password;

    public Connection getConection() {
        return connection;
    }
    
public String getDriver() {
        return jdbc_driver;
    }

    public String getURL() {
        return db_URL;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setJdbc_driver(String jdbc_driver) {
        this.jdbc_driver = jdbc_driver;
    }

    public void setDb_URL(String db_URL) {
        this.db_URL = db_URL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
