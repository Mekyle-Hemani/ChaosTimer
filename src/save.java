import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class save {
    //All of these are just a function that changes the value of a main function
    public static void savetime(List<String> input) throws IOException {
        save(input, "/saveTime.txt");
    }
    public static List<String> loadtime() throws IOException {
        return load("/saveTime.txt");
    }
    public static void saveConfiguration(List<String> input) throws IOException {
        save(input, "/saveConfig.txt");
    }
    public static List<String> loadConfiguration() throws IOException {
        return load("/saveConfig.txt");
    }

    public static void save(List<String> input, String filename) throws IOException {
        String path = System.getProperty("user.dir"); //Finds the dir of this file
        String folderpath = path + "/save"; //This is the target dir where the save file will be stored
        File foldercheck = new File(folderpath); //This creates the object that will be checked for existence
        if (!foldercheck.exists() && !foldercheck.isDirectory()) { //If the folder doesn't exist...
            boolean folderstate = foldercheck.mkdirs(); //Makes the folder
            if (!folderstate){ //If the folder state is not made...
                System.exit(0); //Exit
            }
        }
        String filepath = folderpath + filename; //This is what we want the save file to be
        File filecheck = new File(filepath); //This creates the object that will be checked for existence
        if (!filecheck.exists() && !filecheck.isFile()) { //If the file doesn't exist
            try {
                filecheck.getParentFile().mkdirs(); //Make the file
                if (!filecheck.createNewFile()) { //If the file doesn't exist
                    System.exit(0); //Exit
                }
            } catch (IOException e) { //If an error is found...
                e.printStackTrace(); //Display the error
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath)); //Opens the writer to the file location
        for (String item : input) { //For each string in the given list
            writer.write(item); //Write it using writer
            writer.newLine(); //Go to the next line
        }
        writer.close(); //Close writer
    }


    public static List<String> load(String filename) throws IOException {
        String path = System.getProperty("user.dir"); //Finds the dir this file is in
        String filepath = path + "/save/"+filename; //This is the locations it is trying to load from
        File filecheck = new File(filepath); //This creates the object that will be checked for existence
        if (!filecheck.exists() && !filecheck.isFile()) { //If the file doesn't exist
            try {
                filecheck.getParentFile().mkdirs(); //Make the file
                if (!filecheck.createNewFile()) { //If the file doesn't exist
                    System.exit(0); //Exit
                }
            } catch (IOException e) { //If an error is found...
                e.printStackTrace(); //Display the error
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(filepath)); //Creates an object of the data in the file
        String line; //This is what each line will be set to
        List<String> output = new ArrayList<>(); //This creates an array that will be given to the user
        while ((line = reader.readLine()) != null) { //For each line in the file
            output.add(line); //Add it to the output list
        }
        reader.close(); //Close the reader
        return output; //Give the user the list
    }
}