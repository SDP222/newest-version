package gui;

import database.Database;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Parameters extends JPanel {
    private JLabel floor;
    private JLabel level;
    private JLabel keys;
    private JLabel stats;
    private JLabel staff;

    Parameters() {
        super();
        setLayout(null);
        BufferedImage image = null;
        try {
            BufferedImage imageO = ImageIO.read(new File("res/key.png"));
            image = new BufferedImage(30, 30, imageO.getType());
            Graphics2D g = image.createGraphics();
            g.drawImage(imageO, 0, 0, 30, 30, null);
            g.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        floor = new JLabel("Floor");
        floor.setBounds(10, 10, 50, 20);
        add(floor);
        level = new JLabel("level");
        level.setBounds(80, 10, 50, 20);
        add(level);
        JLabel key1 = new JLabel(new ImageIcon(dye(image, new Color(255, 0, 0, 128))));
        key1.setBounds(50, 110, 30, 30);
        add(key1);
        JLabel key2 = new JLabel(new ImageIcon(dye(image, new Color(0, 0, 128, 128))));
        key2.setBounds(90, 110, 30, 30);
        add(key2);
        JLabel key3 = new JLabel(new ImageIcon(dye(image, new Color(0, 128, 0, 128))));
        key3.setBounds(130, 110, 30, 30);
        add(key3);
        keys = new JLabel("keys");
        keys.setBounds(10, 90, 180, 20);
        add(keys);
        stats = new JLabel("Floor");
        stats.setBounds(10, 140, 180, 200);
        add(stats);
        staff = new JLabel();
        staff.setBounds(10, 300, 100, 100);
        add(staff);
        update();
    }

    static BufferedImage dye(BufferedImage image, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return dyed;
    }

    void update() {
        int[] dataL = Database.getDataLevel();
        int[] dataPl = Database.getDataPlayer();
        floor.setText("Floor " + dataL[0]);
        level.setText("Level " + dataL[1]);
        keys.setText("Keys     " + dataPl[0] + "     " + dataPl[1] + "     " + dataPl[2]);
        stats.setText("<html><table>\n   <tr>\n  <td>HEALTH</td>\n  <td>" + dataPl[3] + "</td>\n" +
                "    </tr>\n  <tr>\n  <td>ATTACK</td>\n  <td>" + dataPl[4] + "</td>\n </tr>\n" +
                "    <tr>\n    <td>DEFENSE</td>\n    <td>" + dataPl[5] + "</td>\n   </tr>\n</table></html>");
        BufferedImage buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        try {
            Graphics2D g2d = buffer.createGraphics();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if ((x + (y * 3)) < Player.staff.size()) {
                        g2d.drawImage(Player.staff.get(x + (3 * y)).getImage(), x * 35, y * 35, this);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        staff.setIcon(new ImageIcon(buffer));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = getWidth();
        int height = getHeight();
        assert image != null;
        for (int x = 0; x < width; x += image.getWidth()) {
            for (int y = 0; y < height; y += image.getHeight()) {
                g.drawImage(image, x, y, this);
            }
        }
    }
}
