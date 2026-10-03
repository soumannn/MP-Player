package javafx;

import business_logic.MP3Player;
import business_logic.PlaylistManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;


public class PlayerViewController {
    private Main application;
    private Pane root;

    Label title;
    Label artist;
    Label time;
    Label timePlayed;
    Button skipRight;
    Button skipLeft;
    Button playButton;
    Button pauseButton;
    Button shuffle;
    Button loop;
    Button loopStop;
    Button settings;
    Slider volumeSlider;
    Slider slider;
    ImageView cover;
    ImageViewPane imgPane;
    Button goToPlaylistView;

    double volume = 100.0;

    MP3Player player;
    PlaylistManager playlistManager;
    PlayerView playerView;

    public PlayerViewController(Main application, MP3Player player, PlaylistManager playlistManager) {
        this.application = application;
        this.player = player;
        this.playlistManager = playlistManager;

        this.playerView = new PlayerView();
        title = playerView.title;
        artist = playerView.artist;
        skipRight = playerView.skipRight;
        skipLeft = playerView.skipLeft;
        playButton = playerView.playButton;
        pauseButton = playerView.pauseButton;
        volumeSlider = playerView.volumeSlider;
        slider = playerView.slider;
        cover = playerView.cover;
        timePlayed = playerView.timePlayed;
        time = playerView.time;
        shuffle = playerView.shuffle;
        imgPane = playerView.imgPane;
        loop = playerView.loop;
        loopStop = playerView.loopStop;
        goToPlaylistView = playerView.goToPlaylistView;
        settings = playerView.settings;

        playButton.setVisible(true);
        pauseButton.setVisible(false);
        loopStop.setVisible(false);

        //erster song in der playlist wird ausgegeben
        title.setText(player.getCurrentTrack().getTitle());
        artist.setText(player.getCurrentTrack().getArtist());
        time.setText(player.getCurrentTrack().getLength() / 60000 + ":" + player.getCurrentTrack().getLength() % 60000 / 1000);
        volumeSlider.setValue(volume);
        cover.setImage(new Image(new ByteArrayInputStream(player.getCurrentTrack().getCover())));

        root = playerView;
        initialize();
    }

    public void initialize() {

        settings.addEventHandler(ActionEvent.ACTION,
                event ->  application.switchView(Main.Scenes.SettingsScene)
        );

        goToPlaylistView.addEventHandler(ActionEvent.ACTION,
                event ->  application.switchView(Main.Scenes.PlaylistScene)
                );

        volumeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            try {
                player.volume(volumeSlider.getValue());
                volume = volumeSlider.getValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        playButton.addEventHandler(ActionEvent.ACTION,
                event -> {
                    try {
                        player.play();
                        volumeSlider.setValue(volume);
                        playButton.setVisible(false);
                        pauseButton.setVisible(true);
                    } catch (NullPointerException e) {
                        System.out.println("No playlist has been selected yet!\n");
                    }
                });

        pauseButton.addEventHandler(ActionEvent.ACTION,
                event -> {
                    player.pause();
                    playButton.setVisible(true);
                    pauseButton.setVisible(false);
                });

        skipRight.addEventHandler(ActionEvent.ACTION,
                event -> {
                    player.skipRight(volume);
                    volumeSlider.setValue(volume);
                });

        skipLeft.addEventHandler(ActionEvent.ACTION,
                event -> {
                    player.skipLeft(volume);
                    volumeSlider.setValue(volume);
                });

        shuffle.addEventHandler(ActionEvent.ACTION,
                event -> player.shuffle());

        loop.addEventHandler(ActionEvent.ACTION,
                event -> {
                    if(player.getAudioPlayer().isPlaying()) {
                        player.loop();
                        loop.setVisible(false);
                        loopStop.setVisible(true);
                    } else {
                        player.loop();
                        loop.setVisible(false);
                        loopStop.setVisible(true);
                        playButton.setVisible(false);
                        pauseButton.setVisible(true);
                    }

                });

        loopStop.addEventHandler(ActionEvent.ACTION,
                event -> {
                        player.loop();
                        loopStop.setVisible(false);
                        loop.setVisible(true);
                });

        player.currentTrackProperty().addListener((observableValue, oldTrack, newTrack)
                -> {
                    Platform.runLater(
                        () -> {
                            title.setText(newTrack.getTitle());
                            artist.setText(newTrack.getArtist());
                            time.setText(newTrack.getLength() / 60000 + ":" + newTrack.getLength() % 60000 / 1000);
                            cover.setImage(new Image(new ByteArrayInputStream(newTrack.getCover())));
                        }
                    );
                }
        );

        player.positionProperty().addListener((observableValue, oldPosition, newPosition)
                -> {
                    Platform.runLater(
                        () -> {
                            slider.setValue(100 / (float)(player.getCurrentTrack().getLength()) * player.getAudioPlayer().position());
                            timePlayed.setText(newPosition.intValue() / 60000 + ":" + newPosition.intValue() % 60000 / 1000);
                        }
                    );
                }
        );
    }

    public Pane getRoot() {
        return root;
    }
}
