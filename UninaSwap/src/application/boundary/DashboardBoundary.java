package application.boundary;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class DashboardBoundary {
	
	private Controller controller = new Controller();
	private String username;
	
	//Componenti finestra
	@FXML private AnchorPane anchorPaneDashboard;
    @FXML private HBox containerDashboard;
    @FXML private Label labelUtente;

    public void creaDashboard(double width, double height, String username) {
    	this.username = username;
    	System.out.println("Il nome utente è " + this.username);
    	labelUtente.setText("Ciao, " + username + "!");
        setContainerSize(width, height);
        setAnchorPaneSize(width, height);
    }
    
    public void setContainerSize(double width, double height) {
    	containerDashboard.setPrefWidth(width);
    	containerDashboard.setPrefHeight(height);
    }

    public void setAnchorPaneSize(double width, double height) {
    	anchorPaneDashboard.setPrefWidth(width);
    	anchorPaneDashboard.setPrefHeight(height);
    }
}
