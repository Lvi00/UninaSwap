package application.boundary;

import java.io.File;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class AnnunciStudenteBoundary {

    @FXML private HBox containerAnnunciStudente;
    @FXML private Label usernameDashboard;
    @FXML private GridPane gridProdotti;
    @FXML private GridPane gridOfferte;
    @FXML private TextField searchField;
    @FXML private ImageView immagineNav;
    @FXML private Label labelAnnunciPubblicati;
    @FXML private AnchorPane AnnunciPane;
    @FXML private AnchorPane OfferteAnnunciPane;
    @FXML private Label labelOfferteAnnuncio;
    
    private SceneManager sceneManager = SceneManager.sceneManager();
	private Controller controller = Controller.getController();

	public void costruisciPagina() {
		usernameDashboard.setText(controller.getUsername(controller.getStudente()));
		String immagineP = controller.getImmagineProfilo(controller.getStudente());
		try {
            File file = new File(immagineP);
            Image image;
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
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
    
	@FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        Label label = (Label) source;
        String nomePagina = label.getText();
    	sceneManager.SelezionaPagina(nomePagina, e);
    }

    public boolean CostruisciProdottiUtente() {
    	ObservableList<Annuncio> annunci;
        
        if (controller.getStudente().getAnnunciPubblicati().isEmpty()) {
            annunci = FXCollections.observableArrayList(controller.getAnnunciStudente(controller.getStudente()));
            controller.setAnnunciPubblicati(new ArrayList<>(annunci));
        } else {
            annunci = FXCollections.observableArrayList(controller.getStudente().getAnnunciPubblicati());
        }
        
        String titolo = "";
        
        if(annunci.size() == 0) titolo = "Non ci sono annunci attivi di ";
        else titolo = "Annunci attivi di ";
        
        labelAnnunciPubblicati.setText(titolo + controller.getUsername(controller.getStudente()));
        
        int column = 0;
        int row = 0;

        for (Annuncio a : annunci) {
            VBox card = creaCardAnnuncio(a);
            gridProdotti.add(card, column, row);
            column++;

            if (column == 3) {
                column = 0;
                row++;
            }
        }

        return true;
    }

    private VBox creaCardAnnuncio(Annuncio a) { 
        VBox box = new VBox();
        box.setPrefWidth(230);
        box.setPrefHeight(300);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");

        AnchorPane imagePane = new AnchorPane();
        imagePane.setPrefWidth(230);
        imagePane.setPrefHeight(150);

        ImageView imageView = new ImageView();
        try {
            String path = controller.getImmagineOggetto(controller.getOggetto(a));
            Image img;
            File file = new File(path);

            if (file.exists()) {
                img = new Image(file.toURI().toString());
            } else {
                img = new Image(getClass().getResource(path).toExternalForm());
            }

            imageView.setFitWidth(230);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setImage(img);

            imagePane.getChildren().add(imageView);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + controller.getImmagineOggetto(controller.getOggetto(a)));
        }

        ImageView iconOffers = new ImageView();
        try {
            String offersPath = "../IMG/immaginiProgramma/offers.png";
            Image imgOffers = new Image(getClass().getResource(offersPath).toExternalForm());
            iconOffers.setImage(imgOffers);
            iconOffers.setFitWidth(22);
            iconOffers.setFitHeight(22);
            iconOffers.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Label titolo = new Label(controller.getTitoloAnnuncio(a));
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titolo.setWrapText(true);
        titolo.setMaxWidth(200);
        titolo.setAlignment(Pos.CENTER);

        Label stato = new Label(controller.getStatoAnnuncio(a) ? "Attivo" : "Non attivo");
        stato.getStyleClass().add(a.isStatoAnnuncio() ? "label-attivo" : "label-non-attivo");
        VBox.setMargin(stato, new Insets(4, 0, 4, 0));

        Label prezzo = new Label(String.format("\u20AC %.2f", controller.getPrezzoAnnuncio(a)));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 12;");

        Label tipo = new Label(controller.getCategoriaOggetto(controller.getOggettoAnnuncio(a)) + " - " + controller.getTipologiaAnnuncio(a));
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
        
        Timestamp data = controller.getDataPubblicazioneAnnuncio(a);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = data.toLocalDateTime().format(formatter);

        Label dataPubblicazione = new Label("Pubblicato il: " + formattedDate);
        
        HBox containerButton = new HBox(20);
        containerButton.setAlignment(Pos.CENTER);
        VBox.setMargin(containerButton, new Insets(5, 0, 0, 0));

        Button buttonDelete = new Button();
        buttonDelete.getStyleClass().add("tasto-terziario");
        buttonDelete.setPrefSize(24, 24);
        buttonDelete.setStyle("-fx-cursor: hand;");
        buttonDelete.setGraphic(iconDelete);
        buttonDelete.setOnMouseClicked(event -> rimuoviAnnuncio(a));

        Button buttonOffers = new Button();
        buttonOffers.getStyleClass().add("tasto-secondario");
        buttonOffers.setPrefSize(24, 24);
        buttonOffers.setStyle("-fx-cursor: hand;");
        buttonOffers.setGraphic(iconOffers);
        buttonOffers.setOnMouseClicked(event -> {
            mostraOfferteAnnuncio(a);
        });

        containerButton.getChildren().addAll(buttonDelete, buttonOffers);
        
        VBox boxPrezzo = new VBox();
        boxPrezzo.setAlignment(Pos.CENTER);
        boxPrezzo.setSpacing(4);
        boxPrezzo.getChildren().addAll(stato, prezzo);

        box.getChildren().addAll(imagePane, titolo, boxPrezzo, tipo, dataPubblicazione, containerButton);

        return box;
    }

    public void mostraOfferteAnnuncio(Annuncio annuncio) {
        AnnunciPane.setVisible(false);
        OfferteAnnunciPane.setVisible(true);
        gridOfferte.getChildren().clear();

        ObservableList<Offerta> offerteRicevute = FXCollections.observableArrayList(controller.getOffertebyAnnuncio(annuncio));

        if (offerteRicevute == null || offerteRicevute.isEmpty()) {
            labelOfferteAnnuncio.setText("Non ci sono offerte per l'annuncio: " + annuncio.getTitoloAnnuncio());
        } else {
            labelOfferteAnnuncio.setText("Queste sono le offerte dell'annuncio: " + annuncio.getTitoloAnnuncio());
        }

        int column = 0;
        int row = 0;
        boolean abilitaTasti = true;

        for (Offerta o : offerteRicevute) {
            if (controller.getStatoOfferta(o).equals("Accettata")) abilitaTasti = false;
        }

        for (Offerta o : offerteRicevute) {
            HBox rigaOfferta = creaRigaOfferta(o, abilitaTasti);
            gridOfferte.add(rigaOfferta, column, row);
            row++;
        }
    }
    
    private HBox creaRigaOfferta(Offerta o, boolean abilitaTasti) {
        HBox riga = new HBox();
        riga.setPrefWidth(800);
        riga.setSpacing(70);
        riga.setAlignment(Pos.CENTER_LEFT);
        riga.getStyleClass().add("card-annuncio");

        Label statoOfferta = new Label(controller.getStatoOfferta(o));
        statoOfferta.setPrefWidth(90);
        statoOfferta.setAlignment(Pos.CENTER);

        if (controller.getStatoOfferta(o).equals("Attesa")) {
            statoOfferta.getStyleClass().add("label-attesa");
        } else if (controller.getStatoOfferta(o).equals("Rifiutata")) {
            statoOfferta.getStyleClass().add("label-non-attivo");
        } else {
            statoOfferta.getStyleClass().add("label-attivo");
        }

        HBox dataPubblicazioneBox = new HBox();
        
        Timestamp data = controller.getDataPubblicazioneOfferta(o);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = data.toLocalDateTime().format(formatter);
        
        Label dataPubblicazione = new Label(formattedDate);
        dataPubblicazione.setStyle("-fx-text-fill: gray; -fx-font-size: 12;");
        
        dataPubblicazioneBox.getChildren().add(dataPubblicazione);
        dataPubblicazioneBox.setAlignment(Pos.CENTER);
        dataPubblicazioneBox.setPrefWidth(150);

        HBox infoBox = new HBox(10);
        infoBox.setPrefWidth(200);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        ImageView iconInfo = new ImageView();
        try {        	
            File file = new File(controller.getImmagineProfilo(controller.getStudenteOfferta(o)));
            Image img;
            
            if(file.exists()) {
            	img = new Image(file.toURI().toString());
            }
            
            else {
            	img = new Image(getClass().getResource(controller.getImmagineProfilo(controller.getStudenteOfferta(o))).toExternalForm());
            }

            iconInfo.setImage(img);
            iconInfo.setFitWidth(33);
            iconInfo.setFitHeight(33);
            iconInfo.setPreserveRatio(false);
            iconInfo.setClip(new Circle(16.5, 16.5, 16.5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Label autoreOfferta = new Label(controller.getUsername(controller.getStudenteOfferta(o)));
        autoreOfferta.setStyle("-fx-font-weight: bold;");
        infoBox.getChildren().addAll(iconInfo, autoreOfferta);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox containerButtons = new HBox(15);
        containerButtons.setAlignment(Pos.CENTER);
       
        Button btnInfo = creaIconButton("../IMG/immaginiProgramma/info.png", "tasto-secondario");
        btnInfo.setOnMouseClicked(e -> showInfoOfferta(e, o));
        
        if(o.getStatoOfferta().equals("Attesa") && abilitaTasti) {
	        Button btnAccetta = creaIconButton("../IMG/immaginiProgramma/check.png", "tasto-conferma");
	        btnAccetta.setOnMouseClicked(e -> accettaOfferta(o));
	
	        Button btnRifiuta = creaIconButton("../IMG/immaginiProgramma/delete_card.png", "tasto-terziario");
	        btnRifiuta.setOnMouseClicked(e -> rifiutaOfferta(o));
	        
	        containerButtons.getChildren().addAll(btnInfo, btnAccetta, btnRifiuta);
        }
        
        else {
        	containerButtons.getChildren().add(btnInfo);
        }
        
        riga.getChildren().addAll(statoOfferta, dataPubblicazioneBox, infoBox, spacer, containerButtons);
        
        return riga;
    }

    private Button creaIconButton(String path, String styleClass) {
        Button button = new Button();
        button.getStyleClass().add(styleClass);
        button.setStyle("-fx-cursor: hand;");

        try {
            Image img = new Image(getClass().getResource(path).toExternalForm());
            ImageView icon = new ImageView(img);
            icon.setFitWidth(22);
            icon.setFitHeight(22);
            icon.setPreserveRatio(true);
            button.setGraphic(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }
    
    public void accettaOfferta(Offerta offerta) {
		switch(controller.accettaOfferta(offerta)) {
			case 0:
				gridOfferte.getChildren().clear();
				tornaIndietroAnnunci();
			break;
			
			case 1:
				System.out.println("Errore: Offerta non trovata.");
			break;
		}
	}
    
    public void rifiutaOfferta(Offerta offerta) {
    	switch(controller.rifiutaOfferta(offerta)) {
	    	case 0:
				gridOfferte.getChildren().clear();
				mostraOfferteAnnuncio(controller.getAnnuncioOfferta(offerta));
	    	break;
	    	
	    	case 1:
	    		System.out.println("Errore: Offerta non trovata.");
	    	break;
    	}
    }
    
    private void rimuoviAnnuncio(Annuncio annuncio) {
        if(controller.rimuoviAnnuncio(annuncio) == 0) {
            gridProdotti.getChildren().clear();
            CostruisciProdottiUtente();
            sceneManager.showPopupAlert(containerAnnunciStudente, "Rimozione avvenuta", "L'annuncio è stato rimosso con successo.");
        }
        else {
			sceneManager.showPopupError(containerAnnunciStudente, "Rimozione fallita", "Si è verificato un errore durante la rimozione dell'annuncio.");
		}
    }
    
    @FXML
    public void tornaIndietroAnnunci() {
		AnnunciPane.setVisible(true);
		OfferteAnnunciPane.setVisible(false);
		gridProdotti.getChildren().clear();
		CostruisciProdottiUtente();
	}
    
    public void showInfoOfferta(MouseEvent e, Offerta offerta) {
        sceneManager.showInfoOfferta(e, offerta);
    }
}