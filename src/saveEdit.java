import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class saveEdit {
    private static String userInput;

    public static float saveEdit(double time) throws IOException {
        JDialog dialog = new JDialog(); //Makes a dialog
        dialog.setTitle("Editing time " + time); //Creates the title
        dialog.setSize(400, 150);
        dialog.setModal(true);
        dialog.setResizable(false); //Makes it non-resize

        //Grabs icon as seen in main and subscreen
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/icon.png");
        dialog.setIconImage(icon.getImage());

        JPanel panel = new JPanel(); //New panel for adding text input
        JLabel label = new JLabel("Changing " + time + " to: "); //Tells us what the time is at the moment
        JTextField textField = new JTextField(20); //Adds the text field
        JButton submitButton = new JButton("Submit"); //Adds a button to submit the given change

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = textField.getText(); //Sets the given input to a variable
                dialog.dispose(); // Close the dialog
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //Quits the dialog and not other swing code when closed
        panel.add(label); //Adds the label
        panel.add(textField); //Adds the text field
        panel.add(submitButton); //Adds the submit button

        dialog.add(panel); //Adds the main panel to the dialog
        dialog.setVisible(true); //Show the dialog and wait for it to close

        // Handle the input and return the value
        if (userInput == null || userInput.trim().isEmpty()) {
            return (float) time; //Returns the original time if the given one isn't the right format
        }

        try {
            return Float.parseFloat(userInput); //Gives the user inout formatted if its the correct format (Not string)
        } catch (NumberFormatException ex) {
            return (float) time; //If any error shows up, return the original time
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(saveEdit(12.00));
    }
}
