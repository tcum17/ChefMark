package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


import chefmark.*;

public class CreateUserTest {

    @Test
    public void testCreateAccount() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0rd\nnewusername@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testCreateAccountInvalidUsernameProvidedValidAfter() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "abc\nnewusername\nMyP@ssw0rd\nnewusername@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testCreateAccountInvalidPasswordProvidedValidAfter() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newusername\nabc\nMyP@ssw0rd\nnewusername@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testCreateAccountNoUsernameProvideAfter() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "\nnewusername\nMyP@ssw0rd\nnewusername@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }
    

    @Test
    public void testCreateAccountNoPasswordProvideAfter() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newusername\n\nMyP@ssw0rd\nnewusername@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testCreateAccountUsernameExistsProvidesUnclaimed() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "abc\newuser\nMyP@ssw0rd\nnewusername@gmail.com\nnewusername";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testCreateAccountUsernameExistsProvidesClaimedThenUnclaimed() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "abc\newuser\nMyP@ssw0rd\nnewusername@gmail.com\nnewuser1\nnewusername";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void testBadEmailProvidedThenValid() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0rd\nnewusernamegmail.com\nnewuser@gmail.com";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the sign up method and verify that it returns true
        UserController uc = new UserController();
        boolean result = uc.signUp(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (!rs.next()) 
            result=false;
        else
            dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        dbq.disconnect();
        assert(result==true);
    }
    
}
