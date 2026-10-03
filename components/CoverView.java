package components;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CoverView extends HBox {
     private ImageView view;
       
     public CoverView() {
    	 setCoverView(new ImageView());
    	 getCoverView().setFitHeight(335);
         getCoverView().setFitWidth(335);
         setAlignment(Pos.CENTER);
         getChildren().add(getCoverView()); 
         view.setPreserveRatio(true);
         
     }
     
     public ImageView getCoverView() {
		return view;
     }
     public void setCoverView(ImageView view) {
		this.view = view;
     }
}
