package com.chef_mark;

import java.sql.*;

public class DBConnection {
    
    private Connection connection;
    private String jdbc_driver;
    private String db_URL;
    private String user;
    private String password;

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
