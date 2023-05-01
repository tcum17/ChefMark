

import java.sql.*;

public class MySQLDB extends DBConnection{
    
    @Override
    public String getURL() {
        return "jdbc:mysql://localhost:3306/chefmark";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "ISURedbirds!";
    }
}
