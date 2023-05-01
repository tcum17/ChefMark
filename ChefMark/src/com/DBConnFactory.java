

public class DBConnFactory {

    private static DBConnection conn = null;

    public enum DBConnType{
        MYSQL("MySQL");
        public String DBName;
        DBConnType(String str){
            this.DBName = str;
        }
    }

    private DBConnFactory(){
        
    }

    public static DBConnection getDBConnection(DBConnType DBType){
        if(DBType.equals(DBConnType.MYSQL)){
            return new MySQLDB();
        }
        else{
            return new MySQLDB();
        }
    }
    
}
