package components;

public class TrackDummy {
	
	private String title;
	private String artist;
	
	public TrackDummy(String title, String interpret) {
		super();
		this.title = title;
		this.artist = interpret;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String toString() {
		return title + " - " + artist;
	}
}
