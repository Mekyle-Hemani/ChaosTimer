import javax.swing.*;
import java.awt.*;

public class ColorPicker extends JFrame {
    public static String selectedHexCode; //This is the hexcode that will be given back and returned

    public ColorPicker(String title) {
        setResizable(false); //Make it non-resizable

        //Sets the icon as seen before by grabbing the parent dir and looking for a file
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/icon.png");
        setIconImage(icon.getImage());

        //This uses a built-in library for colour choosing
        Color color = JColorChooser.showDialog(null, title, Color.WHITE);
        if (color != null) {
            selectedHexCode = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()); //If a colour is selected, encode to hex
        }
    }

    public static String getSelectedHexCode() {
        return selectedHexCode; //Return the hex
    }

    public static void main(String[] args) {
        new ColorPicker("Test");

        System.out.println(getSelectedHexCode());
    }
}