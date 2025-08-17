package application.boundary;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ProfiloBoundary {
	
	private Controller controller;

    @FXML private Label usernameDashboard;
    @FXML private Label usernameProfilo;
    @FXML private Label nomeProfilo;
    @FXML private Label cognomeProfilo;
    @FXML private Label matricolaProfilo;
    @FXML private Label emailProfilo;
    @FXML private ImageView immagineProfilo;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setUsername(String s) {
        usernameDashboard.setText(s);
        usernameProfilo.setText(s);
    }
    
    public void setNome(String nome) {
    	nomeProfilo.setText(nome); 
    }

    public void setCognome(String cognome) {
    	cognomeProfilo.setText(cognome); 
    }

    public void setMatricola(String matricola) {
    	matricolaProfilo.setText(matricola);
    }
    
    public void setEmail(String email) {
    	emailProfilo.setText(email); 
    }
    
    public void setImmagine(String immagineP) {
        Image image = new Image(getClass().getResource(immagineP).toExternalForm());
        immagineProfilo.setImage(image);  
        
        Circle clip = new Circle(75, 75, 75);  
        immagineProfilo.setClip(clip);
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
                        prodottiCtrl.setUsername(this.controller.getStudente().getUsername());
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("UninaSwap - Prodotti");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                break;
                
                case "Crea annuncio":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
		    	        Parent root = loader.load();
		                CreaAnnuncioBoundary creaCtrl = loader.getController();
		                creaCtrl.setController(this.controller);
		                creaCtrl.setUsername(this.controller.getStudente().getUsername());
		                creaCtrl.setCampiForm();
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.centerOnScreen();
		    	        stage.setTitle("UninaSwap - Crea annuncio");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
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
            stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
