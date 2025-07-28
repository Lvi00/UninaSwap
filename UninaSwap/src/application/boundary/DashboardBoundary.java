package application.boundary;

import javafx.scene.input.MouseEvent;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DashboardBoundary {
	
    private Studente studente;

    @FXML private Label usernameDashboard;

    public void CostruisciDashboard(Studente s){
    	this.studente = s;
    	usernameDashboard.setText("Ciao, " + this.studente.getUsername() + "!");
    }
    
    public void logout(MouseEvent e) {    	
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("UninaSwap - Login");
            stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            stage.show();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			this.studente = null;
		}
    }
    
    public void selezioneSezione(MouseEvent e) {
    	Node source = (Node) e.getSource();
    	String selezione = ((Labeled) source).getText();
    	
    	switch (selezione) {
			case "Catalogo":
				System.out.println("Hai selezionato: Catalogo");
			break;
			
			case "Le tue vendite":
				System.out.println("Hai selezionato: Le tue vendite");
			break;
			
			case "I tuoi acquisti":
				System.out.println("Hai selezionato: I tuoi acquisti");
			break;
			
			case "I tuoi scambi":
				System.out.println("Hai selezionato: I tuoi scambi");
			break;

			case "Statistiche":
				System.out.println("Hai selezionato: Statistiche");
			break;
			
			default:
				System.out.println("Si è verificato un errore nella selezione della sezione.");
			break;
		}
    }
}
