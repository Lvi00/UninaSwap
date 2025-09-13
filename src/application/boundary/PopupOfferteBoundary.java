package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class PopupOfferteBoundary {
	
    enum Categorie {
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Cultura,
        Musica
    }

    private Controller controller;
    @FXML private HBox containerOfferte;
    @FXML private AnchorPane paneOfferta;
    @FXML private AnchorPane paneOggettiOfferti;
    @FXML private Label titoloAnnuncio;
    @FXML private Label descrizioneAnnuncio;
    @FXML private Label prezzoAnnuncio;
    @FXML private Label usernameAnnuncio;
    @FXML private Label disponibilitàAnnuncio;
    @FXML private Label categoriaAnnuncio;
    @FXML private Label sedeAnnuncio;
    @FXML private ImageView immagineAnnuncio;
    @FXML private ImageView immagineProfilo;
    @FXML private AnchorPane PaneOfferteVendita;
    @FXML private AnchorPane PaneOfferteScambio;
    @FXML private AnchorPane PaneOfferteRegalo;
    @FXML private Button buttonOfferta;
    @FXML private Button aggiungiOggetto;
    @FXML private Button mostraOfferta;
    @FXML private Button oggettiOffertiButton;
    @FXML private Button sendButton;
    @FXML private Button mostraCampiScambio;
    @FXML private ImageView sendImage;
    @FXML private Button controffertaButton;
    @FXML private ImageView backImage;
    @FXML private Button backButton;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    @FXML private ImageView imageScambioButtonImage;
    @FXML private Button imageScambioButton;
    @FXML private StackPane imageScambio;
    @FXML private ImageView immagineCaricata;
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private TextArea campoDescrizioneAnnuncioScambio;
    @FXML private TextArea campoDescrizioneAnnuncioRegalo;
    @FXML private TableView<Oggetto> tabellaOggetti;
    @FXML private TableColumn<Oggetto, String> colId;
    @FXML private TableColumn<Oggetto, String> colCategoria;
    @FXML private TableColumn<Oggetto, String> colDescrizione;
    @FXML private TableColumn<Oggetto, String> colPercorsoImmagine;
    @FXML private TableColumn<Oggetto, String> colAzioni;
    
    private File fileSelezionato = null;
	private ArrayList<Oggetto> listaOggettiOfferti = new ArrayList<Oggetto>();
    private Annuncio annuncio;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setProdottiBoundary(ProdottiBoundary prodottiBoundary) {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
    }
    
    public void setAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;
        costruisciPopup();
    }

    @FXML
    private void costruisciPopup() {
    	if(this.paneOggettiOfferti.isVisible()) {
    		paneOggettiOfferti.setVisible(false);
    		paneOfferta.setVisible(true);
    	}
    	
        if (this.annuncio != null) {
            titoloAnnuncio.setText(annuncio.getTitoloAnnuncio());
            descrizioneAnnuncio.setText(annuncio.getDescrizioneAnnuncio());

            if (annuncio.getTipologia().equals("Vendita")) {
                prezzoAnnuncio.setText(String.format("€ %.2f", annuncio.getPrezzo()));
            }

            categoriaAnnuncio.setText(annuncio.getOggetto().getCategoria());
            usernameAnnuncio.setText(annuncio.getOggetto().getStudente().getUsername());
            disponibilitàAnnuncio.setText(
                annuncio.getGiorni() + " dalle " + annuncio.getFasciaOrariaInizio() + " alle " + annuncio.getFasciaOrariaFine()
            );
            
            Sede sede = annuncio.getSede();
            
            sedeAnnuncio.setText(sede.getParticellaToponomastica() + " " + sede.getDescrizioneIndirizzo() + " " + sede.getCivico() + " " + sede.getCap());

            try {
                String path = annuncio.getOggetto().getImmagineOggetto();
                File file = new File(path);
                Image img;
                if (file.exists()) {
                    img = new Image(file.toURI().toString());
                } else {
                    img = new Image(getClass().getResource(path).toExternalForm());
                }
                immagineAnnuncio.setImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String path = annuncio.getOggetto().getStudente().getImmagineProfilo();
                File file = new File(path);
                Image img;
                if (file.exists()) {
                    img = new Image(file.toURI().toString());
                } else {
                    img = new Image(getClass().getResource(path).toExternalForm());
                }

                immagineProfilo.setImage(img);
                immagineProfilo.setFitWidth(94);
                immagineProfilo.setFitHeight(94);
                immagineProfilo.setPreserveRatio(false);
                Circle clip = new Circle(47, 47, 47);
                immagineProfilo.setClip(clip);
                
                switch(this.annuncio.getTipologia()) {
	                case "Scambio":
	                    buttonOfferta.setText("Proponi");
	                    buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
	                    controffertaButton.setVisible(false);
	                    mostraCampiScambio.setVisible(true);
	                    aggiungiOggetto.setVisible(false);
	                    oggettiOffertiButton.setVisible(false);
                    break;
	                
	                case "Vendita":
						buttonOfferta.setText("Acquista");
	                	buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
						controffertaButton.setVisible(true);
	                	aggiungiOggetto.setVisible(false);
	                	oggettiOffertiButton.setVisible(false);
					break;
					
					case "Regalo":
						buttonOfferta.setText("Richiedi");
	                	buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
	                	controffertaButton.setVisible(false);
	                	PaneOfferteRegalo.setVisible(true);
	                	aggiungiOggetto.setVisible(false);
	                	oggettiOffertiButton.setVisible(false);
					break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void aggiungiOggettoDaScambiare(MouseEvent e) {
	    String descrizione = campoDescrizioneAnnuncioScambio.getText();
	    String categoriaSelezionata = campoCategoriaOggetto.getValue().toString();
	    String percorsoImmagine = null;
	    
	    if (fileSelezionato != null) {
	        percorsoImmagine = fileSelezionato.getAbsolutePath();
	    }
	    
	    switch(controller.controllaCampiOggettoScambio(descrizione, categoriaSelezionata, percorsoImmagine)) {
			case 0:
			   Oggetto nuovoOggetto = new Oggetto(percorsoImmagine, categoriaSelezionata.toString(), descrizione, controller.getStudente());
			   
			   if(this.listaOggettiOfferti.size() < 5)
			   {
				    //Aggiungo l'oggetto all'arraylist
				   	this.listaOggettiOfferti.add(nuovoOggetto);
			   		ShowPopupAlert("Oggetto aggiunto!", "L'oggetto è stato aggiunto alla lista degli oggetti da offrire.");
	    	   }
			   else 
			   {
				   	ShowPopupError("Troppi oggetti!", "Hai inserito il limite massimo di 5 oggetti scambiabili.");
			   }
			   // Pulisco i campi del form
			   campoDescrizioneAnnuncioScambio.clear();
			   campoCategoriaOggetto.getSelectionModel().selectFirst();
			   immagineCaricata.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/no_image.png").toExternalForm()));
			   fileSelezionato = null;
			break;
			
			case 1:
				ShowPopupError("Errore nella categoria", "Seleziona una categoria valida.");
			break;
			
			case 2:
				ShowPopupError("Errore nella descrizione", "La descrizione deve essere compresa tra 1 e 255 caratteri.");
			break;
			
			case 3:
				ShowPopupError("Errore nell'immagine", "Carica un'immagine valida.");
			break;
    	}
    }
    
    @FXML
    public void mostraOggettiOfferti(MouseEvent e) {
        if (this.listaOggettiOfferti.isEmpty()) {
            ShowPopupError("Nessun oggetto aggiunto", "Non hai ancora aggiunto oggetti da offrire per lo scambio.");
            return;
        }

        paneOfferta.setVisible(false);
        paneOggettiOfferti.setVisible(true);

        // Pulisce eventuali dati precedenti e popola la TableView
        tabellaOggetti.getItems().setAll(listaOggettiOfferti);
    }

    //Chiamato automaticamente da JavaFX dopo il caricamento dell'FXML
    @FXML
    public void initialize() 
    {
        // Inizializza le colonne
        colId.setCellValueFactory(data -> 
            new SimpleStringProperty(String.valueOf(tabellaOggetti.getItems().indexOf(data.getValue()) + 1))
        );
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));
        colDescrizione.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescrizione()));
        colPercorsoImmagine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImmagineOggetto()));

        // Aggiungi tooltip alle colonne
        addTooltipToColumn(colCategoria);
        addTooltipToColumn(colDescrizione);
        addTooltipToColumn(colPercorsoImmagine);

        // Colonna Azioni con bottone "rimuovi"
        colAzioni.setCellFactory(col -> new TableCell<Oggetto, String>() {
            private final Button removeButton = new Button();
            {
                ImageView imageView = new ImageView(
                    new Image(getClass().getResourceAsStream("../IMG/immaginiProgramma/Delete.png"))
                );
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                removeButton.setGraphic(imageView);
                removeButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-cursor: hand;");
                removeButton.setOnAction(e -> {
                    Oggetto oggetto = getTableView().getItems().get(getIndex());
                    tabellaOggetti.getItems().remove(oggetto);
                    listaOggettiOfferti.remove(oggetto);
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new StackPane(removeButton));
            }
        });

        // --- Imposta policy di resize colonne ---
        tabellaOggetti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Imposta altezza fissa delle righe
        tabellaOggetti.setFixedCellSize(38);

        // Aggiorna altezza dinamica quando cambia il numero di righe
        tabellaOggetti.getItems().addListener((ListChangeListener<Oggetto>) change -> aggiornaAltezzaTabella());
    }

   // Aggiorna altezza dinamica
    private void aggiornaAltezzaTabella() {
        int numeroRighe = tabellaOggetti.getItems().size();
        double altezzaHeader = 28;
        double altezzaRighe = numeroRighe * tabellaOggetti.getFixedCellSize();

        double altezzaTotale;

        if(numeroRighe == 0) {
            altezzaTotale = paneOggettiOfferti.getHeight() - tabellaOggetti.getLayoutY(); 
            
        } else {
            altezzaTotale = altezzaHeader + altezzaRighe;
        }

        tabellaOggetti.setPrefHeight(altezzaTotale);
        tabellaOggetti.setMinHeight(altezzaTotale);
        tabellaOggetti.setMaxHeight(altezzaTotale);
    }

    // Metodo di utilità per tooltip
    private void addTooltipToColumn(TableColumn<Oggetto, String> column) {
        column.setCellFactory(col -> new TableCell<Oggetto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item);
                    Tooltip tooltip = new Tooltip(item);
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(200);
                    setTooltip(tooltip);
                }
            }
        });
    }
    
    @FXML
    public void inviaOfferta(MouseEvent e) { 
    	Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    	
    	if(this.annuncio.getTipologia().equals("Vendita")) {
    		switch(controller.inviaOffertaVendita(this.annuncio)) {
	    		case 0:
	    	        currentStage.close(); 
	    	        ShowPopupAlert("Acquisto effettuato!", "Il prodotto è stato acquistato con successo.");
	    		break;
	    		
	            case -1:
	                ShowPopupError("Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
	            break;
    		}
    	}
    	
    	else if(this.annuncio.getTipologia().equals("Regalo")) {
    	    String messaggioMotivazionale = campoDescrizioneAnnuncioRegalo.getText();
    	    
    	    if (messaggioMotivazionale == null || messaggioMotivazionale.trim().isEmpty()) 
    	    {
    	    	messaggioMotivazionale = "Assente";
    	    } 
    	    else 
    	    {
    	    	messaggioMotivazionale = messaggioMotivazionale.trim();
    	    }

    	    switch(controller.inviaOffertaRegalo(annuncio, messaggioMotivazionale)) {
    	        case 0: // offerta inviata correttamente
                    currentStage.close();
    	            ShowPopupAlert("Richiesta inviata!",  "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
    	        break;

    	        case 1: // errore generico
    	            ShowPopupError("Errore nella richiesta", "La richiesta di " + annuncio.getTipologia() + " non è stata inviata a causa di un errore nei dati inseriti.");
    	        break;

    	        case -1: // offerta duplicata
    	            ShowPopupError("Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
    	        break;
    	    }
    	}
    	
    	else if(this.annuncio.getTipologia().equals("Scambio"))
    	{
    	    switch(controller.inviaOffertaScambio(this.annuncio, this.listaOggettiOfferti)) {
		        case 0: // offerta di scambio normale inviata correttamente
	                currentStage.close();
		            ShowPopupAlert("Richiesta inviata!",  "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
		        break;
	
		        case 1: // offerta di scambio personalizzata inviata correttamente
		        	currentStage.close();
		            ShowPopupAlert("Richiesta personalizzata inviata!",  "La richiesta di " + annuncio.getTipologia() + " con gli oggetti inseriti è stata inviata con successo.");
		        break;
		        
		        case -1: // offerta duplicata
		            ShowPopupError("Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
		        break;
    	    }
    	}
    }
    
    @FXML
    public void checkControfferta(MouseEvent e) {
    	Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    	String intero = campoPrezzoIntero.getText();
        String decimale = campoPrezzoDecimale.getText();

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
        
        switch(controller.checkControfferta(this.annuncio, stringaPrezzo)) {
            case 0:
                currentStage.close();
                ShowPopupAlert("Controfferta inviata!", "La controfferta è stata inviata con successo.");
            break;

            case 1:
                campoPrezzoIntero.clear();
                campoPrezzoDecimale.clear();
                ShowPopupError("Controfferta non valida!", "La controfferta deve avere max 3 cifre per la parte intera e max 2 cifre per quella decimale e (0 < controfferta < prezzo).");
            break;

            case -1:
                ShowPopupError("Offerta già esistente!","Hai già effettuato un'offerta per questo annuncio.");
            break;

            default:
                ShowPopupError("Errore sconosciuto", "Si è verificato un errore imprevisto. Riprova.");
            break;
        }
    }
    
    @FXML
    public void MostraControfferta(){
    	if(!PaneOfferteVendita.isVisible()){	
    		PaneOfferteVendita.setVisible(true);
    		backImage.setVisible(true);
    	    backButton.setVisible(true);
    	    controffertaButton.setVisible(false);
    	    buttonOfferta.setVisible(false);
    	    sendButton.setVisible(true);
    	    sendImage.setVisible(true);
    	}
    	else{
    		PaneOfferteVendita.setVisible(false);
    		backImage.setVisible(false);
    	    backButton.setVisible(false);
    	    controffertaButton.setVisible(true);   
    	    buttonOfferta.setVisible(true);
    	    sendButton.setVisible(false);
    	    sendImage.setVisible(false);
    	}
    }
    
    @FXML
    public void showFileChooser(MouseEvent e) {
    	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
    	
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            //Carica sull'imaggine di default la nuova immagine
            immagineCaricata.setImage(image);
            this.fileSelezionato = selectedFile;
        }
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
    
    private void ShowPopupError(String title, String message) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
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
    
    @FXML
    public void mostraCampiScambio(MouseEvent e) {
    	mostraCampiScambio.setVisible(false);
    	PaneOfferteScambio.setVisible(true);
    	aggiungiOggetto.setVisible(true);
    	oggettiOffertiButton.setVisible(true);
    }
}
