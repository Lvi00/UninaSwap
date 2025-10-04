package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PopupEditOffertaBoundary {

    @FXML private VBox GeneralOffersPane;
    @FXML private VBox paneOffertaVendita;
    @FXML private VBox paneOffertaScambio;
    @FXML private VBox paneOffertaRegalo;
    @FXML private Label labelNessunOggettoOfferto;
    @FXML private TextArea messaggioMotivazionale;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    @FXML private Label labelOffertaScambio;
    @FXML private Label labelOffertaVendita;
    @FXML private Label labelOffertaRegalo;
    @FXML private Button inviaDatiOfferta;
    @FXML private Button inviaMotivazioneOfferta;
    @FXML private GridPane gridOggettiOfferti;
    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
    
    enum Categorie {
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Cultura,
        Musica
    }

    public void CostruisciPopupEdit(Offerta offerta, Stage stage) {
        // Nascondo tutto inizialmente
        paneOffertaVendita.setVisible(false);
        paneOffertaVendita.setManaged(false);
        paneOffertaScambio.setVisible(false);
        paneOffertaScambio.setManaged(false);
        paneOffertaRegalo.setVisible(false);
        paneOffertaRegalo.setManaged(false);

        if (offerta instanceof OffertaVendita) {
            OffertaVendita offertaVendita = (OffertaVendita) offerta;
            
            paneOffertaVendita.setVisible(true);
            paneOffertaVendita.setManaged(true);
            
            // Imposta prezzo
            double prezzo = controller.getPrezzoOfferta(offertaVendita);
            String prezzoString = String.format("%.2f", prezzo).replace(".", ",");
            String[] parti = prezzoString.split(",");
            campoPrezzoIntero.setText(parti[0]);
            campoPrezzoDecimale.setText(parti[1]);

            VBox.setMargin(labelOffertaVendita, new Insets(20, 0, 0, 0));
            stage.setWidth(450);
            stage.setHeight(175);       
            
            inviaDatiOfferta.setOnMouseClicked(event -> prelevaDatiOfferta(offerta, event));
        }
        else if (offerta instanceof OffertaScambio) {
        	OffertaScambio offertaScambio = (OffertaScambio) offerta;
            paneOffertaScambio.setVisible(true);
            paneOffertaScambio.setManaged(true);
            
            ObservableList<Oggetto> listaOggettiOfferti = FXCollections.observableArrayList(controller.getOggettiOfferti(offertaScambio));
            
            controller.setOggettiOfferti(offertaScambio, new ArrayList<>(listaOggettiOfferti));
            gridOggettiOfferti.getChildren().clear();
            gridOggettiOfferti.getColumnConstraints().clear();

            if (!listaOggettiOfferti.isEmpty()) {
                labelNessunOggettoOfferto.setVisible(false);
                int numeroCol = listaOggettiOfferti.size();
                int larghezzaCard = 300;
                int altezzaCard = 400;

                gridOggettiOfferti.setHgap(35);
                gridOggettiOfferti.setVgap(10);
                gridOggettiOfferti.setAlignment(Pos.CENTER);
                VBox.setMargin(gridOggettiOfferti, new Insets(10));

                int colonna = 0;
                for (Oggetto oggetto : listaOggettiOfferti) {
                    StackPane card = creaCardOggettoOfferto(oggetto, offerta);
                    card.getStyleClass().add("card-annuncio");

                    gridOggettiOfferti.add(card, colonna, 0);
                    GridPane.setFillWidth(card, false);

                    // colonna cresce automaticamente
                    ColumnConstraints col = new ColumnConstraints();
                    col.setHgrow(Priority.NEVER);
                    gridOggettiOfferti.getColumnConstraints().add(col);

                    colonna++;
                }

                double larghezzaTotaleCard = (larghezzaCard * numeroCol) / 1.2;
                stage.setWidth(larghezzaTotaleCard);
                stage.setHeight(altezzaCard);
            } else {
                stage.setWidth(450);
                stage.setHeight(175);
                labelNessunOggettoOfferto.setVisible(true);
            }

        } else if (offerta instanceof OffertaRegalo) {
        	OffertaRegalo offertaRegalo = (OffertaRegalo) offerta;
            paneOffertaRegalo.setVisible(true);
            paneOffertaRegalo.setManaged(true);

            String motivazione = controller.getMotivazioneOfferta(offertaRegalo);
            if (motivazione == null || motivazione.equalsIgnoreCase("Assente")) {
                messaggioMotivazionale.setText("");
            } else {
                messaggioMotivazionale.setText(motivazione);
            }

            VBox.setMargin(labelOffertaRegalo, new Insets(20, 0, 0, 0));
            stage.setWidth(450);
            stage.setHeight(175);
            
            inviaMotivazioneOfferta.setOnMouseClicked(event -> prelevaDatiOffertaRegalo(offerta, event));
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2);
            stage.setY(bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2);
        }
    }

    private void prelevaDatiOfferta(Offerta offerta, MouseEvent e) {
    	Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

    	if(offerta instanceof OffertaVendita)
    	{
    		OffertaVendita offertaVendita = (OffertaVendita) offerta;
        	String intero = campoPrezzoIntero.getText().trim();
            String decimale = campoPrezzoDecimale.getText().trim();

            // Se intero è vuoto, aggiungi "0"
            if (intero.isEmpty()) {
            	intero = "0";
            }
            
            // Se decimale è vuoto, aggiungi "00"
            if (decimale.isEmpty()) {
                decimale = "00";
            }

            // Costruisci il prezzo finale
            String stringaPrezzo = intero + "." + decimale;
            
            switch(controller.checkModificaControffertaVendita(offertaVendita, stringaPrezzo)) {
	    		case 0:
	                currentStage.close();
	                campoPrezzoIntero.setText(intero);
	                campoPrezzoDecimale.setText(decimale);
	                sceneManager.showPopupAlert(GeneralOffersPane, "Modifica effetuata", "La modifica dell'offerta è avvenuta con successo");
				break;
				
	    		case 1:
	    			sceneManager.showPopupError(GeneralOffersPane, "Errore nella modifica dell'offerta", "La controfferta non è valida, o è maggiore del prezzo dell'annuncio o è uguale alla controfferta precedente");
				break;
				
	    		case 2:
	    			sceneManager.showPopupError(GeneralOffersPane, "Errore inaspettato", "Impossibile modificare l'offerta");
	    		break;
            }
    	}
    }
    
    private void prelevaDatiOffertaRegalo(Offerta offerta, MouseEvent e) {
    	Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

    	if(offerta instanceof OffertaRegalo) {
            String motivazione = messaggioMotivazionale.getText().trim();
            
            OffertaRegalo offertaRegalo = (OffertaRegalo) offerta;
            
	        if (motivazione.isEmpty()) {
	            motivazione = "Assente";
	        }
	        
	        switch(controller.editOffertaRegaloMotivazione(offertaRegalo, motivazione)){
	    		case 0:
	                currentStage.close();
	                messaggioMotivazionale.setText(motivazione);
	                sceneManager.showPopupAlert(GeneralOffersPane, "Modifica effetuata", "La modifica dell'offerta è avvenuta con successo");
				break;
				
	    		case 1:
	    			sceneManager.showPopupError(GeneralOffersPane, "Errore nella modifica dell'offerta", "Offerta troppo lunga, superiore al limite di caratteri consentiti o identica all'offerta corrente");
				break;
				
	    		case 2:
	    			sceneManager.showPopupError(GeneralOffersPane, "Errore inaspettato!!!", "Impossibile modificare l'offerta");
	    		break;
	        }
    	}
    }
    
    private StackPane creaCardOggettoOfferto(Oggetto oggetto, Offerta offerta) {
        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(10));

        ImageView localImageView = new ImageView(); 
    	String path = controller.getImmagineOggetto(oggetto); 
    	Image img; 
    	File file = new File(path);
    	if (file.exists()) 
    	{ 
    		img = new Image(file.toURI().toString()); 
    	} 
    	else 
    	{ 
    		img = new Image(getClass().getResource(path).toExternalForm()); 
    	} 
    	localImageView.setFitWidth(150); 
    	localImageView.setFitHeight(100); 
    	localImageView.setPreserveRatio(false); 
    	localImageView.setClip(new Rectangle(150, 100)); 
    	localImageView.setImage(img); localImageView.setStyle("-fx-cursor: hand;"); 
    	localImageView.setOnMouseClicked(event -> showFileChooser(event, oggetto, localImageView));

        
        Label categoria = new Label(controller.getCategoriaOggetto(oggetto));
        categoria.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-background-color: #f4f4f4;" +
            "-fx-border-color: #bdbdbd;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 6 10;" +
            "-fx-pref-width: 150;"
        );

        ChoiceBox<Categorie> choiceCategoria = new ChoiceBox<>();
        choiceCategoria.getItems().addAll(Categorie.values());
        choiceCategoria.setValue(Categorie.valueOf(oggetto.getCategoria()));
        choiceCategoria.setPrefWidth(150);
        choiceCategoria.setStyle("-fx-cursor: hand;");
        choiceCategoria.setVisible(false);
        choiceCategoria.setManaged(false);

        Label descrizione = new Label(controller.getDescrizioneOggetto(oggetto));
        descrizione.setWrapText(true);
        descrizione.setPrefWidth(150);
        descrizione.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-background-color: #f4f4f4;" +
            "-fx-border-color: #bdbdbd;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 6 10;" +
            "-fx-pref-width: 150;" +
            "-fx-pref-height: 220;" +
            "-fx-alignment: top-left;"
        );

        TextArea textDescrizione = new TextArea(controller.getDescrizioneOggetto(oggetto));
        textDescrizione.setWrapText(true);
        textDescrizione.setPrefRowCount(3);
        textDescrizione.setPrefWidth(150);
        textDescrizione.setVisible(false);
        textDescrizione.setManaged(false);
        textDescrizione.setStyle("-fx-padding: -10px");

        content.getChildren().addAll(localImageView, categoria, choiceCategoria, descrizione, textDescrizione);

        ImageView iconDelete = new ImageView();
        try {
            iconDelete.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/delete_card.png").toExternalForm()));
            iconDelete.setFitWidth(22);
            iconDelete.setFitHeight(22);
            iconDelete.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button buttonDelete = new Button();
        buttonDelete.getStyleClass().add("tasto-terziario");
        buttonDelete.setPrefSize(24, 24);
        buttonDelete.setStyle("-fx-cursor: hand;");
        buttonDelete.setGraphic(iconDelete);

        ImageView iconEdit = new ImageView();
        try {
            iconEdit.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/edit.png").toExternalForm()));
            iconEdit.setFitWidth(22);
            iconEdit.setFitHeight(22);
            iconEdit.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button buttonEdit = new Button();
        buttonEdit.getStyleClass().add("tasto-edit");
        buttonEdit.setPrefSize(24, 24);
        buttonEdit.setStyle("-fx-cursor: hand;");
        buttonEdit.setGraphic(iconEdit);

        ImageView iconBack = new ImageView();
        try {
            iconBack.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/back.png").toExternalForm()));
            iconBack.setFitWidth(22);
            iconBack.setFitHeight(22);
            iconBack.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button buttonBack = new Button();
        buttonBack.getStyleClass().add("tasto-secondario");
        buttonBack.setPrefSize(24, 24);
        buttonBack.setStyle("-fx-cursor: hand;");
        buttonBack.setGraphic(iconBack);
        buttonBack.setVisible(false);
        buttonBack.setManaged(false);
        
        ImageView iconCheck = new ImageView();
        try {
        	iconCheck.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/check.png").toExternalForm()));
        	iconCheck.setFitWidth(22);
        	iconCheck.setFitHeight(22);
        	iconCheck.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button buttonCheck = new Button();
        buttonCheck.getStyleClass().add("tasto-conferma");
        buttonCheck.setPrefSize(24, 24);
        buttonCheck.setStyle("-fx-cursor: hand;");
        buttonCheck.setGraphic(iconCheck);
        buttonCheck.setVisible(false);
        buttonCheck.setManaged(false);

        buttonEdit.setOnMouseClicked(event -> showCampiModifica(oggetto, categoria, choiceCategoria, descrizione, textDescrizione, buttonDelete, buttonEdit, buttonBack, buttonCheck));
        buttonBack.setOnMouseClicked(event -> showCampiModifica(oggetto, categoria, choiceCategoria, descrizione, textDescrizione, buttonDelete, buttonEdit, buttonBack, buttonCheck));
        buttonDelete.setOnMouseClicked(event -> rimuoviOggettoOfferto(oggetto, offerta));

        buttonCheck.setOnMouseClicked(event -> {
            String newPath;
            File fileOggettoOfferto = controller.getFileOggettoOfferto();
            if (fileOggettoOfferto != null) {
                newPath = fileOggettoOfferto.getAbsolutePath();
            } else {
                newPath = path;
            }
            editOggettiOfferti(        
            		oggetto,
                    offerta,
                    newPath,
                    choiceCategoria.getValue().toString(),
                    textDescrizione.getText(),
                    categoria,
                    choiceCategoria,
                    descrizione,
                    textDescrizione,
                    buttonDelete,
                    buttonEdit,
                    buttonBack,
                    buttonCheck);
        });

        AnchorPane topLeftLayer = new AnchorPane(buttonEdit, buttonBack);
        AnchorPane topRightLayer = new AnchorPane(buttonDelete, buttonCheck);
        topLeftLayer.setPickOnBounds(false);
        AnchorPane.setTopAnchor(buttonEdit, -16.0);
        AnchorPane.setLeftAnchor(buttonEdit, -16.0);
        AnchorPane.setTopAnchor(buttonBack, -16.0);
        AnchorPane.setLeftAnchor(buttonBack, -16.0);
        AnchorPane.setTopAnchor(buttonCheck, -16.0);
        AnchorPane.setRightAnchor(buttonCheck, -16.0);
        topRightLayer.setPickOnBounds(false);
        AnchorPane.setTopAnchor(buttonDelete, -16.0);
        AnchorPane.setRightAnchor(buttonDelete, -16.0);

        StackPane card = new StackPane(content, topRightLayer, topLeftLayer);
        card.getStyleClass().add("card-annuncio");

        return card;
    }

    private void rimuoviOggettoOfferto(Oggetto oggetto, Offerta offerta) {
    	if(controller.rimuoviOggettoOfferto(oggetto, offerta) == 0) {
    		sceneManager.showPopupAlert(GeneralOffersPane, "Oggetto rimosso", "L'oggetto è stato rimosso con successo dall'offerta di scambio");
    	}
    	else {
			sceneManager.showPopupError(GeneralOffersPane, "Errore nella rimozione", "Impossibile rimuovere l'oggetto dall'offerta di scambio");
		}
    	
    	CostruisciPopupEdit(offerta, (Stage) GeneralOffersPane.getScene().getWindow());
    }
    
    private void showCampiModifica(Oggetto oggetto, Label categoria, ChoiceBox<Categorie> choiceCategoria, Label descrizione, TextArea textDescrizione, Button btnDelete, Button btnModifica, Button btnBack, Button btnCheck) {
        if(categoria.isVisible()) {
	        categoria.setVisible(false);
	        descrizione.setVisible(false);
	        categoria.setManaged(false);
	        descrizione.setManaged(false);
	        choiceCategoria.setVisible(true);
	        textDescrizione.setVisible(true);
	        choiceCategoria.setManaged(true);
	        textDescrizione.setManaged(true);
	        
	        choiceCategoria.setValue(Categorie.valueOf(controller.getCategoriaOggetto(oggetto)));
	        textDescrizione.setText(controller.getDescrizioneOggetto(oggetto));
	        
	        btnDelete.setVisible(false);
	        btnDelete.setManaged(false);
	        btnModifica.setVisible(false);
	        btnModifica.setManaged(false);
	        btnBack.setVisible(true);
	        btnBack.setManaged(true);
	        btnCheck.setVisible(true);
	        btnCheck.setManaged(true);
        }
        else {
	        categoria.setVisible(true);
	        descrizione.setVisible(true);
	        categoria.setManaged(true);
	        descrizione.setManaged(true);
	        
	        choiceCategoria.setVisible(false);
	        textDescrizione.setVisible(false);
	        choiceCategoria.setManaged(false);
	        textDescrizione.setManaged(false);
	        
	        btnDelete.setVisible(true);
	        btnDelete.setManaged(true);
	        btnModifica.setVisible(true);
	        btnModifica.setManaged(true);
	        btnBack.setVisible(false);
	        btnBack.setManaged(false);
	        btnCheck.setVisible(false);
	        btnCheck.setManaged(false);
        }
    }
    
    public void editOggettiOfferti(Oggetto oggetto, Offerta offerta, String pathImmagine, String categoria, String descrizione, Label categoriaLabel, ChoiceBox<Categorie> choiceCategoria, Label descrizioneLabel, TextArea textDescrizione, Button btnDelete, Button btnEdit, Button btnBack, Button btnCheck) {
    	descrizione = descrizione.trim();
    	
    	int result = controller.editOffertaScambio(oggetto, offerta, pathImmagine, categoria, descrizione);

	    switch(result) {
	        case 0:
	            sceneManager.showPopupAlert(GeneralOffersPane, "Modifiche Effettuate", "Le modifiche sono state applicate con successo");
            break;
            
	        case 1:
	            sceneManager.showPopupError(GeneralOffersPane, "Errore di modifica", "Nessun cambiamento effettuato");
            break;
            
	        case 2:
	            sceneManager.showPopupError(GeneralOffersPane, "Errore di modifica", "Offerte ripetute");
            break;
            
	        case 3:
	            sceneManager.showPopupError(GeneralOffersPane, "Errore di modifica", "L'offerta non è stata modificata");
            break;
            
	        case -1:
	        	sceneManager.showPopupError(GeneralOffersPane, "Errore inaspettato", "Impossibile modificare l'offerta");
			break;
	    }

	    showCampiModifica(oggetto, categoriaLabel, choiceCategoria, descrizioneLabel, textDescrizione, btnDelete, btnEdit, btnBack, btnCheck);
	    CostruisciPopupEdit(offerta, (Stage) GeneralOffersPane.getScene().getWindow());
    }
    
    public void showFileChooser( MouseEvent e, Oggetto oggetto, ImageView targetImageView) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            targetImageView.setImage(image);
            controller.setFileOggettoOfferto(selectedFile);
        }
    }
}
