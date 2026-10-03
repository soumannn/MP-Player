package application;

import javafx.scene.image.Image;

public class Track {
	private String title;
	private String album;
	private String artist;
	private String year;
	private String path;
	private double length;
	private Image albumCover;
	  private boolean isPlaying;
	
	public Track(String title, String album, String artist, String year, String path, Image albumCover, double duration) {
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.year = year;
		this.path = path;
		this.albumCover = albumCover;
		this.length = duration;	
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean playing) {
		isPlaying = playing;
	}
	    
	public Image getAlbumCover() {
		return albumCover;
	}
	public void setAlbumCover(Image albumCover) {
		this.albumCover = albumCover;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
}
