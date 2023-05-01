import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UserController {
    private static Pattern usernameRegex = Pattern
    .compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"); // username
private static Pattern passwordRegex = Pattern
    .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=[^\r\n\t\f ]+$).{8,}$"); // password

    private User user;

    public static Pattern getUsernameRegex() {
        return usernameRegex;
    }

    public static Pattern getPasswordRegex() {
        return passwordRegex;
    }

    public void initializePantry(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.read(user.getPantry(), this.user);
        while (rs.next()) {
            user.getPantry().addIngredient(new Ingredient(rs.getString(1), "", Float.parseFloat(rs.getString(2)), rs.getString(3)));
        }
    }

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

    public boolean deleteUser(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("\nThis action will delete your account. Are you sure you want to continue? y/n");
        String testInput = sc.nextLine();
        if (testInput.equals("y")) {
            System.out.println("Delete process continuing.");

            System.out.println("Enter your username: ");
            String username = validateUsername(sc);

            System.out.println("Enter your password: ");

            String password = validatePassword(sc);

            // Matcher passwordMatch = passwordRegex.matcher(testInput);
            // while (!(passwordMatch.matches())) {
            //     System.out.println("Password is in incorrect format. Passwords must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            //     System.out.println("\nEnter your password: ");
            //     testInput = sc.nextLine();
            //     passwordMatch = passwordRegex.matcher(testInput);
            // }
            // password = testInput;

            ResultSet rs = dbq.read(new User(username, password, ""));
            if (rs.next()) {
                if (username.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
                    System.out.println("Are you absolutely sure you want to delete your account? This action cannot be undone. y/n");
                    testInput = sc.nextLine();
                    if (testInput.equals("y")) {
                        System.out.println("Proceeding with user deletion.");
                        dbq.delete(new User(username, password, ""));
                        System.out.println("User " + username + " has been deleted. Sorry to see you go!");
                    } else {
                        System.out.println("User " + username + " will not be deleted.");
                    }
                } else {
                    System.out.println("User information does not match with database. Try again.");
                }
            } else {
                System.out.println("Database query unsuccessful. Try again later.");
            }
        } else {
            System.out.println("Deletion process halted.");
        }
        return false;
    }
    

    public User getUser() {
        return this.user;
    }

    public boolean login(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your username: ");

        String username = validateUsername(sc);

        System.out.println("Enter your password: ");

        String password = validatePassword(sc);
        ResultSet rs = dbq.read(new User(username, password, ""));
        if (rs.next()) {
            if (rs.getString(1).equals(username) && rs.getString(2).equals(password)) {
                System.out.println("\nLogin successful!\n\nEnter your selection: ");
                createUser(username, password, rs.getString(3));
                initializePantry(dbq);
                return true;
            }
            else {
                System.out.println("\nUsername or password is incorrect. Do you have an account?"); // either username or password doesn't match
            }
        } else {
            System.out.println("\nUsername or password is incorrect. Do you have an account?"); // username and password don't exist (don't tell user)
        }
        return false;
    }

    public boolean signUp(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your desired username: ");
        String username = validateUsername(sc);
        System.out.println("Enter your desired password: ");
        String password = validatePassword(sc);
        System.out.println("Enter your desired email: ");
        String email = sc.nextLine();
        ResultSet rs = dbq.read(new User(username, password, email));
        if (rs.next()) {
            while (username.equals(rs.getString(1))) {
                System.out.println("Username already taken. Please enter a new username: ");
                username = validateUsername(sc);
                //uc.getUser().setUser(username);
            }
            while (email.equals(rs.getString(3))) {
                System.out.println("Email already taken. Please enter a new email: ");
                email = sc.nextLine();
            }

        }
        createUser(username, password, email);
        dbq.create(this.user);
        System.out.println("You are now logged in. Please save your login info as this cannot be retrieved.");
        return true;
    }

    public String validateUsername(Scanner sc) {
        String username = sc.nextLine();
        Matcher usernameMatch = usernameRegex.matcher(username);
        while (!(usernameMatch.matches())) {
            System.out.println("Username is in incorrect form. Usernames must be at least 6 characters");
            System.out.println("\nEnter your username: ");
            username = sc.nextLine();
            usernameMatch = usernameRegex.matcher(username);
        }
        return username;
    }

    public String validatePassword(Scanner sc) {
        String password = sc.nextLine();
        Matcher passwordMatch = passwordRegex.matcher(password);
        while (!(passwordMatch.matches())) {
            System.out.println(
                    "Password is in incorrect format. Passwords must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            System.out.println("\nEnter your password: ");
            password = sc.nextLine();
            passwordMatch = passwordRegex.matcher(password);
        }
        return password;
    }
}
