package chefmark;

public class MySQLDB extends DBConnection{
    
    /**
     * returns the URL
     */
    @Override
    public String getURL() {
        return "jdbc:mysql://localhost:3306/chefmark";
    }

    /**
     * returns the username
     */
    @Override
    public String getUsername() {
        return "root";
    }

    /**
     * returns the password
     */
    @Override
    public String getPassword() {
        return "ISURedbirds!";
    }
}
