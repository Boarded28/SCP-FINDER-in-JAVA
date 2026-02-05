package program;

import java.util.Scanner;

public class MainMenu {

    public static final Scanner 
    in = new Scanner(System.in);

    public void start() {
        ConsoleUtils.clearScreen(); 
        try {
            System.out.printf("""                        
                             [SCP FINDER]                              
                        ======================                  
                            [1] search
                            [2] exit
                        ======================
                        ->%s"""," ");
            
            switch (Integer.parseInt(in.nextLine())) {
                case 1  -> {ScpFinder.search();}
                case 2  -> {System.out.println("\nProgram Terminated.\n");System.exit(0);}
                default -> {System.out.println("\nType invalid.\n\n--- Press Enter to return ---\n");in.nextLine();}
            } 
        } catch (Exception e) {
            System.out.println("\nType invalid.\n\n--- Press Enter to return ---\n");in.nextLine();
        }
    }
}
