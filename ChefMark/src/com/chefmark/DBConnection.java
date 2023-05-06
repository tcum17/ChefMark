package chefmark;

public abstract class DBConnection {

    public DBConnection(){}

    public abstract String getURL();
    public abstract String getUsername();
    public abstract String getPassword();

}
