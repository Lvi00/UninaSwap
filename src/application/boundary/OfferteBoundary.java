package application.boundary;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Offerta;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    @FXML private HBox containerOfferte;
    @FXML private AnchorPane OffertePane;
    @FXML private Label usernameDashboard;
    @FXML private ImageView immagineNav;
    @FXML private HBox containerModificaOfferta;
    @FXML private Label labelModificaOfferta;
    @FXML private Label labelOffertePubblicate;
    @FXML private GridPane gridOfferte;
    @FXML private TextArea campoMotivazione;
    @FXML private AnchorPane paneOffertaRegalo;
    @FXML private AnchorPane paneOffertaScambio;
    @FXML private AnchorPane paneOffertaVendita;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    
    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();

    public void costruisciPagina() {
		usernameDashboard.setText(controller.getUsername(controller.getStudente()));
		String immagineP = controller.getImmagineProfilo(controller.getStudente());
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
    	sceneManager.SelezionaPagina(e);
    }
    
    public boolean CostruisciOfferteUtente(Studente s) {
        ArrayList<Offerta> offerteInviate;
        
        if(controller.getStudente().getOfferteInviate().isEmpty()) {
        	offerteInviate = controller.getOffertebyMatricola(s);
        	controller.setOfferteInviate(offerteInviate);
        	controller.getStudente().setOfferteInviate(offerteInviate);
        }
        else{
        	System.out.println("offerte inviate già caricate");
        	offerteInviate = controller.getStudente().getOfferteInviate();
        }
        
        String titolo = "";
        
        if(offerteInviate.size() == 0) titolo = "Non ci sono offerte inviate da ";
        else titolo = "Offerte inviate da ";
        
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

    private VBox creaCardOfferte(Offerta offerta) { 
        VBox box = new VBox();
        box.setPrefWidth(250);
        box.setPrefHeight(150);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");
        
        Label labelAnnuncio = new Label("Offerta per l'annuncio:");
        labelAnnuncio.setStyle("-fx-font-size: 14px;");
        
        Label titoloAnnuncio = new Label(controller.getTitoloAnnuncio(controller.getAnnuncioOfferta(offerta)));
        titoloAnnuncio.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        titoloAnnuncio.setWrapText(true);
        
        Label stato = new Label(controller.getStatoOfferta(offerta));
        
        switch(controller.getStatoOfferta(offerta)) {
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

        Label tipo = new Label(controller.getTipologiaOfferta(offerta));
        tipo.setStyle("-fx-text-fill: gray;");
        
        HBox containerButton = new HBox(20);
        containerButton.setAlignment(Pos.CENTER);
        VBox.setMargin(containerButton, new Insets(5, 0, 0, 0));
        
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

        if(!controller.getStatoOfferta(offerta).equals("Accettata")) {
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
	        buttonDelete.setOnMouseClicked(event -> eliminaOfferta(offerta));
	        
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
	        buttonEdit.setOnMouseClicked(event -> editOfferta(event, offerta));
	        
	        containerButton.getChildren().addAll(buttonEdit, buttonDelete);
        }
                
        VBox boxStato = new VBox();
        boxStato.setAlignment(Pos.CENTER);
        boxStato.setSpacing(4);
        boxStato.getChildren().addAll(stato);
        
        Timestamp data = controller.getDataPubblicazioneOfferta(offerta);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = data.toLocalDateTime().format(formatter);

        Label dataPubblicazione = new Label("Inviata il: " + formattedDate);

        box.getChildren().addAll(labelAnnuncio, titoloAnnuncio, boxStato, tipo, dataPubblicazione, containerButton);

        return box;
    }
    
    public void eliminaOfferta(Offerta offerta) {
    	switch(controller.eliminaOfferta(offerta)){
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
    
    public void editOfferta(MouseEvent e, Offerta offerta) {
		try{
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupEditOfferta.fxml"));
		   Parent root = loader.load();
		   PopupEditOffertaBoundary popupEditController = loader.getController();
		   Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		   Stage popupStage = new Stage();
		   popupStage.initOwner(mainStage);
		   popupStage.initModality(Modality.WINDOW_MODAL);
		   Scene scene = new Scene(root);
		   scene.getStylesheets().add(
		       getClass().getResource("../resources/application.css").toExternalForm()
		   );
		   popupStage.setScene(scene);
		   popupStage.setTitle("UninaSwap - Modifica offerta");
		   popupStage.getIcons().add(
		       new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
		   );
		   popupStage.setResizable(false);
		   mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
		   popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));    	       
		   popupEditController.CostruisciPopupEdit(offerta, popupStage);
		   popupStage.show();
		   popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
		   popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);
		}
		catch (Exception ex) {
		    ex.printStackTrace();
		}
    }
    
    @FXML
    public void tornaIndietroOfferte() {
    	OffertePane.setVisible(true);
    	containerModificaOfferta.setVisible(false);
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