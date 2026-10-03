package components;

import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {
	
     public Button backButton;
     public Button playButton;
     public Button skipButton;
     public Button shuffleButton;
     public Slider volumeSlider;
    
     public ControlView() {
    	 /*backButton = new Button("Back");
         playButton = new Button("Play");
         skipButton = new Button("Skip");
         shuffleButton = new Button("Shuffle");*/
    	 
         //Wegen Icons Beschriftung entfernt
         backButton = new Button();
         playButton = new Button();
         skipButton = new Button();
         shuffleButton = new Button();
         volumeSlider = new Slider(-50, 50, 0); //-50 to 50 range, initial value = 0
         volumeSlider.setPrefWidth(80);
 		
         //IDs Für CSS
         backButton.getStyleClass().add("icon-button");
         playButton.getStyleClass().add("icon-button");
         skipButton.getStyleClass().add("icon-button");
         shuffleButton.getStyleClass().add("icon-button");
         

         backButton.setId("backButton");
         playButton.setId("playButton");
 		 skipButton.setId("skipButton");
 		 shuffleButton.setId("shuffleButton");
 		 setId("controlView");
 		         
         setAlignment(Pos.CENTER);
         setSpacing(8);
         setPadding(new Insets (10));
         getChildren().addAll(shuffleButton, backButton, playButton, skipButton, volumeSlider);
         
     }
 }