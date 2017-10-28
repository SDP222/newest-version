package gui;

import database.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Field extends JPanel {
    public static BufferedImage buffer;
    static int d = 0;
    private ArrayList<JLabel> obj;

    Field() {
        super();
        obj = new ArrayList<>();
        setLayout(null);
        update();
    }

    void update() {
        for (JLabel anObj : obj) {
            remove(anObj);
        }
        BufferedImage image = null;
        BufferedImage imageD = null;
        try {
            image = ImageIO.read(new File("res/key.png"));
            imageD = ImageIO.read(new File("res/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Database.getObjects().length; i++) {
            for (int j = 0; j < Database.getObjects().length; j++) {
                ImageIcon img = null;
                switch (Database.getObjects()[j][i]) {
                    case 1:
                        img = new ImageIcon("res/player.png");
                        break;
                    case 2:
                        img = new ImageIcon("res/door.png");
                        break;
                    case 3:
                        img = new ImageIcon(Parameters.dye(image, new Color(255, 0, 0, 128)));
                        break;
                    case 4:
                        img = new ImageIcon(Parameters.dye(image, new Color(255, 0, 128, 128)));
                        break;
                    case 5:
                        img = new ImageIcon(Parameters.dye(image, new Color(0, 128, 0, 128)));
                        break;
                    case 6:
                        img = new ImageIcon(Parameters.dye(imageD, new Color(255, 0, 0, 128)));
                        break;
                    case 7:
                        img = new ImageIcon(Parameters.dye(imageD, new Color(255, 0, 128, 128)));
                        break;
                    case 8:
                        img = new ImageIcon(Parameters.dye(imageD, new Color(0, 128, 0, 128)));
                        break;
                    case 9:
                        img = new ImageIcon("res/exit.png");
                        break;
                    case 10:
                        img = new ImageIcon("res/helm.png");
                        break;
                    case 11:
                        img = new ImageIcon("res/shield.png");
                        break;
                    default:
                        break;
                }
                JLabel object = new JLabel(img);
                object.setBounds(i * 25, j * 25, 25, 25);
                obj.add(object);
                add(object);
            }
        }
        repaint();
        revalidate();
        updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buffer == null) {
            rebuildBuffer();
        }
        g.drawImage(buffer, 0, 0, this);
    }

    private void rebuildBuffer() {
        int w = getWidth();
        int h = getHeight();
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        BufferedImage images[] = new BufferedImage[5];
        try {
            images[0] = ImageIO.read(new File("res/rock.png"));
            images[1] = ImageIO.read(new File("res/sand.png"));
            images[2] = ImageIO.read(new File("res/grass.png"));
            images[3] = ImageIO.read(new File("res/wall.png"));
            images[4] = ImageIO.read(new File("res/exit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                g2d.drawImage(images[Database.getField()[y][x]], x * 25, y * 25, this);
            }
        }
    }
}

