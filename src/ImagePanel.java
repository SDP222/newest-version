
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel {
    
    private Image backgroundImage;

    /**
     * Read and set image file
     * 
     * @param fileName 
     */
    public ImagePanel(String fileName)
    {
        try
        {
            backgroundImage = ImageIO.read(new File("res/" + fileName));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Function to paint the Image
     * 
     * @param g 
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
