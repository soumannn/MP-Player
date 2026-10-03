package application;
import javafx.concurrent.Task;
public class GetPlaylistTask extends Task<Playlist> {
	    private String dateiName;
	    private PlaylistManager playlistManager;

	    public GetPlaylistTask(PlaylistManager playlistManager, String playlistFileName) {
	        this.dateiName = playlistFileName;
	        this.playlistManager = playlistManager;
	    }

	    @Override
	    protected Playlist call() throws Exception {
	        // Implementar la lógica para cargar la lista de reproducción aquí
	        Playlist loadedPlaylist = playlistManager.getPlaylist(dateiName);
	        return loadedPlaylist;
	    }
	}