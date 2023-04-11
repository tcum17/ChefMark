package com.chef_mark;

import java.sql.*;

public class DBConnection {
    
    private Connection connection;
    private String jdbc_driver;
    private String db_URL;
    private String user;
    private String password;
    private String query;
    
    public DBConnection(Connection connection, String jdbc_driver, String db_URL, String user, String password, String query) {
        this.connection = connection;
        this.jdbc_driver = jdbc_driver;
        this.db_URL = db_URL;
        this.user = user;
        this.password = password;
        this.query = query;
    }

    public Connection getConnection() {
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public void setJdbc_driver(String jdbc_driver){
        this.jdbc_driver = jdbc_driver;
    }

    public void setDb_URL(String db_URL){
        this.db_URL = db_URL;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
