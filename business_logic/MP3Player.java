package business_logic;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Collections;

public class MP3Player {
    SimpleMinim minim = new SimpleMinim();
    SimpleAudioPlayer audioPlayer;
    PlaylistManager playlistManager;
    int counter = 0;
    boolean pause;
    boolean isLooped;
    Thread timeThread;
    SimpleObjectProperty<Track> currentTrack;
    SimpleIntegerProperty position;

    public MP3Player(PlaylistManager playlistManager) {
        position = new SimpleIntegerProperty();
        currentTrack = new SimpleObjectProperty<Track>();
        currentTrack.set(playlistManager.getSelected().getTracks().get(0));
        this.playlistManager = playlistManager;
        audioPlayer = minim.loadMP3File(playlistManager.getSelected().getTracks().get(0).getPath());
    }

    public void play() {
        if(!audioPlayer.isPlaying()) {
            new Thread() {
                public void run() {
                    pause = false;
                    timeThread();
                    audioPlayer.play();
                    if (!audioPlayer.isPlaying() && !pause) {
                        if(counter < playlistManager.getSelected().getTracks().size()) {
                            counter++;
                            audioPlayer = minim.loadMP3File(getCurrentTrack().getPath());
                            currentTrack.set(getCurrentTrack());
                            play();
                        }
                    }
                }
            }.start();
        } else {
            pause();
        }
    }

    public void timeThread() {
        timeThread = new Thread() {
            public void run() {
                while(!interrupted()) {
                    try {
                        sleep(1000);
                        position.set(audioPlayer.position());
                    } catch (InterruptedException e) {
                        this.interrupt();
                    }
                }
            }
        };
        timeThread.start();
    }

    public void newList() {
        pause();
        counter = 0;
        audioPlayer = minim.loadMP3File(getCurrentTrack().getPath());
        currentTrack.set(getCurrentTrack());
        play();
    }

    public void selectSong(int counter) {
        pause();
        audioPlayer = minim.loadMP3File(getCurrentTrack().getPath());
        currentTrack.set(getCurrentTrack());
        play();
    }

    public void loop() {
        if(!isLooped) {
            isLooped = true;
            pause();
            audioPlayer.loop();
        } else {
            isLooped = false;
            pause();
            play();
        }
    }

    public void shuffle() {
        Playlist bla = new Playlist("shuffle");

        for (Track e : playlistManager.getSelected().getTracks())
            bla.getTracks().add(e);

        Collections.shuffle(bla.getTracks());

        playlistManager.setSelected(bla);

        newList();
    }

    public void skipRight(double volume) {
        if(counter < playlistManager.getSelected().getTracks().size() - 1) {
            pause();
            counter++;
            audioPlayer = minim.loadMP3File(getCurrentTrack().getPath());
            currentTrack.set(getCurrentTrack());
            volume(volume);
            play();
        }
    }

    public void skipLeft(double volume) {
        if (counter > 0) {
            pause();
            counter--;
            audioPlayer = minim.loadMP3File(getCurrentTrack().getPath());
            currentTrack.set(getCurrentTrack());
            volume(volume);
            play();
        }
    }
    
    public void pause() {
        pause = true;
        audioPlayer.pause();
        timeThread.interrupt();
    }

    public void volume(double value){
        audioPlayer.setGain(0 + (-60) * (1 - (float)(value) / 100));
    }

    //getter and setter
    public SimpleAudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Track getCurrentTrack() {
        return getCurrentTrack();
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public SimpleObjectProperty<Track> currentTrackProperty() {
        return currentTrack;
    }

    public SimpleIntegerProperty positionProperty() {
        return position;
    }
}
