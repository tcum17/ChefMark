package chefmark;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.Statement;
=======
import java.sql.*;
>>>>>>> e447491204d5e355c3096bd2e8d91169f3a03ec5

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

<<<<<<< HEAD
    // public setStatement(Statement statement)
    // {
    //     this.statement = statement;
    // }
    // public ResultSet dbQuery(String query)
    // {
        
    // }
=======
    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }
    
    public ResultSet read(User user) {
        

    }
>>>>>>> e447491204d5e355c3096bd2e8d91169f3a03ec5


}
