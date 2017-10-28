package music;

import javax.sound.sampled.Clip;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class counts playing time in the form of HH:mm:ss
 * It also updates the time slider
 */

public class PlayingTimer extends Thread {
    private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");
    private boolean isRunning = false;
    private boolean isPause = false;
    private boolean isReset = false;
    private long startTime;
    private long pauseTime;
    private Clip audioClip;

    public PlayingTimer() {
    }

    public void setAudioClip(Clip audioClip) {
        this.audioClip = audioClip;
    }

    public void run() {
        isRunning = true;
        startTime = System.currentTimeMillis();
        while (isRunning) {
            try {
                Thread.sleep(100);
                if (!isPause) {
                    if (audioClip != null && audioClip.isRunning()) {
                        int currentSecond = (int) audioClip.getMicrosecondPosition() / 1_000_000;
                    }
                } else {
                    pauseTime += 100;
                }
            } catch (InterruptedException ex) {
                // ex.printStackTrace();
                if (isReset) {
                    isRunning = false;
                    break;
                }
            }
        }
    }

    /**
     * Reset counting to "00:00:00"
     */
    public void reset() {
        isReset = true;
        isRunning = false;
    }

    void pauseTimer() {
        isPause = true;
    }

    void resumeTimer() {
        isPause = false;
    }
}