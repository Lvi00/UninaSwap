package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
            paneOffertaVendita.setVisible(true);
            paneOffertaVendita.setManaged(true);

            // Imposta prezzo
            double prezzo = ((OffertaVendita) offerta).getPrezzoOfferta();
            String prezzoString = String.format("%.2f", prezzo).replace(".", ",");
            String[] parti = prezzoString.split(",");
            campoPrezzoIntero.setText(parti[0]);
            campoPrezzoDecimale.setText(parti[1]);

            VBox.setMargin(labelOffertaVendita, new Insets(20, 0, 0, 0));
            stage.setWidth(450);
            stage.setHeight(175);

        } else if (offerta instanceof OffertaScambio) {
            paneOffertaScambio.setVisible(true);
            paneOffertaScambio.setManaged(true);

            ArrayList<Oggetto> listaOggetti = controller.getOggettiOffertiByOfferta(offerta);
            controller.setOggettiOfferti(listaOggetti);
            gridOggettiOfferti.getChildren().clear();
            gridOggettiOfferti.getColumnConstraints().clear();

            if (!listaOggetti.isEmpty()) {
                labelNessunOggettoOfferto.setVisible(false);
                int numeroCol = listaOggetti.size();
                int larghezzaCard = 300;
                int altezzaCard = 400;

                gridOggettiOfferti.setHgap(35);
                gridOggettiOfferti.setVgap(10);
                gridOggettiOfferti.setAlignment(Pos.CENTER);
                VBox.setMargin(gridOggettiOfferti, new Insets(10));

                int colonna = 0;
                for (Oggetto oggetto : listaOggetti) {
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
            paneOffertaRegalo.setVisible(true);
            paneOffertaRegalo.setManaged(true);

            String motivazione = ((OffertaRegalo) offerta).getMotivazione();
            if (motivazione == null || motivazione.equalsIgnoreCase("Assente")) {
                messaggioMotivazionale.setText("");
            } else {
                messaggioMotivazionale.setText(motivazione);
            }

            VBox.setMargin(labelOffertaRegalo, new Insets(20, 0, 0, 0));
            stage.setWidth(450);
            stage.setHeight(175);
        }

        inviaDatiOfferta.setOnAction(event -> prelevaDatiOfferta(offerta));

        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX() + (screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY(screenBounds.getMinY() + (screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    private void prelevaDatiOfferta(Offerta offerta) {

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
    	controller.rimuoviOggettoOfferto(oggetto, offerta);
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
	            sceneManager.showPopupError(GeneralOffersPane, "Errore di modifica", "Nessun record aggiornato (ID non trovato)");
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
