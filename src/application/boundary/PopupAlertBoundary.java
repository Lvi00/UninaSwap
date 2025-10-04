package application.boundary;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PopupAlertBoundary {
	private Controller controller = Controller.getController();
    @FXML private Label LabelAlert;
    @FXML private Label LabelDescrizioneAlert;

    public void setLabels(String title, String message) {
    	LabelAlert.setText(title);
    	LabelDescrizioneAlert.setText(message);
    }
}