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

    @Test
    public void testUserDeletion() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "y\nnewusername\nP@ssw0rd\ny\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // user is deleted from database

        if (username.equals(inputUsername) && !rs2.next())
            result =true;
        assert(result == true);
    }

    @Test
    public void testUserDeletionChangedMind() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        //dbq.create(uc.getUser());

        String input = "n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // user is deleted from database

        if (username.equals(inputUsername) && !rs2.next())
            result =true;
        assert(result == false);
    }

    @Test
    public void testUserDeletionChangedMindLater() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "y\nnewusername\nP@ssw0rd\nn\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // user is deleted from database

        if (username.equals(inputUsername) && !rs2.next()) // checks if user deleted, rs2 is empty if deleted
            result =true;
        dbq.delete(uc.getUser()); // actually delete, we have all boolean values stored
        assert(result == false);
    }

    @Test
    public void testUserDeletionInvalidUsername() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "y\nnewusernam\nP@ssw0rd\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // if user is deleted from database

        if (username.equals(inputUsername) && !rs2.next()) // checks if user deleted, rs2 is empty if deleted
            result =true;
        dbq.delete(uc.getUser()); // actually delete, we have all boolean values stored
        assert(result == false);
    }

    @Test
    public void testUserDeletionFirstTimeNoneThenUsername() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "y\n\nnewusername\nP@ssw0rd\ny\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // if user is deleted from database

        if (username.equals(inputUsername) && !rs2.next()) // checks if user deleted, rs2 is empty if deleted
            result =true;
        dbq.delete(uc.getUser()); // actually delete, we have all boolean values stored
        assert(result == true);
    }

    @Test
    public void testUserDeletionFirstTimeNoneThenPassword() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "y\nnewusername\n\nP@ssw0rd\ny\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // if user is deleted from database

        if (username.equals(inputUsername) && !rs2.next()) // checks if user deleted, rs2 is empty if deleted
            result =true;
        dbq.delete(uc.getUser()); // actually delete, we have all boolean values stored
        assert(result == true);
    }

    @Test
    public void testUserDeletionInvalidEnter() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String inputUsername = "newusername";

        UserController uc = new UserController();
        uc.createUser(inputUsername, "P@ssw0rd", "");

        dbq.create(uc.getUser());

        String input = "q\nnewusername\nP@ssw0rd\ny\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);
        boolean result =false;
        String username = "";
        ResultSet rs = dbq.read(uc.getUser());
        if (rs.next())
            username = rs.getString(1);

        uc.deleteUser(dbq, scanner);
        ResultSet rs2 = dbq.read(uc.getUser()); // if user is deleted from database

        if (username.equals(inputUsername) && !rs2.next()) // checks if user deleted, rs2 is empty if deleted
            result =true;
        dbq.delete(uc.getUser()); // actually delete, we have all boolean values stored
        assert(result == true);
    }


}