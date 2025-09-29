package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class PopupOfferteBoundary {
	
    enum Categorie {
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Musica
    }

    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
    @FXML private HBox containerOfferte;
    @FXML private AnchorPane paneOfferta;
    @FXML private AnchorPane paneOggettiOfferti;
    @FXML private Label titoloAnnuncio;
    @FXML private Label descrizioneAnnuncio;
    @FXML private Label prezzoAnnuncio;
    @FXML private Label usernameAnnuncio;
    @FXML private Label disponibilitàAnnuncio;
    @FXML private Label categoriaAnnuncio;
    @FXML private Label labelDataPubblicazione;
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
    @FXML private TableColumn<Oggetto, String> colCategoria;
    @FXML private TableColumn<Oggetto, String> colDescrizione;
    @FXML private TableColumn<Oggetto, String> colPercorsoImmagine;
    @FXML private TableColumn<Oggetto, String> colAzioni;
    private ObservableList<Oggetto> listaOggettiOfferti = FXCollections.observableArrayList();
    
    public void setProdottiBoundary(ProdottiBoundary prodottiBoundary) {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
    }

    public void costruisciPopup() {
    	Annuncio annuncio = controller.getAnnuncioSelezionato();
    	
    	if(this.paneOggettiOfferti.isVisible()) {
    		paneOggettiOfferti.setVisible(false);
    		paneOfferta.setVisible(true);
    	}
    	
        if (annuncio != null) {
            titoloAnnuncio.setText(controller.getTitoloAnnuncio(annuncio));
            descrizioneAnnuncio.setText(controller.getDescrizioneAnnuncio(annuncio));

            if (annuncio.getTipologia().equals("Vendita")) {
                prezzoAnnuncio.setText(String.format("€ %.2f", controller.getPrezzoAnnuncio(annuncio)));
            }

            categoriaAnnuncio.setText(controller.getCategoriaOggetto(controller.getOggettoAnnuncio(annuncio)));
            
            Timestamp data = controller.getDataPubblicazioneAnnuncio(annuncio);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = data.toLocalDateTime().format(formatter);
            
            labelDataPubblicazione.setText(formattedDate);
            
            usernameAnnuncio.setText(controller.getUsername(controller.getStudenteOggetto(controller.getOggettoAnnuncio(annuncio))));
            
            disponibilitàAnnuncio.setText(
                controller.giorniDisponibilitaAnnuncio(annuncio) + " dalle " + controller.getFasciaInizioAnnuncio(annuncio) + " alle " + controller.getFasciaFineAnnuncio(annuncio)
            );
            
            Sede sede = controller.getSedeAnnuncio(annuncio);
            
            sedeAnnuncio.setText(controller.getParticellaToponomasticaSede(sede) + " " + controller.getDescrizioneIndirizzo(sede) + " " + controller.getCivicoSede(sede) + " " + controller.getCapSede(sede));

            try {
                String path = controller.getImmagineOggetto(controller.getOggettoAnnuncio(annuncio));
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
                String path = controller.getImmagineProfilo(controller.getStudenteOggetto(controller.getOggettoAnnuncio(annuncio)));
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
                
                switch(controller.getTipologiaAnnuncio(annuncio)) {
	                case "Scambio":
	                    buttonOfferta.setText("Scambia");
	                    buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
	                    controffertaButton.setVisible(false);
	                    mostraCampiScambio.setVisible(true);
	                    
	                    if(PaneOfferteScambio.isVisible()) {
	                    	PaneOfferteScambio.setVisible(true);
	                    	aggiungiOggetto.setVisible(true);
	                    	oggettiOffertiButton.setVisible(true);
	                    	mostraCampiScambio.setVisible(false);
	                    }
	                    else {
	                    	PaneOfferteScambio.setVisible(false);
	                    	aggiungiOggetto.setVisible(false);
	                    	oggettiOffertiButton.setVisible(false);
	                    }
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
	    
	    File fileOggettoAlternativo = controller.getFileOggettoAlternativo();
	    
	    if (fileOggettoAlternativo != null) {
	        percorsoImmagine = fileOggettoAlternativo.getAbsolutePath();
	    }
	    
	    switch(controller.controllaCampiOggettoScambio(descrizione, categoriaSelezionata, percorsoImmagine)) {
			case 0:
			   Oggetto nuovoOggetto = new Oggetto(percorsoImmagine, categoriaSelezionata.toString(), descrizione, controller.getStudente());
			   
			   if (this.listaOggettiOfferti.size() < 5) {
				    sceneManager.showPopupAlert(containerOfferte, "Oggetto aggiunto!", "L'oggetto è stato aggiunto alla lista degli oggetti offerti da te.");
				    this.listaOggettiOfferti.add(nuovoOggetto);
			   }
			   else sceneManager.showPopupError(containerOfferte, "Troppi oggetti!", "Hai inserito il limite massimo di 5 oggetti scambiabili.");
			   
			   campoDescrizioneAnnuncioScambio.clear();
			   campoCategoriaOggetto.getSelectionModel().selectFirst();
			   immagineCaricata.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/no_image.png").toExternalForm()));
			   controller.setFileOggettoAlternativo(null);
			break;
			
			case 1:
				sceneManager.showPopupError(containerOfferte, "Errore nella categoria", "Seleziona una categoria valida.");
			break;
			
			case 2:
				sceneManager.showPopupError(containerOfferte, "Errore nella descrizione", "La descrizione deve essere compresa tra 1 e 255 caratteri.");
			break;
			
			case 3:
				sceneManager.showPopupError(containerOfferte, "Errore nell'immagine", "Carica un'immagine valida.");
			break;
    	}
    }
    
    @FXML
    public void mostraOggettiOfferti(MouseEvent e) {
        if (this.listaOggettiOfferti.isEmpty()) {
            sceneManager.showPopupError(containerOfferte, "Nessun oggetto aggiunto", "Non hai ancora aggiunto oggetti da offrire per lo scambio.");
            return;
        }
        
        paneOfferta.setVisible(false);
        paneOggettiOfferti.setVisible(true);
    }

    //Chiamato automaticamente da JavaFX dopo il caricamento dell'FXML
    @FXML
    public void initialize() {
        tabellaOggetti.setItems(listaOggettiOfferti);
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));
        colDescrizione.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescrizione()));
        colPercorsoImmagine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getImmagineOggetto()));

        addTooltipToColumn(colCategoria);
        addTooltipToColumn(colDescrizione);
        addTooltipToColumn(colPercorsoImmagine);

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
                    listaOggettiOfferti.remove(oggetto); // basta rimuovere dalla lista
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new StackPane(removeButton));
            }
        });

        tabellaOggetti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaOggetti.setFixedCellSize(38);
        tabellaOggetti.setSelectionModel(null);
        listaOggettiOfferti.addListener((ListChangeListener<Oggetto>) change -> aggiornaAltezzaTabella());
    }

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
    	
    	Annuncio annuncio = controller.getAnnuncioSelezionato();
    	
    	if(controller.getTipologiaAnnuncio(annuncio).equals("Vendita")) {
    		switch(controller.inviaOffertaVendita(annuncio)) {
	    		case 0:
	    	        currentStage.close(); 
	    	        sceneManager.showPopupAlert(containerOfferte, "Offerta inviata!", "Hai inviato correttamente un'offerta per questo annuncio.");
	    		break;
	    		
	            case -1:
	                sceneManager.showPopupError(containerOfferte, "Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
	            break;
    		}
    	}
    	
    	else if(controller.getTipologiaAnnuncio(annuncio).equals("Regalo")) {
    	    String messaggioMotivazionale = campoDescrizioneAnnuncioRegalo.getText();
    	    
    	    if (messaggioMotivazionale == null || messaggioMotivazionale.trim().isEmpty()) messaggioMotivazionale = "Assente";
    	    else messaggioMotivazionale = messaggioMotivazionale.trim();

    	    switch(controller.inviaOffertaRegalo(annuncio, messaggioMotivazionale)) {
    	        case 0: // offerta inviata correttamente
                    currentStage.close();
    	            sceneManager.showPopupAlert(containerOfferte, "Richiesta inviata!",  "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
    	        break;

    	        case 1: // errore generico
    	            sceneManager.showPopupError(containerOfferte, "Errore nella richiesta", "La richiesta di " + annuncio.getTipologia() + " non è stata inviata a causa di un errore nei dati inseriti.");
    	        break;

    	        case -1: // offerta duplicata
    	            sceneManager.showPopupError(containerOfferte, "Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
    	        break;
    	    }
    	}
    	
    	else if(controller.getTipologiaAnnuncio(annuncio).equals("Scambio"))
    	{
    	    switch(controller.inviaOffertaScambio(annuncio, this.listaOggettiOfferti)) {
		        case 0: // offerta di scambio normale inviata correttamente
	                currentStage.close();
		            sceneManager.showPopupAlert(containerOfferte, "Richiesta inviata!",  "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
		        break;
	
		        case 1: // offerta di scambio personalizzata inviata correttamente
		        	currentStage.close();
		            sceneManager.showPopupAlert(containerOfferte, "Richiesta personalizzata inviata!",  "La richiesta di " + annuncio.getTipologia() + " con gli oggetti inseriti è stata inviata con successo.");
		        break;
		        
		        case -1: // offerta duplicata
		            sceneManager.showPopupError(containerOfferte, "Offerta già esistente!", "Hai già effettuato un'offerta per questo annuncio.");
		        break;
		        
	            case -2:
	                sceneManager.showPopupError(containerOfferte, "Oggetti offerti duplicati!","Hai inserito degli oggetti duplicati nella lista degli oggetti da offrire per lo scambio.");
	                listaOggettiOfferti.clear();
	            break;
    	    }
    	}
    }
    
    @FXML
    public void checkControfferta(MouseEvent e) {
    	
    	Annuncio annuncio = controller.getAnnuncioSelezionato();
    	
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
        
        switch(controller.checkControfferta(annuncio, stringaPrezzo)) {
            case 0:
                currentStage.close();
                sceneManager.showPopupAlert(containerOfferte, "Controfferta inviata!", "La controfferta è stata inviata con successo.");
            break;

            case 1:
                campoPrezzoIntero.clear();
                campoPrezzoDecimale.clear();
                sceneManager.showPopupError(containerOfferte, "Controfferta non valida!", "La controfferta deve avere max 3 cifre per la parte intera e max 2 cifre per quella decimale e (0 < controfferta < prezzo).");
            break;

            case -1:
                sceneManager.showPopupError(containerOfferte, "Offerta già esistente!","Hai già effettuato un'offerta per questo annuncio.");
            break;
            
            default:
                sceneManager.showPopupError(containerOfferte, "Errore sconosciuto", "Si è verificato un errore imprevisto. Riprova.");
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
            immagineCaricata.setImage(image);
            controller.setFileOggettoAlternativo(selectedFile);
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
