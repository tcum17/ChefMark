package chefmark;

import java.util.*;

public interface Screen {
    Set<String> commands = new HashSet<>();

    void displayScreen();

    void executeCommand(String command);

    
}
