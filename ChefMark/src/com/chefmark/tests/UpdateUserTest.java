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

public class UpdateUserTest{

    @Test
    public void updateUserTestNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP@ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0rd\nyes\nMyP@s$w0rd\nyes\nnewuser12@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) || newEmail.equals(inputEmail))
                result = false;
            else 
                result = true;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void updateUserTestInvalidCreds() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP@ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0d\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) || newEmail.equals(inputEmail))
                result = false;
            else 
                result = true;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==false);
    }

    @Test
    public void updateUserTestOnlyEmail() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP#ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP#ssw0rd\nno\nyes\nnewuser12@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) && (!(newEmail.equals(inputEmail))))
                result = true;
            else 
                result = false;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void updateUserTestOnlyPassword() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP#ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP#ssw0rd\nyes\nMyP@ssw0rd\nno";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if ((!(newPassword.equals(inputPassword))) && newEmail.equals(inputEmail))
                result = true;
            else 
                result = false;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void updateUserTestNone() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP#ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP#ssw0rd\nno\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) && newEmail.equals(inputEmail))
                result = true;
            else 
                result = false;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void updateUserTestInvalidPasswordFirstTime() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP@ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0rd\nyes\nasdf\nMyP#ssw0rd\nyes\nnewuser12@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) || newEmail.equals(inputEmail))
                result = false;
            else 
                result = true;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void updateUserTestEmail() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String username = "newusername";
        String inputPassword = "MyP@ssw0rd";
        String inputEmail = "newuser@gmail.com";

        UserController uc = new UserController();
        uc.createUser(username, inputPassword, inputEmail);
        dbq.create(uc.getUser());
        // Create a fake input stream with username and password
        String input = "newusername\nMyP@ssw0rd\nyes\nMyP@s$w0rd\nyes\nabcd\nnewuser12@gmail.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        boolean result = false;
        uc.updateUser(dbq, scanner);
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next()) {
            String newPassword = rs.getString(2);
            String newEmail = rs.getString(3);

            if (newPassword.equals(inputPassword) || newEmail.equals(inputEmail))
                result = false;
            else 
                result = true;
        }
        else {
            result=false;
        }
        dbq.delete(uc.getUser()); // remove from database immediately as this was a test--bad idea to populate junk values
        
        dbq.disconnect();
        assert(result==true);
    }

    

}