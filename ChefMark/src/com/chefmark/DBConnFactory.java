package chefmark;

public class DBConnFactory {

    private static DBConnection conn = null;

    
    public enum DBConnType{
        MYSQL("MySQL");
        public String DBName;
        DBConnType(String str){
            this.DBName = str;
        }
    }

    /**
     * Private constructor
     */
    private DBConnFactory(){
        
    }

    /**
     * Returns a connection
     * @param DBType database type (MySQL, Postgre, etc)
     * @return returns a connection
     */
    public static DBConnection getDBConnection(DBConnType DBType){
        if(DBType.equals(DBConnType.MYSQL)){
            if (conn == null) conn = new MySQLDB();
            return conn;
        }   
        else{
            if (conn == null) conn = new MySQLDB();
            return conn;
        }
    }
    
}
