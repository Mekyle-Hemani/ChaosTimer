import java.io.*;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class fileConversion {
    public static boolean makecsv() throws IOException {
        String csvpath = pathGrabbing.grabFolder("CSV save location"); // Finds the dir this file is in
        if (csvpath == null){
            return false;
        }
        String csvfilepath = csvpath + "/ChaosTimes.csv"; // This is the location it is trying to load from
        File filecheck = new File(csvfilepath); // This creates the object that will be checked for existence

        // If the file doesn't exist
        if (!filecheck.exists() || !filecheck.isFile()) {
            try {
                filecheck.getParentFile().mkdirs(); // Make the directories if they do not exist
                if (!filecheck.createNewFile()) { // If the file doesn't exist
                    return false;
                }
            } catch (IOException e) { //If an error is found...
                e.printStackTrace(); //Display the error
                return false;
            }
        }
        // Write the items from save.loadtime() to the made CSV
        List<String> data = save.loadtime(); //Load the data

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filecheck))) {
            for (String item : data) {
                //Write each line from the save.txt to the csv
                String csvLine = String.join(",", item);
                writer.write(csvLine);
                writer.newLine(); //New line every time
            }
            openat(csvpath);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); //Display the error
            return false;
        }
    }

    public static void openat(String path) {
        File file = new File(path); //Grabs the path
        if (Desktop.isDesktopSupported()) { //If supported
            try {
                Desktop.getDesktop().open(file); //Open the file in file explorer
            } catch (IOException e) {
                e.printStackTrace(); //Print error
            }
        }
    }
}
