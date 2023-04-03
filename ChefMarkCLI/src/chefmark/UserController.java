package chefmark;
public class UserController {
    private User user;

    public boolean createUser(String username, String password, String email) {
        //if (check if username and email already exist) -- return true if successfully created, false if otherwise
        this.user = new User(username, password, email);
        return true;
    }

    public changePassword(String password) {
        this.user.setPassword(password);
    }
}
