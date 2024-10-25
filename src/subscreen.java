import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

public class subscreen {
    public static List<String> configSave;
    static {
        try {
            configSave = save.loadConfiguration(); //Grabs the config file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static JPanel buttonPanel = new JPanel(); //Makes the button panel
    public static JLabel displayLabel = new JLabel("Select an option", SwingConstants.CENTER); //Adds a label to help fill-in with the user instructions
    public static JButton button; //New button
    public static String[] buttonNames = {"Times", "Averages", "Colors", "Export", "Default", "Quit"}; //These are all the option buttons
    public static String[] colourbuttons = {"Neutral", "Starting", "Ready", "Text", "Button"}; //These are all the colour buttons
    public static JButton buttonc; //New button for colours
    public static JFrame frame = new JFrame("Options"); //Make the main frame

    public static void displayoptions() throws IOException {
        configManagement.verifyFile(); //Makes the config file if it doesn't exist
        displayLabel.setVisible(false); //Makes the label with text hidden
        buttonPanel.removeAll(); //Removes all past buttons
        frame.setSize(400, 300); //Makes the screen size
        frame.setLayout(new BorderLayout());
        frame.setResizable(false); // This makes the screen non-resizable

        //Icon as seen in so many other scripts its actually crazy
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/icon.png");
        frame.setIconImage(icon.getImage());

        //Sets the background to be linked to the main file
        buttonPanel.setBackground(Color.decode(configSave.get(4)));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); //Creates the layout type in the button panel

        displayLabel.setFont(new Font("Arial", Font.PLAIN, 16)); //Sets the font type
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //Set border to black
        displayLabel.setOpaque(true);  //Allows background color to show
        displayLabel.setBackground(Color.decode(configSave.get(0))); //Links the label to be the same colour as every other text line in main
        displayLabel.setForeground(Color.decode(configSave.get(3))); //Sets the background to be the button colour

        // Create and add buttons for how many were created in the above starting array
        for (String buttonName : buttonNames) {
            button = new JButton(buttonName); //Makes a new button using the button name
            button.setBackground(Color.decode(configSave.get(4))); //Sets the colours to be the same as main
            button.setForeground(Color.decode(configSave.get(0))); //...
            button.setFocusPainted(false); //Makes it not-focused so that the user can still use the timer with this open
            buttonPanel.add(button); //Add each button

            // Add ActionListener to update the display label when a button is pressed
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (buttonName.equals("Times")) { //If the button is times
                        displayLabel.setVisible(false); //Hide the label
                        try {
                            List<String> times = save.loadtime();  // Load times
                            Collections.reverse(times); //Reverses the times as it is given backwards from oldest to most recent

                            // Clear previous buttons
                            buttonPanel.removeAll();
                            // Create buttons for each time
                            for (int i = 0; i < times.size(); i++) {
                                String time = times.get(i); //Finds the time
                                JButton timeButton = new JButton(time); //Makes a new button for each time titled time
                                timeButton.setBackground(Color.decode(configSave.get(4))); //Sets the colours to the other buttons
                                timeButton.setForeground(Color.decode(configSave.get(0))); //...
                                timeButton.setFocusPainted(false); //Makes it not-focused so that the user can still use the timer with this open

                                final int index = i;  // Capture the index in a final variable
                                timeButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            // Parse the clicked time value and update it
                                            double newValue = saveEdit.saveEdit(Double.parseDouble(time)); //Grabs the new wanted value using saveEdit.java
                                            times.set(index, String.valueOf(newValue));  // Update the time at the correct index
                                            Collections.reverse(times); //Reverse the times for reasons explained earlier
                                            save.savetime(times); //Save the time
                                            Collections.reverse(times); //Reverse the times to match the reversing pattern leaving identical results
                                            setcolours(); //Set the colours back to normal on main and this screen if they change and update buttons
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                });

                                buttonPanel.add(timeButton); //Add each time button made above
                            }

                            // Refresh the button panel
                            buttonPanel.revalidate();
                            buttonPanel.repaint();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        displayLabel.setVisible(true); //Make the label available to see
                    }

                    if (buttonName.equals("Averages")) {
                        //If the user wants to see averages, they can do so by pressing the button and being shown their average of 5 and 12
                        try {
                            displayLabel.setText("AO5: "+Main.grabAverageOf(5)+" AO12: "+Main.grabAverageOf(12));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    if (buttonName.equals("Colors")) {
                        buttonPanel.removeAll();
                        for (String colourButtonName : colourbuttons) { // Renamed to avoid conflict
                            buttonc = new JButton(colourButtonName); // Create a new button using the color name
                            buttonc.setBackground(Color.decode(configSave.get(4))); // Set background color
                            buttonc.setForeground(Color.decode(configSave.get(0))); // Set text color
                            buttonc.setFocusPainted(false); // Disable focus paint
                            buttonPanel.add(buttonc); // Add each button

                            // Add ActionListener to update the display label when a button is pressed
                            buttonc.addActionListener(new ActionListener() { // Changed to buttonc
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Example action: show which color is being changed
                                    displayLabel.setText("Changing colour of " + colourButtonName);
                                    new ColorPicker(colourButtonName+ "Color");
                                    try {
                                        //Changes the grabbed config file to selected colours
                                        List<String> config = save.loadConfiguration();
                                        if (colourButtonName.equals("Neutral")) {
                                            config.set(0, ColorPicker.getSelectedHexCode());
                                        }
                                        if (colourButtonName.equals("Starting")) {
                                            config.set(1, ColorPicker.getSelectedHexCode());
                                        }
                                        if (colourButtonName.equals("Ready")) {
                                            config.set(2, ColorPicker.getSelectedHexCode());
                                        }
                                        if (colourButtonName.equals("Text")) {
                                            config.set(3, ColorPicker.getSelectedHexCode());
                                        }
                                        if (colourButtonName.equals("Button")) {
                                            config.set(4, ColorPicker.getSelectedHexCode());
                                        }
                                        save.saveConfiguration(config); //Save the new config
                                        setcolours(); //Reset the colours
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    // Handle other color changes (Starting, Ready, etc.) similarly
                                }
                            });
                        }

                        // Refresh the button panel
                        buttonPanel.revalidate();
                        buttonPanel.repaint();
                    }

                    if (buttonName.equals("Export")) { //If it is on export mode
                        try {
                            if (fileConversion.makecsv()) { //Make the csv and say if it went well or not
                                displayLabel.setText("CSV Export Complete");
                            } else {
                                displayLabel.setText("CSV Export Failed");
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (buttonName.equals("Default")) {
                        try {
                            displayLabel.setText("Restored Default Configs");
                            configManagement.defaultconfig(); //This sets the default colours if selected
                            configSave = save.loadConfiguration(); //This keeps the default configs
                            setcolours(); //Resets all colours on main and this
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (buttonName.equals("Quit")) {
                        Main.optionmenu = !Main.optionmenu; //Tells the main file it can open a new tab
                        frame.dispose(); //Close this tab
                    }
                }
            });
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.optionmenu = false; //Lets the main.java open a new tab
                frame.dispose(); //Close this one
            }
        });

        // Add button panel to the left and label to the center
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(displayLabel, BorderLayout.CENTER);

        // Set the frame visibility
        frame.setVisible(true);
    }

    public static void setcolours() {
        //Noe to cut this short, this just takes all the colours that everything should be set to and sets it again
        buttonPanel.setBackground(Color.decode(configSave.get(4)));
        displayLabel.setBackground(Color.decode(configSave.get(0)));
        displayLabel.setForeground(Color.decode(configSave.get(3)));
        Component[] buttons = buttonPanel.getComponents();
        for (Component comp : buttons) {
            if (comp instanceof JButton) { //Sets each button too
                comp.setBackground(Color.decode(configSave.get(4)));
                comp.setForeground(Color.decode(configSave.get(0)));
            }
        }
        Main.timerLabel.setForeground(Color.decode(configSave.get(3)));
        Main.timeDifference.setForeground(Color.decode(configSave.get(3)));
        Main.timeCompare.setForeground(Color.decode(configSave.get(3)));
        Main.topPanel.setBackground(Color.decode(configSave.get(0)));
        Main.optionbutton.setBackground(Color.decode(configSave.get(4)));
        Main.optionbutton.setForeground(Color.decode(configSave.get(0)));
        Main.bottomPanel.setBackground(Color.decode(configSave.get(0)));
        Main.panel.setBackground(Color.decode(configSave.get(0)));
    }

    public static void main(String[] args) throws IOException {
        subscreen.displayoptions();
    }
}
