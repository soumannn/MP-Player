package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TitleView extends VBox {
	 private Label title;
	 private Label artistName;

     public TitleView() {
    	 title = new Label();
    	 artistName = new Label();
    	 setAlignment(Pos.CENTER);
    	 setSpacing(6);
         getChildren().addAll(title, artistName);    
     }
     
     public Label getTitle() {
		return title;
     }
     public Label getArtistName() {
 		return artistName;
      }
     public void setTitle(Label title) {
		this.title = title;
     }
}
