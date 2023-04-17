package chefmark;

import java.util.Scanner;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Scanner sc = new Scanner(System.in);
        String currentUser;
        String password;
        DBQuery dbq = new DBQuery(new MySQLDB());
        //DBConnection db = new DBConnection(new MySQLDB()); // mySQL
        UserController uc = new UserController();

        System.out.println("Type \"Exit\" to quit the program");
        System.out.println("Enter your username: ");
        currentUser = sc.nextLine();
        System.out.println("Enter your password: ");
        password = sc.nextLine();

        dbq.connect();
        ResultSet rs = dbq.read(new User(currentUser, password, ""));
        //uc.login(currentUser, password, dbq);

        String exit = sc.nextLine();

        if (exit.equals("Exit")) {
            System.exit(0);
        }
        boolean signedIn = false;
        String choice = "";
        while (!choice.equals("quit")) {
            while (!signedIn) {
                System.out.println("==== Welcome to ChefMark! ====");
                System.out.println("login");
                System.out.println("signup");
                System.out.println("quit");
                String startScreenInput = sc.nextLine();

                if (startScreenInput.equals("login")) {
                    /**
                     * LOGIN
                     * 
                     * Query database passing in email and password
                     * If doesn't exist, tell user, show original login/signup prompt
                     * If password incorrect, tell user, reprompt password field
                     * Otherwise, enter into main application
                     */
                    signedIn = true;
                } else if (startScreenInput.equals("signup")) {
                    /**
                     * SIGNUP
                     * 
                     * Query database passing in email first
                     * If email already found in database, tell user, send to LOGIN
                     * Else display password input field
                     */
                    signedIn = true;
                } else if (startScreenInput.equals("quit")) {
                    System.out.println("GoodBye");
                    System.exit(0);
                } else {
                    System.out.println("Your answer did not match any of our options. Please reenter");
                }
            }
        }

        /**
         * Home
         */
    }
}
