package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Staff {
    String path;

    public Staff(int i) {
        switch (i) {
            case 11:
                path = "res/helm.png";
                break;
            default:
                path = "res/shield.png";
                break;
        }
    }

    public BufferedImage getImage() throws IOException {
        return ImageIO.read(new File(path));
    }
}
