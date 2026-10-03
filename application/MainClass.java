package application;

import java.io.IOException;
import java.util.HashMap;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import playerview.PlayerViewController;
import playlistview.PlaylistView;
import playlistview.PlaylistViewController;

public class MainClass extends Application {
	private MP3Player player;
	private HashMap<ViewName, Pane> primaryViews;
	private Stage primaryStage;
	private Pane playerView;
	private Pane playlistView;
	private PlaylistManager playlistManager;
	
	@Override
	public void init() throws UnsupportedTagException, InvalidDataException, IOException {
		playlistManager = new PlaylistManager();
		player = new MP3Player();
		//add Playlists
		playlistManager.getPlaylist("Playlist two.m3u");
		player.setPlaylist(playlistManager.getPlaylist("Testplaylist.m3u"));
		
		/*GetPlaylistTask playlistTask = new  GetPlaylistTask(playlistManager, "Testplaylist.m3u");
		    playlistTask.setOnSucceeded(event -> {
		    	 try {
		    	     player.setPlaylist(playlistTask.getValue());
		    	 } catch (Exception e) {
		    	     e.printStackTrace();
		    	 }
		     });
		 Thread thread = new Thread(playlistTask);
		 thread.setDaemon(true);
		 thread.start();*/
		 
		primaryViews = new HashMap<>();
		PlayerViewController playerController = new PlayerViewController(player, this);
		playerView = playerController.getRoot();
		PlaylistViewController playlistController = new PlaylistViewController(player, this);
		playlistView = playlistController.getRoot();
		//playlistView = new PlaylistView();
		primaryViews.put(ViewName.PlayerView, playerView);
		primaryViews.put(ViewName.PlaylistView, playlistView);
	}
	@Override
    public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		BorderPane root = new BorderPane(); //or pane?	
		//root.setStyle("-fx-background-color: lightgrey;");
    	Scene scene = new Scene(root, 425, 550);
    	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    	
    	primaryStage.setTitle("MP3 Player");
    	primaryStage.setScene(scene);
    	primaryStage.setMinHeight(550);
    	primaryStage.setMinWidth(380);
		switchView(ViewName.PlayerView);
        primaryStage.show(); 
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        //Add an icon:
        /*InputStream iconStream = getClass().getResourceAsStream("/icon.png");
		Image image = new Image(iconStream);
		primaryStage.getIcons().add(image);*/ 
    }
	@Override
	public void stop() {
	}
	
	public void switchView (ViewName name) {
		Scene currentScene = primaryStage.getScene();
		Pane nextView = primaryViews.get(name);
		if (nextView != null) {
			currentScene.setRoot(nextView);
		}
	}
	
    public static void main(String[] args) {
    	launch(args); 		
    }
}
