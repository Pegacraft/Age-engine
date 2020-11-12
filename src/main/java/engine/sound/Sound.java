package engine.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Use this class to handle sounds.
 */
public class Sound {
    private final URL url;
    private Clip clip;
    private long startPos = 0;
    private AudioInputStream audioIn;

    /**
     * Import the sound, that you want to work with here.
     *
     * @param path The path to the sound.
     */
    public Sound(String path) {
        url = this.getClass().getClassLoader().getResource(path);
        try {
            // Open an audio input stream.
            assert url != null;
            audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the imported sound.
     */
    public void playSound() {
        stopSound();
        clip.start();
    }

    /**
     * Stops the currently playing sound.
     */
    public void stopSound() {
        clip.stop();
        clip.close();
        reset();
    }

    /**
     * Changes the volume the sound.
     *
     * @param db the value in decibel you want to change
     */
    public Sound setVolume(float db) {
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(db);
        return this;
    }

    /**
     * Stets the starting point of the sound you wanna play.
     *
     * @param startPos The timestamp in ms.
     */
    public Sound setPlayLocation(long startPos) {
        if (startPos > 0 && startPos < clip.getMicrosecondLength()) {
            this.startPos = 10000 * startPos;
            clip.stop();
            clip.close();
            reset();
            clip.setMicrosecondPosition(this.startPos);
        }
        return this;
    }

    private void reset() {
        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            clip.open(audioIn);
            clip.setMicrosecondPosition(startPos);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
