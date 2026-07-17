package helpers;

import models.User;
import services.LoggedUser;

import javax.sound.sampled.*;
import java.net.URL;

//manage game sounds
public class SoundManager {

    private static Clip backgroundMusic;
    private static Clip shotSound;
    private static Clip explosionSound;
    private static Clip gameOverSound;
    private static Clip winSound;

    static {

        backgroundMusic =
                loadSound("/sounds/soundeffects/Main-Theme.wav");

        shotSound = loadSound("/sounds/soundeffects/short-laser-gun-shot.wav");

        explosionSound = loadSound("/sounds/soundeffects/explosion.wav");

        gameOverSound = loadSound("/sounds/soundeffects/arcade-game-over.wav");

        winSound = loadSound("/sounds/soundeffects/win.wav");
    }

    //load sound file
    private static Clip loadSound(String path) {

        try {

            URL soundUrl = SoundManager.class.getResource(path);

            if(soundUrl == null) {

                System.out.println(
                        "Sound file not found: " + path
                );

                return null;
            }

            Clip clip = AudioSystem.getClip();

            try(AudioInputStream audioStream =
                        AudioSystem.getAudioInputStream(soundUrl)) {

                clip.open(audioStream);
            }

            return clip;

        } catch(Exception e) {

            System.out.println(
                    "Could not load sound: " + path
            );

            e.printStackTrace();
            return null;
        }
    }

    //check and update background music
    public static void updateBackgroundMusic() {

        User user = LoggedUser.getUser();

        if(user != null && user.isMusicOn()) {

            playBackgroundMusic();

        } else {

            stopBackgroundMusic();
        }
    }

    //play background music in loop
    public static void playBackgroundMusic() {

        if(backgroundMusic == null || backgroundMusic.isRunning()) {
            return;
        }

        backgroundMusic.setFramePosition(0);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    //stop background music
    public static void stopBackgroundMusic() {

        if(backgroundMusic != null) {

            backgroundMusic.stop();
            backgroundMusic.setFramePosition(0);
        }
    }

    //play shot effect
    public static void playShotSound() {

        User user = LoggedUser.getUser();

        if(user != null && user.isShotSoundOn()) {
            playEffect(shotSound);
        }
    }

    //play crash or explosion effect
    public static void playExplosionSound() {

        User user = LoggedUser.getUser();

        if(user != null && user.isCrashSoundOn()) {

            playEffect(explosionSound);
        }
    }

    //play game over effect
    public static void playGameOverSound() {

        User user = LoggedUser.getUser();

        if(user != null && user.isGameOverSoundOn()) {

            playEffect(gameOverSound);
        }
    }

    //play win effect
    public static void playWinSound() {

        User user = LoggedUser.getUser();

        if(user != null && user.isGameOverSoundOn()) {

            playEffect(winSound);
        }
    }

    //restart and play one sound effect
    private static void playEffect(Clip clip) {

        if(clip == null) {
            return;
        }

        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
