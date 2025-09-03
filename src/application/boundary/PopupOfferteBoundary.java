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
	                	aggiungiOggetto.setVisible(true);
	                	PaneOfferteScambio.setVisible(true);
	                	buttonOfferta.onMouseClickedProperty().set(e -> inviaOfferta(e));
	                break;
	                
	                case "Vendita":
						buttonOfferta.setText("Acquista");
						controffertaButton.setVisible(true);
	                	aggiungiOggetto.setVisible(false);
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
        
        if(controller.checkControfferta(this.annuncio, stringaPrezzo) == 0){
            currentStage.close();
            ShowPopupAlert("Controfferta inviata!", "La controfferta è stato inviata con successo.");
        }
        
        else {
	    	  campoPrezzoIntero.clear();
	          campoPrezzoDecimale.clear();
	          ShowPopupError("Controfferta non valida!", "La controfferta deve avere max 3 cifre per la parte intera e max 2 cifre per quella decimale e (0 < controfferta < prezzo).");
	    }
    }
    
    @FXML
    public void aggiungiOggettoDaScambiare(MouseEvent e) {
    	System.out.println("Oggetto aggiunto");
    }
    
    public void inviaOfferta(MouseEvent e) {   	
        if (controller.inviaOfferta(annuncio) == 1) {
            ShowPopupError("Errore nella richiesta", "La richiesta di " + annuncio.getTipologia() + " non è stata inviata a causa di un errore nei dati inseriti.");
        } else {
            ShowPopupAlert("Richiesta inviata!", "La richiesta di " + annuncio.getTipologia() + " è stata inviata con successo.");
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
