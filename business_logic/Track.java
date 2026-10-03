package business_logic;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class Track {
    private String title, album, artist, year, genre;
    private byte[] cover;
    private long length;
    private String path;

    public Track(Mp3File file, String path) {
        if(file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = file.getId3v1Tag();
            ID3v2 id3v2Tag = file.getId3v2Tag();
    
            title = id3v1Tag.getTitle();
            artist = id3v1Tag.getArtist();
            album = id3v1Tag.getAlbum();
            year = id3v1Tag.getYear();
            genre = id3v1Tag.getGenreDescription();
            cover = id3v2Tag.getAlbumImage();
        } else if (file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = file.getId3v2Tag();
    
            title = id3v2Tag.getTitle();
            artist = id3v2Tag.getArtist();
            album = id3v2Tag.getAlbum();
            year = id3v2Tag.getYear();
            genre = id3v2Tag.getGenreDescription();
            cover = id3v2Tag.getAlbumImage();
        }
        
        length = file.getLengthInMilliseconds();

        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public long getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }
    
    public String getPath() {
        return path;
    }

    public byte[] getCover() {
        return cover;
    }
}
