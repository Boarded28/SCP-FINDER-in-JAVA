package program;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConsoleUtils {

    public static void clearScreen() {
        try {            
            new ProcessBuilder("cmd", "/c", "cls")
            .inheritIO()
            .start()
            .waitFor();
        } catch (Exception e) {

            for (int i=0;i<50;i++){System.out.println();}
        }
    }

    public static String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src\\program\\info.txt"));
            return prop.getProperty("version");
        } 
        catch (FileNotFoundException e) {return null;}
        catch (IOException e) {return null;}        
    }

    public static String getCreator() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src\\program\\info.txt"));
            return prop.getProperty("creator");
        } 
        catch (FileNotFoundException e) {return null;}
        catch (IOException e) {return null;}        
    }

    public static String getBuildStatus() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src\\program\\info.txt"));
            return prop.getProperty("status");
        } 
        catch (FileNotFoundException e) {return null;}
        catch (IOException e) {return null;}        
    }

    public static String getBuildDate() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src\\program\\info.txt"));
            return prop.getProperty("build-date");
        } 
        catch (FileNotFoundException e) {return null;}
        catch (IOException e) {return null;}        
    }

}
