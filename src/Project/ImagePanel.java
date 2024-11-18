package Project;

import javax.swing.*;
import java.awt.*;

/**
 * The ImagePanel class extends JPanel and is used to display an image as the background of the panel.
 */
public class ImagePanel extends JPanel {
    private Image img; // Image to be displayed as the background

    /**
     * Constructor to initialize the image and set the layout.
     * @param imgPath the path to the image file.
     * @param layout the layout manager to be set for the panel.
     */
    public ImagePanel(String imgPath, LayoutManager layout) {
        img = new ImageIcon(imgPath).getImage(); // Load the image from the specified path
        setLayout(layout); // Set the layout for the panel
    }

    /**
     * Override the paintComponent method to draw the image.
     * @param g the Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Draw the image, resizing to fit the panel
    }
}
