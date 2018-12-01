package viewmodel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles audio related events.
 */
public class AudioManager {
    private static AudioManager instance = new AudioManager();
    //keep a reference to the sound until it finishes playing, to prevent GC from prematurely recollecting it
    private final Set<MediaPlayer> soundPool = Collections.newSetFromMap(new ConcurrentHashMap<MediaPlayer, Boolean>());
    private boolean enabled = true;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Plays the sound. If disabled, simply return.
     * <p>
     * Hint:
     * {@link MediaPlayer#play()} and {@link MediaPlayer#dispose()} are used.
     * When creating a new MediaPlayer object, add it into the soundpool before playing it.
     * Also set a callback for when the sound has completed: remove it from the soundpool, and dispose of the
     * sound in a newly created thread using the dispose method.
     * <p>
     * Make sure to set any threads you create as daemon threads so that the application will exit cleanly.
     *
     * @param name the name of the sound file to be played, excluding .mp3
     */


    private void playFile(String name) {
        //TODO
        if(enabled){
//            Media media;
//            MediaPlayer mediaPlayer;
//            if(name.equals("move")){
//                media = new Media(AudioManager.class.getResource("/assets/audio/move.mp3").toString());
//                 mediaPlayer = new MediaPlayer(media);
//            }
//            else if(name.equals("win")){
//                media = new Media(AudioManager.class.getResource("/assets/audio/win.mp3").toString());
//                 mediaPlayer = new MediaPlayer(media);
//            }
//            else {
//                media = new Media(AudioManager.class.getResource("/assets/audio/deadlock.mp3").toString());
//                mediaPlayer = new MediaPlayer(media);
//
//            }
//            soundPool.add(mediaPlayer);
//            mediaPlayer.play();
//            mediaPlayer.setOnEndOfMedia(()->{soundPool.clear();mediaPlayer.dispose();});



            if(name.equals("move")) {
                Media media;
                MediaPlayer mediaPlayer;
                media = new Media(AudioManager.class.getResource("/assets/audio/move.mp3").toString());
                mediaPlayer = new MediaPlayer(media);
                soundPool.add(mediaPlayer);
                mediaPlayer.play();


                mediaPlayer.setOnEndOfMedia(() -> { soundPool.clear(); mediaPlayer.dispose();});

            }
            if(name.equals("win")) {
                Media media;
                MediaPlayer mediaPlayer;
                media = new Media(AudioManager.class.getResource("/assets/audio/win.mp3").toString());
                mediaPlayer = new MediaPlayer(media);

                soundPool.add(mediaPlayer);


                var thread = new Thread(
                        () -> { soundPool.clear();
                            mediaPlayer.dispose(); }
                );

//                try {
//                thread.sleep(2000);
                    mediaPlayer.play();
//                    if(mediaPlayer.getStatus()== MediaPlayer.Status.PLAYING){System.out.println("my god ... how come...");thread.sleep(2000);}
//                thread.join();
//                    System.out.println("Current Thread: " + Thread.currentThread().getName());
//                } catch (InterruptedException e) {}

                thread.setDaemon(true);

                mediaPlayer.setOnEndOfMedia(thread);
//                soundPool.add(mediaPlayer);
//                mediaPlayer.play();
//
//                var thread = new Thread(
//                        () -> {soundPool.clear();
//                            mediaPlayer.dispose();}
//                );
//                thread.setDaemon(true);
//                thread.setPriority(Thread.MAX_PRIORITY);
//                mediaPlayer.setOnEndOfMedia(thread);
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {}
            }
            else if(name.equals("deadlock"))
            {
                Media media;
                MediaPlayer mediaPlayer;
                media = new Media(AudioManager.class.getResource("/assets/audio/deadlock.mp3").toString());
                mediaPlayer = new MediaPlayer(media);

                soundPool.add(mediaPlayer);

                mediaPlayer.play();
                var thread = new Thread(
                        () -> { soundPool.clear();
                            mediaPlayer.dispose(); }
                );
//                try {


//                    if(mediaPlayer.getStatus()== MediaPlayer.Status.PLAYING){mediaPlayer.play();System.out.println("my god ... how come...");thread.sleep(2000);}

//                    System.out.println("Current Thread: " + Thread.currentThread().getName());
//                } catch (InterruptedException e) {}

                thread.setDaemon(true);
                mediaPlayer.setOnEndOfMedia(thread);
            }
        }
        else{return;}
    }

    public void playMoveSound() {
        playFile("move");
    }

    public void playWinSound() {
        playFile("win");
    }

    public void playDeadlockSound() {
        playFile("deadlock");
    }
}
