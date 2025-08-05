package application.boundary;

import application.control.Controller;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreaAnnuncioBoundary {

	private Controller controller;

    @FXML private Label usernameDashboard;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setLabel(String s) {
        usernameDashboard.setText(s);
    }
    
	public void SelezionaPagina(MouseEvent e) {
	        
    	//Prende l'oggetto cliccato
        Object source = e.getSource();
        
        //Controlla se è una Label
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
       
            switch (testo) {
            case "Prodotti":
            	try{
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
	            	Parent root = loader.load();
	            	
	                ProdottiBoundary prodottiCtrl = loader.getController();
	                // PASSO lo stesso controller (contiene lo studente)
	                prodottiCtrl.setController(this.controller);
	                // ora costruisco il catalogo
	                prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
	                //Mette il nome in alto a destra
	                prodottiCtrl.setLabel(this.controller.getStudente().getUsername());
	                
	                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			        Scene scene = new Scene(root);
			        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
			        stage.setScene(scene);
			        stage.centerOnScreen();
			        stage.setTitle("UninaSwap - Prodotti");
			        stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
			        stage.setResizable(false);
			        stage.show();
            	}
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            	
	            break;
            }
        }
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
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    public void showProfile(MouseEvent e) {
	    	System.out.println("Profilo utente");
	    }
}
