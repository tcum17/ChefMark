package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static org.junit.Assert.*;

import chefmark.*;

public class LoginTest {
    
    @Test
    public void testLoginAccountDoesNotExist() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.login(dbq, scanner);
        dbq.disconnect();
        assert(result==false);
    }

    @Test
    public void testLoginAccountExists() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.login(dbq, scanner);
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testLoginInvalidUsernameFirstTime() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "abc\nnewuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.login(dbq, scanner);
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testLoginInvalidPassword() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newuser1\nCreate1@2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.login(dbq, scanner);
        dbq.disconnect();
        assert(result==false);
    }

    @Test
    public void testLoginInvalidPasswordFirstTime() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newuser1\nabc\nCreate1@2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.login(dbq, scanner);
        dbq.disconnect();
        assert(result==false);
    }
}
