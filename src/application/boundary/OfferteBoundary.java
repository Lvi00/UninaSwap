package application.boundary;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.Studente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OfferteBoundary {

    private Controller controller;

    @FXML private HBox containerOfferte;
    @FXML private AnchorPane OffertePane;
    @FXML private Label usernameDashboard;
    @FXML private ImageView immagineNav;
    @FXML private HBox containerModificaOfferta;
    @FXML private Label labelModificaOfferta;
    @FXML private Label labelOffertePubblicate;
    @FXML private GridPane gridOfferte;
    
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setUsername(String s) {
        usernameDashboard.setText(s);
    }
    
    public void setImmagine(String immagineP) {
        try {
            File file = new File(immagineP);
            Image image;
            if (file.exists()) {
                // Se esiste come file nel file system, caricalo da file
                image = new Image(file.toURI().toString());
            } else {
                // Altrimenti prova a caricare da risorsa classpath
                image = new Image(getClass().getResource(immagineP).toExternalForm());
            }
            
            immagineNav.setImage(image);
            Circle clip = new Circle(16.5, 16.5, 16.5);
            immagineNav.setClip(clip);
            immagineNav.setImage(image);
            immagineNav.setFitWidth(33);
            immagineNav.setFitHeight(33);  
            immagineNav.setPreserveRatio(false);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore caricando immagine: " + immagineP);
        }
    }
    
    
    public void SelezionaPagina(MouseEvent e) {
        
    	//Prende l'oggetto cliccato
        Object source = e.getSource();
        
        //Controlla se è una Label
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
            
            System.out.println("Hai cliccato su: " + testo);
       
            switch (testo) {
	            case "Crea annuncio":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
		    	        Parent root = loader.load();
		                CreaAnnuncioBoundary creaCtrl = loader.getController();
		                creaCtrl.setController(this.controller);
		                creaCtrl.setUsername(this.controller.getStudente().getUsername());
		                creaCtrl.setCampiForm();
		                creaCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                creaCtrl.MostraPaneVendita(e);
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - Crea annuncio");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;
	            
	            case "Offerte":
		            System.out.println("Sei già nella pagina Offerte.");
	            break;
	            
	            case "Informazioni":
	            	try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Informazioni.fxml"));
		    	        Parent root = loader.load();
		                InformazioniBoundary infoCtrl = loader.getController();
		                infoCtrl.setController(this.controller);
		                infoCtrl.setUsername(this.controller.getStudente().getUsername());
		                infoCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - Crea annuncio");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;
	            
	            case "Prodotti":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                        Parent root = loader.load();
                        ProdottiBoundary prodottiCtrl = loader.getController();
                        prodottiCtrl.setController(this.controller);
                        prodottiCtrl.setUsername(this.controller.getStudente().getUsername());
                        prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
                        prodottiCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
                        prodottiCtrl.setFiltri();
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
		                annunciCtrl.setController(this.controller);
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
	            	//Profilo utente
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profilo.fxml"));
		    	        Parent root = loader.load();
		                ProfiloBoundary ProfiloCtrl = loader.getController();
		                ProfiloCtrl.setController(this.controller);
		                ProfiloCtrl.setUsername(this.controller.getStudente().getUsername());
		                ProfiloCtrl.setNome(this.controller.getStudente().getNome());
		                ProfiloCtrl.setCognome(this.controller.getStudente().getCognome());
		                ProfiloCtrl.setMatricola(this.controller.getStudente().getMatricola());
		                ProfiloCtrl.setEmail(this.controller.getStudente().getEmail());
		                ProfiloCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - Profilo");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
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
    
    public boolean CostruisciOfferteUtente(Studente s) {
        ArrayList<Offerta> offerteInviate;
        
        if(controller.getStudente().getOfferteInviate().isEmpty()) {
        	offerteInviate = controller.getOffertebyMatricola(s);
        	controller.getStudente().setOfferteInviate(offerteInviate);
        }
        else{
        	System.out.println("offerte inviate già caricate");
        	offerteInviate = controller.getStudente().getOfferteInviate();
        }
        
        String titolo = "";
        
        if(offerteInviate.size() == 0) titolo = "Non ci sono offerte attive di ";
        else titolo = "Offerte attive di ";
        
        labelOffertePubblicate.setText(titolo + s.getUsername());
        
        int column = 0;
        int row = 0;

        for (Offerta o : offerteInviate) {
            VBox card = creaCardOfferte(o);
            gridOfferte.add(card, column, row);
            column++;

            if (column == 3) {
                column = 0;
                row++;
            }
        }

        return true;
    }

    private VBox creaCardOfferte(Offerta o) { 
        VBox box = new VBox();
        box.setPrefWidth(250);
        box.setPrefHeight(150);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");
        
        Label labelAnnuncio = new Label("Offerta per l'annuncio:");
        labelAnnuncio.setStyle("-fx-font-size: 14px;");
        
        Label titoloAnnuncio = new Label(o.getAnnuncio().getTitoloAnnuncio());
        titoloAnnuncio.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titoloAnnuncio.setWrapText(true);
        
        Label stato = new Label(o.getStatoOfferta());
        
        switch(o.getStatoOfferta()) {
            case "Attesa":
                stato.getStyleClass().add("label-attesa");
            break;
            
            case "Accettata":
                stato.getStyleClass().add("label-attivo");
            break;

            case "Rifiutata":
                stato.getStyleClass().add("label-non-attivo");
            break;
        }
        VBox.setMargin(stato, new Insets(4, 0, 4, 0));

        Label tipo = new Label(o.getTipologia());
        tipo.setStyle("-fx-text-fill: gray;");

        ImageView iconDelete = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/delete_card.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconDelete.setImage(imgIcon);
            iconDelete.setFitWidth(22);
            iconDelete.setFitHeight(22);
            iconDelete.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Button buttonDelete = new Button();
        buttonDelete.setGraphic(iconDelete);
        buttonDelete.getStyleClass().add("tasto-terziario");
        buttonDelete.setOnMouseClicked(event -> eliminaOfferta(o));
        
        ImageView iconInfo = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/info.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconInfo.setImage(imgIcon);
            iconInfo.setFitWidth(22);
            iconInfo.setFitHeight(22);
            iconInfo.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Button buttonInfo = new Button();
        buttonInfo.setGraphic(iconInfo);
        buttonInfo.getStyleClass().add("tasto-secondario");
        buttonInfo.setOnMouseClicked(event -> mostraInfoOfferta(o));
        
        ImageView iconEdit = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/edit.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconEdit.setImage(imgIcon);
            iconEdit.setFitWidth(22);
            iconEdit.setFitHeight(22);
            iconEdit.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Button buttonEdit = new Button();
        buttonEdit.setGraphic(iconEdit);
        buttonEdit.getStyleClass().add("tasto-edit");
        buttonEdit.setOnMouseClicked(event -> editOfferta(o));

        HBox containerButton = new HBox(20);
        containerButton.setAlignment(Pos.CENTER);
        VBox.setMargin(containerButton, new Insets(5, 0, 0, 0));
        
        containerButton.getChildren().addAll(buttonInfo, buttonEdit, buttonDelete);
        
        VBox boxStato = new VBox();
        boxStato.setAlignment(Pos.CENTER);
        boxStato.setSpacing(4);
        boxStato.getChildren().addAll(stato);
        
        Timestamp data = o.getDataPubblicazione();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = data.toLocalDateTime().format(formatter);

        Label dataPubblicazione = new Label("Inviata il: " + formattedDate);

        box.getChildren().addAll(labelAnnuncio, titoloAnnuncio, boxStato, tipo, dataPubblicazione, containerButton);

        return box;
    }
    
    public void eliminaOfferta(Offerta o) {
    	switch(controller.eliminaOfferta(o)){
    		case 0:
    			ShowPopupAlert("Eliminazione offerta", "Offerta eliminata correttamente.");
				gridOfferte.getChildren().clear();
				controller.SvuotaOfferteInviate();
				CostruisciOfferteUtente(this.controller.getStudente());
			break;
			
    		case 1:
    			System.out.println("Errore durante l'eliminazione dell'offerta.");
			break;
    	}
    }
    
    public void editOfferta(Offerta o) {
    	labelModificaOfferta.setText("Modifica offerta per l'annuncio: " + o.getAnnuncio().getTitoloAnnuncio());
    	OffertePane.setVisible(false);
    	containerModificaOfferta.setVisible(true);
    	//aggiungere lo svuotamento della lista delle offerte inviate
    }
    
    public void mostraInfoOfferta(Offerta o) {
    	System.out.println("Mostra info offerta");
    }
    
    private void ShowPopupAlert(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupAlert.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) containerOfferte.getScene().getWindow();
	        Stage stage = new Stage();
	        stage.initOwner(mainStage);
	        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("UninaSwap - " + title);
	        stage.setResizable(false);
	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));

	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	       
	        mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
	        stage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

	        stage.show();
	        
	        stage.setX(mainStage.getX() + (mainStage.getWidth() - stage.getWidth()) / 2);
	        stage.setY(mainStage.getY() + (mainStage.getHeight() - stage.getHeight()) / 2 - 50);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}