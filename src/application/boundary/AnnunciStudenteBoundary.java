package application.boundary;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AnnunciStudenteBoundary {

    private Controller controller;

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
        // Prende l'oggetto cliccato
        Object source = e.getSource();

        // Controlla se è una Label
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
                    
                case "Offerte":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offerte.fxml"));
		    	        Parent root = loader.load();
		                OfferteBoundary offerteCtrl = loader.getController();
		                offerteCtrl.setController(this.controller);
		                offerteCtrl.setUsername(this.controller.getStudente().getUsername());
		                offerteCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
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

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                    
	            case "I tuoi annunci":
	            	System.out.println("Sei già nella pagina I tuoi annunci.");
	            break;

                default:
                	// Profilo utente
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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                break;
            }
        }
    }

    public boolean CostruisciProdottiUtente(Studente s) {
        ArrayList<Annuncio> annunci;
        
        if(controller.getStudente().getAnnunciPubblicati().isEmpty()) 
        {
        	annunci = controller.getAnnunciStudente(s);
        	controller.setAnnunciPubblicati(annunci);
        	
        }
		else 
        {
			annunci = controller.getStudente().getAnnunciPubblicati();
        	System.out.println("sono nell'else");
        }
        
        String titolo = "";
        
        if(annunci.size() == 0) titolo = "Non ci sono annunci attivi di ";
        else titolo = "Annunci attivi di ";
        
        labelAnnunciPubblicati.setText(titolo + s.getUsername());
        
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
            String path = a.getOggetto().getImmagineOggetto();
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
            System.out.println("Immagine non trovata: " + a.getOggetto().getImmagineOggetto());
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

        Label titolo = new Label(a.getTitoloAnnuncio());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titolo.setWrapText(true);
        titolo.setMaxWidth(200);
        titolo.setAlignment(Pos.CENTER);

        Label stato = new Label(a.isStatoAnnuncio() ? "Attivo" : "Non attivo");
        stato.getStyleClass().add(a.isStatoAnnuncio() ? "label-attivo" : "label-non-attivo");
        VBox.setMargin(stato, new Insets(4, 0, 4, 0));

        Label prezzo = new Label(String.format("\u20AC %.2f", a.getPrezzo()));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 12;");

        Label tipo = new Label(a.getOggetto().getCategoria() + " - " + a.getTipologia());
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
        
        Timestamp data = a.getDataPubblicazione();
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

    public void mostraOfferteAnnuncio(Annuncio a)
    {
    	AnnunciPane.setVisible(false);
    	OfferteAnnunciPane.setVisible(true);

    	ArrayList<Offerta> offerte;
    	
    	if(controller.getStudente().getOfferteRicevute().isEmpty()) {
    		offerte = controller.getOffertebyAnnuncio(a);
        	labelOfferteAnnuncio.setText("Non ci sono offerte per l'annuncio: " + a.getTitoloAnnuncio());
    	}
    	else{
    		offerte = controller.getStudente().getOfferteRicevute();
        	labelOfferteAnnuncio.setText("Queste sono le offerte dell'annuncio: " + a.getTitoloAnnuncio());
    	}
        
        int column = 0;
        int row = 0;
        boolean abilitaTasti = true;
        
        for(Offerta o : offerte) if(o.getStatoOfferta().equals("Accettata")) abilitaTasti = false;
        
    	for(Offerta o : offerte) {
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

        // Stato Offerta
        Label statoOfferta = new Label(o.getStatoOfferta());
        statoOfferta.setPrefWidth(90);
        statoOfferta.setAlignment(Pos.CENTER);

        if (o.getStatoOfferta().equals("Attesa")) {
            statoOfferta.getStyleClass().add("label-attesa");
        } else if (o.getStatoOfferta().equals("Rifiutata")) {
            statoOfferta.getStyleClass().add("label-non-attivo");
        } else {
            statoOfferta.getStyleClass().add("label-attivo");
        }

        HBox dataPubblicazioneBox = new HBox();
        
        Timestamp data = o.getDataPubblicazione();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = data.toLocalDateTime().format(formatter);
        
        Label dataPubblicazione = new Label(formattedDate);
        dataPubblicazione.setStyle("-fx-text-fill: gray; -fx-font-size: 12;");
        
        dataPubblicazioneBox.getChildren().add(dataPubblicazione);
        dataPubblicazioneBox.setAlignment(Pos.CENTER);
        dataPubblicazioneBox.setPrefWidth(150);

        // Info Studente
        HBox infoBox = new HBox(10);
        infoBox.setPrefWidth(200);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        ImageView iconInfo = new ImageView();
        try {
            File file = new File(o.getStudente().getImmagineProfilo());
            Image img = file.exists()
                    ? new Image(file.toURI().toString())
                    : new Image(getClass().getResource(o.getStudente().getImmagineProfilo()).toExternalForm());

            iconInfo.setImage(img);
            iconInfo.setFitWidth(33);
            iconInfo.setFitHeight(33);
            iconInfo.setPreserveRatio(false);
            iconInfo.setClip(new Circle(16.5, 16.5, 16.5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Label autoreOfferta = new Label(o.getStudente().getUsername());
        autoreOfferta.setStyle("-fx-font-weight: bold;");
        infoBox.getChildren().addAll(iconInfo, autoreOfferta);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Bottoni
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
    
    public void accettaOfferta(Offerta o) {
		switch(controller.accettaOfferta(o)) {
			case 0:
				gridOfferte.getChildren().clear();
				tornaIndietroAnnunci();
				//DA RIMUOVERE E METTERE REMOVE
				controller.svuotaOfferteRicevute();
			break;
			
			case 1:
				System.out.println("Errore: Offerta non trovata.");
			break;
		}
	}
    
    public void rifiutaOfferta(Offerta o) {
    	switch(controller.rifiutaOfferta(o)) {
	    	case 0:
				gridOfferte.getChildren().clear();
				mostraOfferteAnnuncio(o.getAnnuncio());
				//DA RIMUOVERE E METTERE REMOVE
				controller.svuotaOfferteRicevute();
	    	break;
	    	
	    	case 1:
	    		System.out.println("Errore: Offerta non trovata.");
	    	break;
    	}
    }
    
    private void rimuoviAnnuncio(Annuncio a) {
        if(controller.rimuoviAnnuncio(a) == 0) {
            gridProdotti.getChildren().clear();
            controller.SvuotaAnnunciPubblicati();
            CostruisciProdottiUtente(controller.getStudente());
            ShowPopupAlert("Rimozione avvenuta", "L'annuncio è stato rimosso con successo.");
        }
        else {
			ShowPopupAlert("Rimozione fallita", "Si è verificato un errore durante la rimozione dell'annuncio.");
		}
    }
    
    @FXML
    public void tornaIndietroAnnunci() {
		AnnunciPane.setVisible(true);
		OfferteAnnunciPane.setVisible(false);
		gridProdotti.getChildren().clear();
		CostruisciProdottiUtente(controller.getStudente());
	}
    
    public void showInfoOfferta(MouseEvent e, Offerta offerta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOfferteAnnuncio.fxml"));
            Parent root = loader.load();
            PopupOfferteAnnuncioBoundary popupInfoController = loader.getController();
            popupInfoController.setController(this.controller);
            popupInfoController.setPopupInfoOfferta(offerta);
            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("Informazioni di " + offerta.getTipologia().toLowerCase());
            popupStage.getIcons().add(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
            popupStage.setResizable(false);
            mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
            popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

            popupStage.show();

            popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void ShowPopupAlert(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupAlert.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) containerAnnunciStudente.getScene().getWindow();
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