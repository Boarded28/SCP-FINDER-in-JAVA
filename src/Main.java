import program.ConsoleUtils;
import program.MainMenu;
import program.ScpFinder;

public class Main {

    public static void main(String[] args) {
        
        ConsoleUtils.clearScreen();        
        ScpFinder.load();
        
        while (true) {
            new MainMenu().start();
        }
    }
}