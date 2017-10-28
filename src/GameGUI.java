
import database.Database;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class GameGUI {
    
    static private gui.Window window;
    static String userid;
    
    public static void setUserID(String id)
    {
        userid = id;
    }
    
    public static void createAndShowGui() {
        window = new gui.Window();
        JFrame frame = new JFrame("Tower Challenge Mission");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(window);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(710, 540));
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        
        JMenuItem item = new JMenuItem("Save");
        menu.add(item);
        item.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save");
                Database.save();
            }
        }));
        
        JMenuItem item2 = new JMenuItem("Restore");
        menu.add(item2);
        item2.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database.restore(userid);
                window.update();
            }
        }));
        
        JMenuItem item3 = new JMenuItem("Reset");
        menu.add(item3);
        item3.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database.reset();
                window.update();
            }
        }));
        
        // exit the game
        JMenuItem item4 = new JMenuItem("Exit");
        menu.add(item4);
        item4.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        }));
        
        menubar.add(menu);
        JMenu menu2 = new JMenu("Music");
        final JMenuItem item21 = new JMenuItem("Stop");
        item21.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (item21.getText().equals("Stop")) {
                    window.stopPlaying();
                    item21.setText("Resume");
                } else {
                    window.playBack();
                    item21.setText("Stop");
                }
            }
        }));
        menu2.add(item21);
        menubar.add(menu2);
        frame.setJMenuBar(menubar);
    }
}
