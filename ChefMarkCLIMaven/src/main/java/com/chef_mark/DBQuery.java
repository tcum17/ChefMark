package com.chef_mark;

import java.sql.*;

public class DBQuery {
    private DBConnection dbconnection;
    private Connection connection;
    private Statement statement;


    public void setDBConnection(DBConnection dbconnection)
    {
        this.dbconnection = dbconnection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    } 

    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }
    public ResultSet dbQuery(String query)
    {
        return null;
    }


}
