package application;
import java.util.ArrayList;
import java.util.Random;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MP3Player {
	private SimpleMinim minim; 
	private Playlist actPlaylist;
	private SimpleAudioPlayer audioPlayer;
	private int currentSong = 0;
	private Track currentTrack;
	private ArrayList<Track> tracks;
	private boolean shuffleOn = false;
	private ObjectProperty<Track> currentTrackProperty; //listener?
	private ArrayList<Integer> lastTracksIndex = new ArrayList<>();
	private int lastIndex;
	private String currentPath = null;
	private boolean playing = false;
	private Track selectedTrack = null;
	
	public MP3Player() {
		minim = new SimpleMinim(true); 
		currentTrackProperty = new SimpleObjectProperty<>();
	}
	
	public void setPlaylist(Playlist actPlaylist) {
		this.actPlaylist = actPlaylist;
		
	} 
	public Playlist getPlaylist() {
		return actPlaylist;
	}
	
	public double getCurrentTimeInSeconds() {
	    if (audioPlayer != null) {
	        long currentPositionMillis = audioPlayer.position();
	        double currentPositionSeconds = currentPositionMillis / 1000.0; //convert to seconds
	        return currentPositionSeconds;
	    } else {
	        return 0.0; 
	    }
	}
	
	public ObjectProperty<Track> getCurrentTrackProperty() {
		return currentTrackProperty;	
	}
	
	public Track getCurrentTrack() {
		tracks = actPlaylist.getTracksList(); //save the arraylist with the songs in a local variable
		currentTrack = tracks.get(currentSong); //save the song in a local variable
		return currentTrack;
	}
	
	public void play() {    
       if(this.audioPlayer == null) {
			currentTrack = getCurrentTrack();
			currentTrackProperty.set(currentTrack);
			Thread playThread = new Thread(() -> {
				play(currentTrack.getPath());

	        });
	        playThread.setDaemon(true);
	        playThread.start();
       } else {
    	   	audioPlayer.play();
       }	
	}
	public void play (String fileName) {
		audioPlayer = minim.loadMP3File(fileName);
		audioPlayer.play();
	}
	public void playSelectedTrack(Track track) {
		if (playing) { //while playing
			if(track == selectedTrack) { //clicked on the same cell
				pause(); 
			 	playing = false;
			} else { //clicked on other cell
				pause();
				this.audioPlayer = null;
				Thread playThread = new Thread(() -> {
	    			play(track.getPath());
	            });
	            playThread.setDaemon(true);
	            playThread.start();
	            selectedTrack = track;
	            playing = true;     
			}
        } else { //if not playing
        	if(track == selectedTrack) {
    			audioPlayer.play();
    			 playing = true;
    		} else {
	        	this.audioPlayer = null;
	        	Thread playThread = new Thread(() -> {
	    			play(track.getPath());
	            });
	            playThread.setDaemon(true);
	            playThread.start();
	            selectedTrack = track;
	            playing = true;
	        }
        }
	}
	
	public void pause() {
		audioPlayer.pause();
	}
	
	public synchronized void skip() {
		if (actPlaylist != null) {
			this.audioPlayer = null;
			if (!shuffleOn) {
				if (currentSong < tracks.size() - 1) {               
	                currentSong++;
	            	System.out.println("Track's index = " + currentSong); 
	            } else if (currentSong == tracks.size() - 1) {
	            	currentSong = 0;
	                System.out.println("The playlist starts again (index = 0)");
	            }	
			}else { //if shufle is on
				lastTracksIndex.add(currentSong);
				lastIndex = lastTracksIndex.size() - 1;
				int random = new Random().nextInt(tracks.size());
				//short term solution
				while(currentSong == random) {
					random = new Random().nextInt(tracks.size());
				}
				currentSong = random;
	        	System.out.println("Track's index = " + currentSong);
			}
        } else {
            System.out.println("No playlist selected");
        }
	}
	
	public synchronized void back() { 
		if (actPlaylist != null) {
			this.audioPlayer = null;
			if (!shuffleOn) {
	            if (currentSong > 0) {               
	                currentSong--;
	            	System.out.println("Track's index = " + currentSong);
	            } else if(currentSong == 0) {
	                System.out.println("You are playing the first song! (index=0)");
	            }
	        } else { //if shuffle is on
	        	 if (lastIndex > 0) {
	        		 currentSong = lastTracksIndex.get(lastIndex);
	 	        	lastIndex--; 
	 	        	System.out.println("Track's index = " + currentSong);
	        	 } else if(lastIndex == 0) {
		                System.out.println("You are playing the first song! (index=0)");
	        	 }
	        }	
		} else {
            System.out.println("No playlist selected");
        }
	}
	
	public void shuffle(boolean on) {
		if (actPlaylist != null) {  
		if (on) {
				shuffleOn = true;
				System.out.println("Shuffle ON");
            } else {
            	shuffleOn = false;
            	lastTracksIndex.clear();
            	lastIndex = 0;
				System.out.println("Shuffle OFF");
            }
        } else {
            System.out.println("No playlist selected");
        }
	}
	
	public void setVolume(float value) {
		//gain-Methode simple audio player? 2.Uebungsblatt
		  audioPlayer.setGain(value);
	}
}
