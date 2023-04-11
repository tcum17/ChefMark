package chefmark;

public class WeeklyPlanScreen implements Screen{



public WeeklyPlanScreen(){
commands.add("1");



}
public void displayScreen(){
System.out.println("1. Create Weekly Plan ");
System.out.println("2. Delete Weekly Plan ");
System.out.println("3. Edit Weekly Plan ");
System.out.println("4. Share Weekly Plan ");

}

public void executeCommand(String command){
    switch(command){
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        case 4:
            break;
    }

}

}