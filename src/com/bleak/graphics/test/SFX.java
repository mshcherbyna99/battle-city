package com.bleak.graphics.test;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public enum SFX {
    EXPLODE("sfx/boom.wav"),
    SHOOT("sfx/shot.wav"),
    BIGEXPLO("sfx/explosion.wav"),
    RICHCHET("sfx/ricochet.wav");

    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;
    private Clip clip;

    SFX(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            assert url != null;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning()) {
                clip.stop();
            }

            clip.setFramePosition(0);
            clip.start();
        } else {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    static void initSFX() {
        values();
    }
}
