import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class configManagement {
    public static void verifyFile() throws IOException {
        String path = System.getProperty("user.dir"); //Finds the dir of this file
        String folderpath = path + "/save"; //This is the target dir where the save file will be stored
        File foldercheck = new File(folderpath); //This creates the object that will be checked for existence
        if (!foldercheck.exists() && !foldercheck.isDirectory()) { //If the folder doesn't exist...
            boolean folderstate = foldercheck.mkdirs(); //Makes the folder
            if (!folderstate){ //If the folder state is not made...
                System.exit(0); //Exit
            }
        }
        String filepath = folderpath + "/saveConfig.txt"; //This is what we want the save file to be
        File filecheck = new File(filepath); //This creates the object that will be checked for existence
        if (!filecheck.exists() && !filecheck.isFile()) { //If the file doesn't exist
            try {
                filecheck.getParentFile().mkdirs(); //Make the file
                if (!filecheck.createNewFile()) { //If the file doesn't exist
                    System.exit(0); //Exit
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(filepath)); //Opens the writer to the file location
                List<String> configSave = new ArrayList<>();

                configSave.add("#000000"); //Start screen 1
                configSave.add("#FF0000"); //Start screen 2
                configSave.add("#00FF00"); //Start screen 3

                configSave.add("#FFFFFF"); //Text colour

                configSave.add("#D3D3D3"); //Make a button

                System.out.println("Default config saved");

                for (String item : configSave) { //For each string in the given list
                    writer.write(item); //Write it using writer
                    writer.newLine(); //Go to the next line
                }
                writer.close(); //Close writer
            } catch (IOException e) { //If an error is found...
                e.printStackTrace(); //Display the error
            }
        }
    }

    public static void defaultconfig() throws IOException {
        String path = System.getProperty("user.dir"); //Finds the dir of this file
        String folderpath = path + "/save"; //This is the target dir where the save file will be stored
        String filepath = folderpath + "/saveConfig.txt"; //This is what we want the save file to be
        File filecheck = new File(filepath); //This creates the object that will be checked for existence
        filecheck.getParentFile().mkdirs(); //Make the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath)); //Opens the writer to the file location
        List<String> configSave = new ArrayList<>();

        configSave.add("#000000"); //Start screen 1
        configSave.add("#FF0000"); //Start screen 2
        configSave.add("#00FF00"); //Start screen 3

        configSave.add("#FFFFFF"); //Text colour

        configSave.add("#D3D3D3"); //Make a button

        for (String item : configSave) { //For each string in the given list
            writer.write(item); //Write it using writer
            writer.newLine(); //Go to the next line
        }
        writer.close(); //Close writer
    }
}
