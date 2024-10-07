package csvprocessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Main{

    public static void main(String[] args) 
        throws FileNotFoundException, IOException{
        
        // Get csv file path from command line args
        if (args.length == 0 || args.length > 1) {
            System.out.println("Proper usage: --- 'filename.csv'");
            System.exit(0);
        }

        // Instantiate reader to read from file line by line
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        // Instantiate an empty AppList
        List<App> apps = new ArrayList<>();

        // Ignore first line of csv (Header)
        reader.readLine();

        while (true) {
            String line =  reader.readLine();

            if (line == null) {
                break;
            }

            // Split by this regex, split by comma (Note: to solve tricky situations where "Hi", "Hi, there", "How are you?", "Doing fine, how about you?")
            String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            String appName = parts[0];

            String appCategory = parts[1];

            String appRatingString = parts[2];

            double appRating = 0;

            // Apps without any rating will be assigned "-1" 
            if (appRatingString.equals("NaN")) {
                appRating = -1; 
            }
            else {
                appRating = Double.parseDouble(appRatingString);
            }

            apps.add(new App(appName, appCategory, appRating));
        }

        reader.close();

        /*
        For every category, display the following:
        1. Category name
        2. Highest rated app name and rating
        3. Lowest rated app name and rating
        4. Average rating for the category
        */

        // Create a HashMap with keys Category, and values list of Apps

        Map<String, ArrayList<App>> byCategory = new HashMap<>();
        
        for (App app : apps) {

            String currentCategory = app.getCategory();

            if (byCategory.containsKey(currentCategory)) {
                // If the category key is already in the hashmap, add the app into the arraylist
                byCategory.get(currentCategory).add(app);
            }
                // else add category key and instantiate an arraylist as value with app
            else {
                byCategory.put(currentCategory, new ArrayList<>());
                byCategory.get(currentCategory).add(app);
            }
        }

        System.out.print("\n\n\n");

        // Print out the categories available and the number of apps in each category
        int totalApps = 0;
        System.out.printf("| %-30s | %-15s |\n", "CATEGORY", "NO. OF APPS");
        for (String category : byCategory.keySet()) {
            totalApps += byCategory.get(category).size();
            System.out.printf("| %-30s | %-,15d |\n", category, byCategory.get(category).size());
        }
        System.out.printf("| %-30s | %-,15d |\n", "TOTAL", totalApps);


        // Remove apps without ratings
        for (String category : byCategory.keySet()) {
            ArrayList<App> appsByCategory = byCategory.get(category);
            Iterator<App> iterator = appsByCategory.iterator();
            while (iterator.hasNext()) {
                App app = iterator.next(); 
                if (app.getRating() == -1) {
                    iterator.remove();
                }
            }

        }

        System.out.print("\n\n\n");

         // Print out the categories available and the number of apps in each category
         int totalAppsWithRating = 0;
         System.out.printf("| %-30s | %-15s |\n", "CATEGORY", "NO. OF APPS");
         for (String category : byCategory.keySet()) {
             totalAppsWithRating += byCategory.get(category).size();
             System.out.printf("| %-30s | %-,15d |\n", category, byCategory.get(category).size());
         }
         System.out.printf("| %-30s | %-,15d |\n", "TOTAL", totalAppsWithRating);




        // Loop through every category, and find the highest rated app 

        System.out.print("\n\n\n");

        System.out.printf("| %-30s | %-100s |\n", "CATEGORY", "HIGHEST RATED APP");

        for (String category : byCategory.keySet()) {
            ArrayList<App> appsByCategory = byCategory.get(category);

            double highestRating = 0;
            App highestRatedApp = null;

            for (App app : appsByCategory) {
                
                if (app.getRating() > highestRating) {
                    highestRating = app.getRating();
                    highestRatedApp = app;
                }
            }
            System.out.printf("| %-30s | %-100s |\n", category, highestRatedApp);
        }

        // Loop through every category, and find the lowest rated app 

        System.out.print("\n\n\n");

        System.out.printf("| %-30s | %-100s |\n", "CATEGORY", "LOWEST RATED APP");

        for (String category : byCategory.keySet()) {
            ArrayList<App> appsByCategory = byCategory.get(category);

            double lowestRating = appsByCategory.get(0).getRating();
            App lowestRatedApp = null;

            for (App app : appsByCategory) {
                
                if (app.getRating() < lowestRating) {
                    lowestRating = app.getRating();
                    lowestRatedApp = app;
                }
            }
            System.out.printf("| %-30s | %-100s |\n", category, lowestRatedApp);
        }

        // Loop through every category and find average rating for each category

        System.out.print("\n\n\n");

        System.out.printf("| %-30s | %-10s | \n", "CATEGORY", "AVG RATING");

        for (String category : byCategory.keySet()) {
            ArrayList<App> appsByCategory = byCategory.get(category);

            double sumOfRating = 0;
            int noOfApps = 0;

            for (App app : appsByCategory) {
                sumOfRating += app.getRating();
                noOfApps ++;
            }

            double averageRating = 1.0 * sumOfRating / noOfApps;

            System.out.printf("| %-30s | %-10.1f |\n", category, averageRating);
        }

    }
}


// Note: 
// Assigned "LIFESTYLE" to Life Made WI-Fi Touchscreen Photo Frame --> searched online for app https://appadvice.com/app/life-made/1277553016