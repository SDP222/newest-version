package gui;

import database.Database;
import game.Player;
import java.awt.Font;
import music.AudioPlayer;
import music.PlayingTimer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;


public class Window extends JPanel {
    private static final String[] keys = new String[]{"UP", "DOWN", "LEFT", "RIGHT"};
    private Parameters paramW;
    private Field fieldW;
    private Player player;
    private File music;
    private String audioFilePath;
    private boolean isPlaying = false;
    private boolean isPause = false;
    private AudioPlayer aplayer = new AudioPlayer();
    private Thread playbackThread;
    private PlayingTimer timer;

    public Window() {
        super();
        setLayout(null);
        player = new Player();
        paramW = new Parameters();
        paramW.setBounds(0, 0, 200, 500);
        add(paramW);
        fieldW = new Field();
        fieldW.setBounds(200, 0, 700, 500);
        add(fieldW);
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false);
        KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false);
        KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false);
        KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false);
        inputMap.put(upKey, keys[0]);
        inputMap.put(downKey, keys[1]);
        inputMap.put(leftKey, keys[2]);
        inputMap.put(rightKey, keys[3]);
        actionMap.put(keys[0], new KeyAction(0));
        actionMap.put(keys[1], new KeyAction(1));
        actionMap.put(keys[2], new KeyAction(2));
        actionMap.put(keys[3], new KeyAction(3));
        music = new File("res/music.wav");
        openFile();
        
        // start = new JPanel();
        final JButton start = new JButton("Start");
        
        start.setFont(new Font("Arial", Font.PLAIN, 50));
       
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(start);
                update();
            }
        });
       
        start.setBounds(200, 0, 500, 500);
        add(start);
    }

    private void openFile() {
        audioFilePath = music.getAbsolutePath();
        if (isPlaying || isPause) {
            stopPlaying();
            while (aplayer.getAudioClip().isRunning()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        playBack();
    }

    public void playBack() {
        timer = new PlayingTimer();
        timer.start();
        isPlaying = true;
        playbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    aplayer.load(audioFilePath);
                    timer.setAudioClip(aplayer.getAudioClip());
                    aplayer.play();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "I/O error while playing the audio file!", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        playbackThread.start();
    }

    public void stopPlaying() {
        isPause = false;
        timer.reset();
        timer.interrupt();
        aplayer.stop();
        playbackThread.interrupt();
    }

    public void update() {
        if (Database.getDataPlayer()[0] == -1) {
            JOptionPane.showMessageDialog(null, "CONGRATULATION!\n" +
                    "You end game in 20 sec!");
            final JLabel finish = new JLabel();
            finish.setBounds(200, 0, 700, 500);
            add(finish);
        }
        fieldW.update();
        paramW.update();
    }

    private class KeyAction extends AbstractAction {
        private int onKeyRelease;

        KeyAction(int key) {
            this.onKeyRelease = key;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            player.go(keys[onKeyRelease]);
            update();
            repaint();
            revalidate();
        }
    }
}
