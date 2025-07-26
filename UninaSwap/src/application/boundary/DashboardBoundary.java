package application.boundary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardBoundary {
	
	private String username;
	
    @FXML private Label labelUtente;

    public void creaDashboard(double width, double height, String username) {
    	this.username = username;
    	System.out.println("Il nome utente è " + this.username);
    	labelUtente.setText("Ciao, " + username + "!");

    }
    
}
