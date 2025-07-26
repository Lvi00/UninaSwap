package application.boundary;

import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardBoundary {
	
    private Studente studente;

    @FXML private Label UserNameDashboard;


    public void CostruisciDashboard(Studente s)
    {
    	this.studente = s;
        UserNameDashboard.setText(this.studente.getUsername());
    }
}
