package chefmark;
public class UserController {
    private User user;

    public boolean createUser(String username, String password, String email) {
        //if (check if username and email already exist) -- return true if successfully created, false if otherwise
        this.user = new User(username, password, email);
        return true;
    }

    public boolean changePassword(String password) {
        //check if password is valid, false if it can't be changed
        this.user.setPassword(password);
        return true;
    }

    public boolean changeEmail(String email) {
        //check if email is valid, false if it can't be changed
        this.user.setEmail(email);
        return true;
    }

    public boolean deleteUser(String username) {
        //find username from ArrayList of users, remove them
        //return false if it can't be found/deleted, true otherwise
        return true;
    }

    public boolean login(String username, String password, DBConnection db) {
        DBQuery dbq = new DBQuery();
        dbq.setConnection(db.getConection());
        dbq.setDBConnection(db);
        ResultSet rs = dbq.read(user);
        return false;
    }
}
