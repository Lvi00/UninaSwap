package application.boundary;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneManager {
	private Controller controller = Controller.getController();
	
	@FXML
	public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();

        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
            
            System.out.println("Navigazione a: " + testo);

            switch (testo) {
                case "Prodotti":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                        Parent root = loader.load();
                        ProdottiBoundary prodottiCtrl = loader.getController();
                        prodottiCtrl.setController(this.controller);
                        prodottiCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
                        prodottiCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        prodottiCtrl.setFiltri();
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Prodotti");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                    
                case "Offerte":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offerte.fxml"));
		    	        Parent root = loader.load();
		                OfferteBoundary offerteCtrl = loader.getController();
		                offerteCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
		                offerteCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
		                offerteCtrl.CostruisciOfferteUtente(this.controller.getStudente());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - Offerte");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;
	            
                case "Informazioni":
                	try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Informazioni.fxml"));
                        Parent root = loader.load();
                        InformazioniBoundary infoCtrl = loader.getController();
                        infoCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        infoCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Informazioni");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
	            break;

                case "Crea annuncio":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
                        Parent root = loader.load();
                        CreaAnnuncioBoundary creaCtrl = loader.getController();
                        creaCtrl.setController(this.controller);
                        creaCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        creaCtrl.setCampiForm();
                        creaCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
		                creaCtrl.MostraPaneVendita(e);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Crea annuncio");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                    
	            case "I tuoi annunci":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnnunciStudente.fxml"));
		    	        Parent root = loader.load();
		                AnnunciStudenteBoundary annunciCtrl = loader.getController();
		                annunciCtrl.CostruisciProdottiUtente(this.controller.getStudente());
		                annunciCtrl.setUsername(this.controller.getStudente().getUsername());
		                annunciCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - I tuoi annunci");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;

	            default:
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profilo.fxml"));
                        Parent root = loader.load();
                        ProfiloBoundary ProfiloCtrl = loader.getController();
                        ProfiloCtrl.setController(this.controller);
                        ProfiloCtrl.setUsername(this.controller.getStudente().getUsername());
                        ProfiloCtrl.setNome(controller.getNome(this.controller.getStudente()));
                        ProfiloCtrl.setCognome(controller.getCognome(this.controller.getStudente()));
                        ProfiloCtrl.setMatricola(controller.getMatricola(this.controller.getStudente()));
                        ProfiloCtrl.setEmail(controller.getEmail(this.controller.getStudente()));
                        ProfiloCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Profilo");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                break;
            }
        }
    }
}
