package application;
import java.util.ArrayList;

public class Playlist {
	private String name;
	private ArrayList<Track> tracks;
	
	public Playlist(String name, ArrayList<Track> list) {
		this.name = name;
		this.tracks = list;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Track> getTracksList() {
		return tracks;
	}
	public void setTracksList(ArrayList<Track> list) {
		this.tracks = list;
	}
	
}
