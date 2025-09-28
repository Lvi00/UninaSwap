package application.boundary;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PopupErrorBoundary {
	private Controller controller = Controller.getController();
    @FXML private Label LabelErrore;
    @FXML private Label LabelDescrizioneErrore;

    public void setLabels(String title, String message) {
        LabelErrore.setText(title);
        LabelDescrizioneErrore.setText(message);
    }
}