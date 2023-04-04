package chefmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "ISURedbirds!");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from persons");
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
            con.close();
        
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
