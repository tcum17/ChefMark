import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

abstract class DBQuery {
    private DBConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public DBQuery(){}

    public void connect() throws SQLException{}
    public void disconnect() throws SQLException{}

    public void create(User user) throws SQLException{}
    public ResultSet read(User user) throws SQLException{
        ResultSet rs = null;
        return rs;
    }
    public void update(User user) throws SQLException{}
    public void delete(User user) throws SQLException{}
    public void create(Pantry pantry, User user) throws SQLException{}
    public void create(Pantry pantry, Ingredient ingredient, User user) throws SQLException{}
    public void delete(Pantry pantry, User user) throws SQLException {}
    public void update(Ingredient ingredient, User user) throws SQLException{}
    public void delete(Ingredient ingredient, User user) throws SQLException {}
    public void create(WeeklyPlan weeklyPlan, User user) throws SQLException {}
    public ResultSet read(Pantry pantry, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(Ingredient ingredient, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }


}