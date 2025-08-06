package application.boundary;

import application.control.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreaAnnuncioBoundary {

	enum Categorie {
	    Abbigliamento,
	    Informatica,
	    Elettronica,
	    Cancelleria,
	    Cultura,
	    Musica
	}
	  
	private Controller controller;

    @FXML private Label usernameDashboard;
    @FXML private ChoiceBox<Categorie> campoSelezioneCategoria;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setLabel(String s) {
        usernameDashboard.setText(s);
    }
    
    @FXML
	public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
       
            switch (testo) {
	            case "Prodotti":
	            	try{
	            		FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
		            	Parent root = loader.load();
		                ProdottiBoundary prodottiCtrl = loader.getController();
		                prodottiCtrl.setController(this.controller);
		                prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
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
	            
	            default:
	            	System.out.println("Selezione non valida: " + testo);
	            break;
            }
        }
    }

    @FXML
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
		catch (Exception ex) {
		    ex.printStackTrace();
		}
    }
	
    @FXML
    public void showProfile(MouseEvent e) {
    	System.out.println("Profilo utente");
    }
    
    @FXML
    public void setCampiForm() {
		campoSelezioneCategoria.setItems(FXCollections.observableArrayList(Categorie.values()));
		campoSelezioneCategoria.getSelectionModel().selectFirst();
    }
}
