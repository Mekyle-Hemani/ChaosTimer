import javax.swing.*;
import java.io.File;

public class pathGrabbing {
    public static String grabFile(String windowTitle) {
        JFrame frame = new JFrame(windowTitle); //Sets the window title to the given one in the function
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes the frame when the entire tab is closed
        frame.setSize(400, 200); //Sets the size
        frame.setAlwaysOnTop(true); //Make it always take layer priority

        JFileChooser fileChooser = new JFileChooser(); //Grabs swings library for dir selecting
        fileChooser.setDialogTitle(windowTitle); //The prompt

        int userSelection = fileChooser.showOpenDialog(frame); //This is changed depending on if the user has selected something yet
        if (userSelection == JFileChooser.APPROVE_OPTION) { //If the user's item has been approved
            File fileToOpen = fileChooser.getSelectedFile(); //Makes the selected dir a file type
            return fileToOpen.getAbsolutePath(); //Return the file path
        } else {
            return null; //Returns null if nothing was selected yet
        }

    }

    public static String grabFolder(String windowTitle) {
        JFrame frame = new JFrame(windowTitle); //Sets the window title to the given one in the function
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes the frame when the entire tab is closed
        frame.setSize(400, 200); //Sets the size
        frame.setAlwaysOnTop(true); //Make it always take layer priority

        JFileChooser folderChooser = new JFileChooser(); //Grabs swings library for dir selecting
        folderChooser.setDialogTitle(windowTitle); //The prompt

        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Makes it only allow selecting dirs

        int userSelection = folderChooser.showOpenDialog(frame); //This is changed depending on if the user has selected something yet
        if (userSelection == JFileChooser.APPROVE_OPTION) { //If the user's item has been approved
            File folderToOpen = folderChooser.getSelectedFile(); //Makes the selected dir a file type
            return folderToOpen.getAbsolutePath(); //Returns the folder dir
        } else {
            return null; //Returns null if nothing was selected yet
        }

    }
}