package javafx;

import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayerView extends BorderPane {
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
    Slider volumeSlider;
    Slider slider;
    Button goToPlaylistView;
    Button settings;
    ImageView cover;
    ImageViewPane imgPane;

    public PlayerView() {
        VBox header = new VBox();
        header.getStyleClass().add("header");
        title = new Label("");
        title.getStyleClass().add("main-text");
        artist = new Label("");
        artist.getStyleClass().add("secondary-text");
        header.getChildren().addAll(title, artist);
        header.setAlignment(Pos.CENTER);
		header.setPadding(new Insets(10, 10, 10, 10));
		this.setTop(header);

        cover = new ImageView();

        imgPane = new ImageViewPane(cover);
	    this.setCenter(imgPane);

        VBox bottomPanel = new VBox();
        bottomPanel.setPadding(new Insets(10, 10, 10, 10));

        GridPane sliderPane = new GridPane();
        sliderPane.setAlignment(Pos.CENTER);
        time = new Label("0:00");
        time.getStyleClass().add("time");
        timePlayed = new Label("0:00");
        timePlayed.getStyleClass().add("time");
        slider = new Slider();
        slider.getStyleClass().add("slider");
        sliderPane.setPadding(new Insets(0, 0,10,0));

        GridPane.setHgrow(slider, Priority.ALWAYS);

        sliderPane.add(timePlayed,0,0);
        sliderPane.add(slider, 1,0);
        sliderPane.add(time,2,0);

        GridPane controller = new GridPane();

        playButton = new Button();
        playButton.getStyleClass().add("playButton");

        pauseButton = new Button("");
        pauseButton.getStyleClass().add("pauseButton");

        skipRight = new Button();
        skipRight.getStyleClass().add("skipRight");
        skipLeft = new Button();
        skipLeft.getStyleClass().add("skipLeft");

        shuffle = new Button();
        shuffle.getStyleClass().add("shuffle");

        loop = new Button();
        loop.getStyleClass().add("loop");

        loopStop = new Button();
        loopStop.getStyleClass().add("loop-activated");

        settings = new Button();
        settings.getStyleClass().add("settings");

        controller.add(shuffle, 1,0);
        controller.add(skipLeft,2,0);
        controller.add(playButton,3,0);
        controller.add(pauseButton, 3,0);
        controller.add(skipRight, 4,0);
        controller.add(loop, 5,0);
        controller.add(loopStop, 5,0);

        controller.setAlignment(Pos.CENTER);

        volumeSlider = new Slider();

        goToPlaylistView = new Button();
        goToPlaylistView.getStyleClass().add("playlistButton");

        goToPlaylistView.setAlignment(Pos.CENTER);

        HBox bottom = new HBox();

        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(settings,goToPlaylistView, controller, volumeSlider);

        bottomPanel.getStyleClass().add("bottomPanel");
        bottomPanel.getChildren().addAll(sliderPane, bottom);

        this.setBottom(bottomPanel);

        }
}
