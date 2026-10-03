package application;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.image.Image;

public class PlaylistManager {
	private String title;
	private String album;
	private String artist;
	private String year;
	private double length;
	private Image albumCover;
	private byte[] imageData;
	private Playlist playlist;
	private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
	private int playlistCounter = 1;
	
	public PlaylistManager(){
	}
	
	public Playlist getPlaylist (String dateiName) throws UnsupportedTagException, InvalidDataException, IOException {
		//Paylist name lesen
		BufferedReader input = new BufferedReader (new FileReader(dateiName));
		String path;
		ArrayList<Track> tracks = new ArrayList<Track>();

		while((path = input.readLine()) != null) {
			//meta daten auslesen mit mp3agic
			Mp3File mp3file = new Mp3File(path);
			length = mp3file.getLengthInSeconds();
			
			if (mp3file.hasId3v1Tag()) {
				ID3v1 id3v1Tag = mp3file.getId3v1Tag();
				title = id3v1Tag.getTitle();
				album = id3v1Tag.getAlbum();
				artist = id3v1Tag.getArtist();
				year = id3v1Tag.getYear();
				//get Album Cover
				if (mp3file.hasId3v2Tag()) {
					ID3v2 id3v2Tag = mp3file.getId3v2Tag();
					imageData = id3v2Tag.getAlbumImage();
					if(imageData != null) {
						albumCover = new Image(new ByteArrayInputStream(imageData)); 
					} else {
						albumCover = new Image(getClass().getResourceAsStream("no album cover.jpg"));
					}
				}
			}
			Track song = new Track(title, album, artist, year, path, albumCover, length);
			
			tracks.add(song);
		}
		playlist = new Playlist("Playlist " + playlistCounter, tracks);
		playlistCounter++;
		playlists.add(playlist);
		return playlist;
		
	}
	/*public void printPaths() {
		for(Track t : tracks) {
			System.out.println(t.getPath());
		}
	}*/
}
