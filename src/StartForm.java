
import database.Database;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import music.PlayingTimer;
import music.AudioPlayer;
import music.PlayingTimer;

public class StartForm extends JFrame implements ActionListener {

    private JButton playButton;
    private JButton musicButton;
    private JButton exitButton;
    private ImagePanel panel;

    private File music;
    private String audioFilePath;
    private boolean isPlaying = false;
    private boolean isPause = false;
    private AudioPlayer aplayer = new AudioPlayer();
    private Thread playbackThread;
    private PlayingTimer timer;

    /**
     * Construct GUI
     */
    public StartForm() {
        
        this.setTitle("Tower Challenge Mission");
        this.setSize(new Dimension(512, 512));
        this.setPreferredSize(new Dimension(512, 512));
        this.setLocationByPlatform(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.BLACK);

        this.panel = new ImagePanel("background.jpg");
        this.panel.setPreferredSize(new Dimension(512, 512));
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        this.add(this.panel);

        JLabel label = new JLabel("");
        label.setMaximumSize(new Dimension(300, 150));
        panel.add(label);

        //Add buttons
        this.playButton = new JButton("Play");
        this.playButton.setMaximumSize(new Dimension(320, 50));
        this.playButton.setBackground(Color.black);
        this.playButton.setForeground(Color.WHITE);
        this.playButton.addActionListener(this);
        this.playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.playButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panel.add(this.playButton);

        this.musicButton = new JButton("Music");
        this.musicButton.setMaximumSize(new Dimension(320, 50));
        this.musicButton.setBackground(Color.black);
        this.musicButton.setForeground(Color.WHITE);
        this.musicButton.addActionListener(this);
        this.musicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.playButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panel.add(this.musicButton);

        this.exitButton = new JButton("Exit");
        this.exitButton.setMaximumSize(new Dimension(320, 50));
        this.exitButton.setBackground(Color.black);
        this.exitButton.setForeground(Color.WHITE);
        this.exitButton.addActionListener(this);
        this.playButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(this.exitButton);

        music = new File("res/music.wav");
        
        //initialize database
        Database.init();

        this.pack();
    }

    public void stopPlaying() {
        isPause = false;
        timer.reset();
        timer.interrupt();
        aplayer.stop();
        playbackThread.interrupt();
    }

    private void playFile() {
        audioFilePath = music.getAbsolutePath();
        if (isPlaying) {
            stopPlaying();
            this.musicButton.setText("Play Music");
            isPlaying = false; 
        }
        else 
        {
            this.musicButton.setText("Stop Music");
            isPlaying = true;
            playBack();
        }
    }
    
    /**
     * Event handler to manage the event triggered
     * 
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
        
        Object src = event.getSource();
        
        if(src.equals(this.musicButton))
        {
            playFile();
        }
        else if(src.equals(exitButton))
        {
            System.exit(0);
        }
        else if(src.equals(playButton))
        {
            new PlayForm();
        }
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
}
