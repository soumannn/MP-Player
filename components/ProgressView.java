package components;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class ProgressView extends HBox {
	Slider timeSlider;
	Label currentTime;
	Label totalTime;
	
	public ProgressView() {
		timeSlider = new Slider();
		timeSlider.setPrefSize(270, 10);
		currentTime = new Label("00:00");
		totalTime = new Label("00:01");
        setSpacing(4);
		setAlignment(Pos.CENTER);
		getChildren().addAll(currentTime, timeSlider, totalTime);
	}
	public Label getCurrentTime() {
		return currentTime;
	}
	public 	Slider getTimeSlider() {
		return timeSlider;
	}
	public Label getTotalTime() {
		return totalTime;
	}
	
}
