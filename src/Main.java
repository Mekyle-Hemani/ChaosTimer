import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.io.*;
import javax.swing.Timer;

public class Main extends JFrame implements KeyListener {
    public static JFrame ui = new JFrame("Chaos Timer"); //Creates the JFrame
    public static JPanel panel = new JPanel(new BorderLayout()); //Creates a border layout
    public static JPanel bottomPanel = new JPanel(new BorderLayout());
    public static JPanel topPanel = new JPanel(new BorderLayout()); // Creates a panel for the top layout
    public static int backgroundState = 0; //This is the background state (Red, Black, Green)
    public static int timePressed = 0; //This is how long the space bar is pressed
    public static int state = 0; //This is the state of the timer
    public Timer timer; //This makes the timer
    public double time = 00.00; //Makes a double value that will hold the current time
    public static JLabel timerLabel; //This is the timer label that will continue to display your time
    public static JLabel timeDifference; //This is the difference between the current and last time label
    public static JLabel timeCompare; //This is the last time label

    public static JButton optionbutton = new JButton("Options"); //This is a button that will open up another menu

    public static int mainscreenx = 800; //This is the screen x size
    public static int mainscreeny = 450; //This is the screen y size
    public static int maintextsize = 120; //This is the main text size
    public static int subtextsize = 40; //This is the sub text size
    public static String displaySet; //This is what is displayed on the difference in time label

    public static boolean optionmenu = false;

    public static java.util.List<String> configSave;

    static {
        try {
            configManagement.verifyFile(); //Creates and checks the config file
            configSave = save.loadConfiguration(); //Saves the config file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Main() throws IOException {
        ui.setSize(mainscreenx, mainscreeny); //This sets the screen size to the created values
        ui.setResizable(false); //This makes the screen non-resizable
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This makes the app exit when closed
        ui.setContentPane(panel); //This is the content panel
        ui.addKeyListener(this);
        ui.setFocusable(true); //Makes it focusable
        ui.requestFocusInWindow(); //Focuses on this window
        setBackground(0); //Sets the background to black
        ui.setResizable(false); //Makes it non-resizable

        //This sets the icon by finding the icon.png in the parent dir of this code
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/icon.png");
        ui.setIconImage(icon.getImage());

        //This is the label that the timer will use
        timerLabel = new JLabel("00.00", SwingConstants.CENTER);
        timerLabel.setPreferredSize(new Dimension(mainscreenx, maintextsize)); //This sets the size to given variables

        //This will show the general difference in time from your current solve to your last
        timeDifference = new JLabel(difference(loadtime(), "0"), SwingConstants.RIGHT);
        timeDifference.setPreferredSize(new Dimension(mainscreenx / 2, subtextsize));

        timeCompare = new JLabel(loadtime(), SwingConstants.RIGHT); //This will show the most recent time
        timeCompare.setPreferredSize(new Dimension(mainscreenx / 2, subtextsize));

        try {
            //Try to load a font
            String path = System.getProperty("user.dir");
            String folderpath = path + "/font/font.ttf";
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File(folderpath)).deriveFont(120f); //Set font size
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(digitalFont);
            timerLabel.setFont(digitalFont); //Set the font to the timer label

            digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File(folderpath)).deriveFont(40f); //Make a smaller sized version
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(digitalFont);
            timeDifference.setFont(digitalFont); //Sets the smaller font to both of the time difference and time compare labels
            timeCompare.setFont(digitalFont);

        } catch (FontFormatException | IOException e) {
            //If the script cant find the font it will use a built in one
            e.printStackTrace();
            timerLabel.setFont(new Font("DialogInput", Font.PLAIN, maintextsize));
            timeDifference.setFont(new Font("DialogInput", Font.PLAIN, subtextsize));
            timeCompare.setFont(new Font("DialogInput", Font.PLAIN, subtextsize));
        }

        //This grabs the config files text colour
        timerLabel.setForeground(Color.decode(configSave.get(3))); //The decode is for decoding hex codes to whatever colour version they use (CMYK?)
        panel.add(timerLabel, BorderLayout.CENTER); //This adds it to the main center of the screen

        //Same thing for the other comparing text
        timeDifference.setForeground(Color.decode(configSave.get(3)));
        timeCompare.setForeground(Color.decode(configSave.get(3)));

        topPanel.add(timeCompare, BorderLayout.EAST); // Add timeCompare to the top-right
        topPanel.setBackground(Color.decode(configSave.get(0))); //This sets the background of the panel to black (Or user selected default)
        panel.add(topPanel, BorderLayout.NORTH); // Add topPanel to the top of the main panel

        //Creates an option button
        optionbutton.setPreferredSize(new Dimension(80, 20)); //Option button size
        optionbutton.setBackground(Color.decode(configSave.get(4))); //The button colour
        optionbutton.setForeground(Color.decode(configSave.get(0))); //The text of the button colour to separate from the actual button colour
        optionbutton.setFocusPainted(false); //Makes the user not focus on it too much so that the space bar still is registered
        bottomPanel.add(timeDifference, BorderLayout.EAST); //Adds a second bottom panel for bottom labels

        bottomPanel.setBackground(Color.decode(configSave.get(0))); //Sets it to the neutral background colour
        panel.add(bottomPanel, BorderLayout.SOUTH); //Add the panel to the main panel

        optionbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!optionmenu){
                        subscreen.displayoptions(); //Runs the sub-menu
                        optionmenu = true; //Says its open
                    }
                    ui.requestFocusInWindow(); //Focuses back to the main window after
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        bottomPanel.add(optionbutton, BorderLayout.WEST); //Also adds the button to the same bottom panel on the left

        ui.setVisible(true); //Makes the timer visible

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer(); //This will keep updating the timer every 10 ms
            }
        });
    }

    private void updateTimer() {
        time += 0.01; //Adds 0.01 to the timer
        DecimalFormat df = new DecimalFormat("0.00"); //Creates a decimal format
        timerLabel.setText(df.format(time)); //Sets the timer label to the added time plus its old time
    }

    private void setBackground(int changenum) throws IOException {
        //If the timer isn't logging and is ready to switch colours to show starting representation
        if (state == 0) {
            backgroundState = changenum; //This sets the background to a given variable
            if (backgroundState == 0) {
                panel.setBackground(Color.decode(configSave.get(0))); //Sets to neutral (black)
                state = 0; //Makes sure the timer hasn't gone off yet
            }
            if (backgroundState == 1) {
                panel.setBackground(Color.decode(configSave.get(1))); //Sets to ready (read)
                state = 0; //Makes sure the timer hasn't gone off yet
            }
            if (backgroundState == 2) {
                panel.setBackground(Color.decode(configSave.get(2))); //Sets to go (green)
                state = 1; //Lets the timer know it will start as soon as the user isn't holding space
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space")) {
            if (state == 2) {
                //If the timer is done and stopped
                timer.stop(); //Stop countdown
                DecimalFormat df = new DecimalFormat("0.00"); //Convert the time
                String stringformat = df.format(time); //As seen before

                try {
                    if (check(loadtime(), stringformat)) { //Finds the difference between the current time and last
                        timeDifference.setForeground(Color.decode(configSave.get(2))); //Green = good time
                        displaySet = "-" + difference(loadtime(), stringformat); //If it's faster add a - and show by how much
                    } else {
                        timeDifference.setForeground(Color.decode(configSave.get(1))); //Red = bad time
                        displaySet = "+" + (Float.parseFloat((difference(loadtime(), stringformat))) * (-1)); //Since it will return negative, do the same thing but with a +
                    }
                    timeDifference.setText(displaySet); //Update the text with the given calculation
                    timeCompare.setText(loadtime()); //Set the comparison to the last time
                    addtime(stringformat); //Add the new time to the save
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                state = 4; //Make the timer ready again
            } else {
                try {
                    setBackground(1); //Make the background red (Get ready)
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                timePressed += 1;
                //After .25s tell the timer to get ready to start
                if (timePressed >= 25) {
                    try {
                        setBackground(2); //Makes the screen green
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space")) {
            if (state == 0) { //If the user let go of space and the timer is not ready yet
                try {
                    setBackground(0); //Go back to black
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                timePressed = 0; //Make sure the timepressed by the user is 0
            }
            if (state == 1) { //But if the timer is ready to start
                state = 2; //Let the code know its started
                time = 0; //Make sure that the time is 0
                timer.start(); //Start the timer
                panel.setBackground(Color.decode(configSave.get(0))); //Make the panel black
            }
            if (state == 4) {
                //If the timer is already running and was told to stop... same thing as earlier (219)
                state = 0;
                timePressed = 0;
                try {
                    setBackground(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static void addtime(String time) throws IOException {
        //This takes all the last times, adds the new time, saves it to a txt file handled by another function
        java.util.List<String> times = save.loadtime(); //Grabs times list
        times.add(time); //Adds time
        save.savetime(times); //Saves it
    }

    public static String loadtime() throws IOException {
        java.util.List<String> times = save.loadtime(); //Load the save
        int length = times.size(); //Grab the size
        if (length == 0) {
            return Integer.toString(0); //Return 0 if the file has nothing
        }
        return times.get(length - 1); //Else, return the save
    }

    public static String difference(String lasttime, String currenttime) {
        //This takes two strings, converts them to floats, finds the difference, converts the difference to a double to look nicer
        float timeValue = Float.parseFloat(lasttime); //Converting the values...
        float timeDifference = Float.parseFloat(currenttime); //...
        timeValue -= timeDifference; //Finds the time difference
        return String.format("%.2f", timeValue); //Converts to a double and sends off the difference
    }

    public static boolean check(String lasttime, String currenttime) {
        //This script check if the last time was faster or not
        float timeValue = Float.parseFloat(lasttime); //Converts both to floats to do basic comparison
        float timeDifference = Float.parseFloat(currenttime); //...
        return (timeValue > timeDifference); //Returns if its bigger or not
    }

    public static float grabAverageOf(int averageOf) throws IOException {
        java.util.List<String> allTime = save.loadtime(); //This grabs all the saves
        float comparedTime = 0; //This makes a float where all the times will be added up from a certain range
        int timeSize = save.loadtime().size(); //Finds the total size of the save
        if (timeSize >= averageOf) { //As long as we haven't found all items wanted
            for (int i = 0; i < averageOf; i++) { //For each item...
                comparedTime += Float.parseFloat(allTime.get(timeSize - 1 - i)); //Add the item to the list
            }
        }
        Float foundAverage = (comparedTime / averageOf); //Find the average
        return Float.parseFloat(String.format("%.2f", foundAverage)); //Returns it transferred to a double
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

//Yeah its a lot of lines, 879 of them (Yes I counted)