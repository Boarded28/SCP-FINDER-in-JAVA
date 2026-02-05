package program;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScpFinder {

    private static Document
    doc;

    private static String
    item;

    private static Map<String, String>
    seriesNames = new HashMap<>();

    public static void search() {
        ConsoleUtils.clearScreen(); 
        System.out.printf("""                                
            ==============[SEARCH]==============
            Type [back] to return to main menu.
            
            Enter item/Entry #
            [currently between 001-999]
            (e.g Entry #: 173)
            
            Entry #:%s"""," ");
                
        item = MainMenu.in.nextLine().toUpperCase();
        switch (item) {
            case ("BACK") -> {System.out.println("\n--- Press Enter to return ---\n");MainMenu.in.nextLine();}
            default -> {get(item);MainMenu.in.nextLine();}
        }
    }
    
    public static void load() {
        try {
            System.out.printf("""
                    Welcome to SCP finder!
                    version   : %s
                    created by: %s
                    build-date: %s
                    build-status: %s%n%n
                    """,ConsoleUtils.getVersion(),ConsoleUtils.getCreator(),ConsoleUtils.getBuildDate(),ConsoleUtils.getBuildStatus());
            System.out.println("Fetching database...");
            String seriesUrl  = "https://scp-wiki.wikidot.com/scp-series";
            Document seriesDoc = Jsoup.connect(seriesUrl)
                           .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                           .timeout(10000)
                           .followRedirects(false)
                           .get(); 
            Elements  links = seriesDoc.select("#page-content a[href^=\"/scp-\"]");
            for (Element item:links) {
                String href = item.attr("href");
                String num = href.replace("/scp-","");
                Element parent = item.parent();
                String text = parent!=null?parent.text():item.text();
                String name = text.contains(" - ")?text.split(" - ",2)[1]:"Unknown";
                seriesNames.put(num,name.replace("\"",""));
                System.out.println("[Debug] entry registered: SCP-" + num + ": " + name);
            }
        }         
        catch (java.net.SocketTimeoutException e) 
            {System.out.println("\nConnection timed out. Check your internet.\n\n--- Press Enter to return ---\n");MainMenu.in.nextLine();}
        catch (org.jsoup.HttpStatusException e) 
            {System.out.println("HTTP error: " + e.getStatusCode() + "\n\n--- Press Enter to return ---\n");MainMenu.in.nextLine();}
        catch (IOException e) 
            {System.out.println("\nNetwork error: " + e.getMessage() + "\n\n--- Press Enter to return ---\n");MainMenu.in.nextLine();}
        ConsoleUtils.clearScreen();
    }

    public static void get(String item) { 
        try {
            ConsoleUtils.clearScreen();  
            System.out.println("Searching...");
            String key = String.format("%03d", Integer.parseInt(item));
            String name = seriesNames.get(key);                      
            String url = String.format("https://scp-wiki.wikidot.com/scp-%s",key);
            Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36")
                            .timeout(10000)
                            .followRedirects(false)
                            .get();
            Element objClass = doc.select("#page-content p:contains(Object Class)").first();
            Element contProcedure = doc.select("#page-content p:contains(Special Containment Procedures)").first();     
            Element description = doc.select("#page-content p:contains(Description)").first();
            String obClss =objClass.text(), scp =  wrapText(contProcedure.text(),50), desc =  wrapText(description.text(),50);
            if (name!=null) {
                ConsoleUtils.clearScreen();
                System.out.printf("""
                        Item/Entry  : #%s
                        Nickname    : %s
                        %s

                        %s

                        %s
                        """, key, name, obClss, scp, desc);
            }
            else {ConsoleUtils.clearScreen();System.out.println("Information not found for SCP-" + key);}                    
        }
        catch (java.net.SocketTimeoutException e) 
            {System.out.println("\nConnection timed out. Check your internet.\n");}
        catch (org.jsoup.HttpStatusException e) 
            {System.out.println("HTTP error: " + e.getStatusCode() + "\n");}
        catch (IOException e) 
            {System.out.println("\nNetwork error: " + e.getMessage() + "\n");}
        System.out.println("\n--- Press Enter to return ---\n");
    }

    private static String wrapText(String text, int lineWidth) {
        StringBuilder wrap = new StringBuilder();
        String[] words = text.split("\\s+");
        int currentLength = 0;

        for (String word : words) {
            if (currentLength + word.length() + 1 > lineWidth) {wrap.append("\n");currentLength = 0;}
            if (currentLength > 0) {wrap.append(" ");currentLength++;}
            wrap.append(word);
            currentLength += word.length();
        }
        return wrap.toString();
    }
}
