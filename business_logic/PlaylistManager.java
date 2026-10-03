package business_logic;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import de.hsrm.mi.prog2.TextIO;

public class PlaylistManager {
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private Playlist selected;
    private File filePath;

    public PlaylistManager(String path) {
        filePath = new File(path);
        playlistReader();
    }

    public void playlistReader() {
        File[] filesList = filePath.listFiles();

        for (File m3u : filesList) {
            Playlist playlist = new Playlist(m3u.toString().substring(filePath.toString().length() + 1, m3u.toString().length() - 4));
            try {
                List<String> data = TextIO.read(new File(filePath + "/" + playlist.getName() + ".m3u"));
                for (String dataFetch : data) {
                    String[] parameter = dataFetch.split("\\\\r?\\\\n");
                    if(!parameter[0].startsWith("#")) {
                        playlist.addTrack(parameter[0]);
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            playlists.add(playlist);
        }
    }

    public void selectPlaylist(int i) {
        selected = playlists.get(i);
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getSelected() {
        return selected;
    }

    public void setSelected(Playlist selected) {
        this.selected = selected;
    }

    public void setFilePath(String path) {
        this.filePath = new File(path);
        playlists.clear();
        playlistReader();
    }
}