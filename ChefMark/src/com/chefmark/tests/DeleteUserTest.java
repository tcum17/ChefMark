package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.*;

import chefmark.*;

public class DeleteUserTest{

    // @Test
    // public void testUserDeletion() throws SQLException{
        
    //     DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
    //     DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
    //     dbq.connect();

    //     Scanner scanner = new Scanner(in);
    //     String userName = "testAccount";
    //     String password = "Create1@1";
    //     String email = "myemail@test.com";
        
    //     UserController uc = new UserController();

    //     uc.createUser(userName, password, email);

    //     Boolean result = uc.deleteUser(dbq, scanner);

    //     String input = "testAccount\nCreate1@1\ny";
    //     InputStream in = new ByteArrayInputStream(input.getBytes());
        
    //     assert(result == true);
    // }

    // public void testUserDeletionNoUser() throws SQLException{
        
    //     DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
    //     DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
    //     dbq.connect();

    //     Scanner scanner = new Scanner(in);
    //     String userName = "testAccount";
    //     String password = "Create1@1";
    //     String email = "myemail@test.com";
        
    //     UserController uc = new UserController();

    //     //uc.createUser(userName, password, email);

    //     Boolean result = uc.deleteUser(dbq, scanner);

    //     String input = "testAccount\nCreate1@1\n";
    //     InputStream in = new ByteArrayInputStream(input.getBytes());
        
    //     assert(result == false);
    // }


}