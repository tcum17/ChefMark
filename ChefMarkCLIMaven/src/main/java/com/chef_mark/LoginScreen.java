package chefmark;

import java.util.Scanner;

public class LoginScreen implements Screen {

    private String username;
    private String password;
    //private String email;

    public LoginScreen() {
        commands.add(username);
        commands.add(password);
        //commands.add(email);
    }

    public void displayScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        username = sc.nextLine();
        System.out.println("Enter your password: ");
        password = sc.nextLine();
    }

    public void executeCommand(String string) {
        
    }
}