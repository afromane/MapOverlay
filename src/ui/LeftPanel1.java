package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LeftPanel1 extends JPanel {

    private static final long serialVersionUID = 1L;

    public LeftPanel1() {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(600, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            File imageFile = new File("src/images/bg-left.jpg");

            if (!imageFile.exists()) {
                System.err.println("File does not exist.");
                return;
            }

            BufferedImage image = ImageIO.read(imageFile);

            if (image != null) {
                // Draw the image to cover the entire area
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                System.err.println("Invalid or unsupported image format.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the image file: " + e.getMessage());
        }
    }
}
