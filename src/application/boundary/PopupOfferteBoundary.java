package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Oggetto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ProdottiBoundary prodottiBoundary;
    @FXML private HBox containerOfferte;
    @FXML private Label titoloAnnuncio;
    @FXML private Label descrizioneAnnuncio;
    @FXML private Label prezzoAnnuncio;
    @FXML private Label usernameAnnuncio;
    @FXML private Label disponibilitàAnnuncio;
    @FXML private Label categoriaAnnuncio;
    @FXML private ImageView immagineAnnuncio;
    @FXML private ImageView immagineProfilo;
    @FXML private AnchorPane PaneOfferteVendita;
    @FXML private AnchorPane PaneOfferteScambio;
    @FXML private AnchorPane PaneOfferteRegalo;
    //Vendita
    @FXML private Button buttonOfferta;
    @FXML private Button aggiungiOggetto;
    @FXML private Button sendButton;
    @FXML private ImageView sendImage;
    @FXML private Button controffertaButton;
    @FXML private ImageView backImage;
    @FXML private Button backButton;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    //Scambio
    @FXML private ImageView imageScambioButtonImage;
    @FXML private Button imageScambioButton;
    @FXML private StackPane imageScambio;
    @FXML private ImageView immagineCaricata;
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private TextArea campoDescrizioneAnnuncioScambio;
    //Regalo
    @FXML private TextArea campoDescrizioneAnnuncioRegalo;
    
    private File fileSelezionato = null;
	private ArrayList<Oggetto> listaOggettiOfferti = new ArrayList<Oggetto>();
    
    private Annuncio annuncio;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setProdottiBoundary(ProdottiBoundary prodottiBoundary) {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        this.prodottiBoundary = prodottiBoundary;
    }
    
    public void setAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;
        costruisciPopup();
    }

    private void costruisciPopup() {
        if (annuncio != null) {
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
	                	buttonOfferta.setText("Scambio");
	                	controffertaButton.setVisible(false);
	                	PaneOfferteScambio.setVisible(true);
	                	aggiungiOggetto.setVisible(true);
	                	buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
	                break;
	                
	                case "Vendita":
						buttonOfferta.setText("Acquista");
						controffertaButton.setVisible(true);
	                	aggiungiOggetto.setVisible(false);
	                	//Manca PaneOfferteVendita.setVisible(true); perche non serve in quanto abbiamo MostraControfferta
	                	buttonOfferta.onMouseClickedProperty().set(e -> AcquistaOggetto(e));
					break;
					
					case "Regalo":
						buttonOfferta.setText("Richiedi");
	                	controffertaButton.setVisible(false);
	                	PaneOfferteRegalo.setVisible(true);
	                	aggiungiOggetto.setVisible(false);
	                	buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
					break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void AcquistaOggetto(MouseEvent e) {
        controller.AcquistaOggetto(this.annuncio);
        
        if (prodottiBoundary != null) {
            prodottiBoundary.CostruisciCatalogoProdotti(controller.getStudente());
        }

        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        currentStage.close();
        
        ShowPopupAlert("Acquisto effettuato!", "Il prodotto è stato acquistato con successo.");
    }
    
    @FXML
    public void aggiungiOggettoDaScambiare(MouseEvent e) {
    	
        String descrizione = campoDescrizioneAnnuncioScambio.getText();
        if (descrizione == null || descrizione.trim().isEmpty()) {
            descrizione = "Assente";
        } else {
            descrizione = descrizione.trim();
        }

        // Categoria selezionata
        Categorie categoriaSelezionata = campoCategoriaOggetto.getValue();

        // Percorso immagine selezionata
        String percorsoImmagine = null;
        if (fileSelezionato != null) {
            percorsoImmagine = fileSelezionato.getAbsolutePath();
        }

        // Creo nuovo oggetto
        Oggetto nuovoOggetto = new Oggetto(percorsoImmagine, categoriaSelezionata.toString(), descrizione, controller.getStudente());

        // Aggiungo l'oggetto all'arraylist
        this.listaOggettiOfferti.add(nuovoOggetto);

        // Pulisco i campi del form
        campoDescrizioneAnnuncioScambio.clear();
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        immagineCaricata.setImage(new Image(getClass().getResource("../IMG/immaginiProgramma/no_image.png").toExternalForm()));
        fileSelezionato = null;

        // Stampa della lista aggiornata per controllo
        System.out.println("Lista oggetti attuali:");
        for (Oggetto obj : listaOggettiOfferti) {
            System.out.println("Categoria: " + obj.getCategoria() +
                               ", Descrizione: " + obj.getDescrizione() +
                               ", Immagine: " + obj.getImmagineOggetto());
        }
        
        // Messaggio di conferma
        ShowPopupAlert("Oggetto aggiunto!", "L'oggetto è stato aggiunto alla lista degli oggetti da offrire.");
    }
    
    public void inviaOfferta(MouseEvent e) { 
    	Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    	
    	if(this.annuncio.getTipologia().equals("Regalo")) {
    	    String motivazione = campoDescrizioneAnnuncioRegalo.getText();

    	    // Se null o solo spazi assegna "Assente"
    	    if (motivazione == null || motivazione.trim().isEmpty()) 
    	    {
    	        motivazione = "Assente";
    	    } 
    	    else 
    	    {
    	        // Rimuove spazi iniziali e finali
    	        motivazione = motivazione.trim();
    	    }

    	    System.out.println(motivazione);

    	    switch(controller.inviaOfferta(annuncio, motivazione)) {
    	        case 0: // offerta inviata correttamente
                    currentStage.close();
    	            ShowPopupAlert("Richiesta inviata!",  "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
    	        break;

    	        case 1: // errore generico
    	            ShowPopupError("Errore nella richiesta", "La richiesta di " + annuncio.getTipologia() + " non è stata inviata a causa di un errore nei dati inseriti.");
    	        break;

    	        case 2: // offerta duplicata
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
            case 0: // tutto ok
                currentStage.close();
                ShowPopupAlert("Controfferta inviata!", "La controfferta è stata inviata con successo.");
            break;

            case 1: // errore di prezzo/validazione
                campoPrezzoIntero.clear();
                campoPrezzoDecimale.clear();
                ShowPopupError("Controfferta non valida!", "La controfferta deve avere max 3 cifre per la parte intera e max 2 cifre per quella decimale e (0 < controfferta < prezzo).");
            break;

            case 2: // offerta duplicata
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
}
