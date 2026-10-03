package javafx;

import business_logic.MP3Player;
import business_logic.PlaylistManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PlaylistViewController {

    private Main application;
    private Pane root;

    MP3Player player;
    PlaylistManager playlistManager;
    int counter = 0;

    @FXML
    public ListView listView;
    @FXML
    public Button goBack;
    @FXML
    public Label playlistName;
    @FXML
    public Button skipLeft;
    @FXML
    public Button skipRight;
    @FXML
    public HBox playlistHeader;
    @FXML
    public HBox playlistBottom;
    @FXML
    public Label currentSong;
    @FXML
    public GridPane playlistGrid;

    public PlaylistViewController(Main application, MP3Player player, PlaylistManager playlistManager) {
        this.application = application;
        this.player = player;
        this.playlistManager = playlistManager;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PlaylistView.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        listView.setOnMouseClicked(event -> {
            player.setCounter((Integer.parseInt(String.valueOf(listView.getSelectionModel().getSelectedItem().toString().charAt(0))) - 1));
            player.selectSong((Integer.parseInt(String.valueOf(listView.getSelectionModel().getSelectedItem().toString().charAt(0))) - 1));
        });

        currentSong.getStyleClass().add("currentSong");
        goBack.getStyleClass().add("goBack");
        skipLeft.getStyleClass().add("arrowLeft");
        skipRight.getStyleClass().add("arrowRight");
        playlistHeader.getStyleClass().add("header");
        playlistName.getStyleClass().add("playlistName");
        playlistBottom.getStyleClass().add("header");
        playlistGrid.getStyleClass().add("header");

        playlistName.setText(playlistManager.getSelected().getName());

        for(int i = 0; i < playlistManager.getSelected().getTracks().size(); i++) {
            listView.getItems().add((i + 1) + " " + playlistManager.getSelected().getTracks().get(i).getTitle());
        }

        goBack.addEventHandler(ActionEvent.ACTION,
                event -> application.switchView(Main.Scenes.PlayerScene)
        );

        skipRight.addEventHandler(ActionEvent.ACTION,
                event -> {
                    if(++counter == playlistManager.getPlaylists().size()){
                        counter = 0;
                    }
                    playlistManager.selectPlaylist(counter);
                    updateGUI();
                }
        );

        skipLeft.addEventHandler(ActionEvent.ACTION,
                event -> {
                    if(--counter < 0){
                        counter = playlistManager.getPlaylists().size() - 1;
                    }
                    playlistManager.selectPlaylist(counter);
                    updateGUI();
                }
        );
    }

    public Pane getRoot() {
        return root;
    }

    public void updateGUI() {
        playlistName.setText(playlistManager.getSelected().getName());
        currentSong.setText("Currently playing: " + playlistManager.getSelected().getTracks().get(player.getCounter()).getTitle() + " by " + playlistManager.getSelected().getTracks().get(player.getCounter()).getArtist());
        listView.getItems().clear();
        for(int i = 0; i < playlistManager.getSelected().getTracks().size(); i++) {
            listView.getItems().add((i + 1) + " " + playlistManager.getSelected().getTracks().get(i).getTitle());
        }
    }
}
