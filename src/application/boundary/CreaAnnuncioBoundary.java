package application.boundary;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.ArrayList;
import application.control.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreaAnnuncioBoundary {
	
	private File fileSelezionato = null;
	
    enum Categorie {
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Cultura,
        Musica
    }
    
    enum ParticellaToponomastica{
        Via,
        Viale,
        Largo,
        Piazza,
        Corso,
        Strada,
        Contrada,
        Traversa
    }
      
    private Controller controller;

    @FXML private Label usernameDashboard;
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private ChoiceBox<ParticellaToponomastica> campoIndirizzo1;
    @FXML private ChoiceBox<String> inizioDisponibilità;
    @FXML private ChoiceBox<String> fineDisponibilità;
    @FXML private TextField campoTitoloAnnuncio;
    @FXML private TextArea campoDescrizioneAnnuncio;
    @FXML private TextField campoIndirizzo2;
    @FXML private TextField campoIndirizzo3;
    @FXML private TextField campoCap;
    @FXML private CheckBox checklun;
    @FXML private CheckBox checkmar;
    @FXML private CheckBox checkmer;
    @FXML private CheckBox checkgio;
    @FXML private CheckBox checkven;
    @FXML private RadioButton campoVendita;
    @FXML private RadioButton campoRegalo;
    @FXML private RadioButton campoScambio;
    @FXML private Pane campiPrezzo;
    @FXML private ImageView immagineCaricata;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    @FXML private ImageView immagineNav;

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
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore caricando immagine: " + immagineP);
        }
    }
    
    @FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
       
            switch (testo) {
                case "Prodotti":
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                        Parent root = loader.load();
                        ProdottiBoundary prodottiCtrl = loader.getController();
                        prodottiCtrl.setController(this.controller);
                        prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
                        prodottiCtrl.setUsername(this.controller.getStudente().getUsername());
                        prodottiCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("UninaSwap - Prodotti");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                break;
                
                default:
	            	//Profilo utentes
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
		    	        stage.centerOnScreen();
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
  
    @FXML
    public void setCampiForm() {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        
        campoIndirizzo1.setItems(FXCollections.observableArrayList(ParticellaToponomastica.values()));
        campoIndirizzo1.getSelectionModel().selectFirst();
        
        inizioDisponibilità.setItems(FXCollections.observableArrayList());
        fineDisponibilità.setItems(FXCollections.observableArrayList());

        for (int i = 7; i <= 22; i++) {
            String ora = (i < 10 ? "0" + i : i) + ":00";
            inizioDisponibilità.getItems().add(ora);
            fineDisponibilità.getItems().add(ora);
        }
        inizioDisponibilità.getSelectionModel().selectFirst();
        fineDisponibilità.getSelectionModel().selectLast();
        
        ToggleGroup gruppoTipologia = new ToggleGroup();
        campoVendita.setToggleGroup(gruppoTipologia);
        campoRegalo.setToggleGroup(gruppoTipologia);
        campoScambio.setToggleGroup(gruppoTipologia);
        campoVendita.setSelected(true);
    }
    
    public void MostraPaneVendita(MouseEvent e) {
    	campiPrezzo.setVisible(true);
    }
    
    @FXML
    public void NascondiPaneVendita(MouseEvent e) {
    	campiPrezzo.setVisible(false);
    	campoPrezzoIntero.clear();
    	campoPrezzoDecimale.clear();
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
            //Carica sull'imaggine di defoult la nuova immagine
            immagineCaricata.setImage(image);
            this.fileSelezionato = selectedFile;
        }
    }
    
    private void ShowPopupError(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			
	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	        
			stage.setTitle("UninaSwap - " + title);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);		
		    stage.show();
			stage.getIcons().addAll(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

    @FXML
    public void InviaDati(MouseEvent e) {
    	
    	ArrayList<String> datiAnnuncio = new ArrayList<String>();
    	
    	datiAnnuncio.add(campoTitoloAnnuncio.getText());
    	datiAnnuncio.add(campoCategoriaOggetto.getValue().name());
        
    	datiAnnuncio.add(inizioDisponibilità.getValue());
    	datiAnnuncio.add(fineDisponibilità.getValue());
        
        String giorniDisponibilità = "";
        
        if (checklun.isSelected()) giorniDisponibilità += "Lun-";
        if (checkmar.isSelected()) giorniDisponibilità += "Mar-";
        if (checkmer.isSelected()) giorniDisponibilità += "Mer-";
        if (checkgio.isSelected()) giorniDisponibilità += "Gio-";
        if (checkven.isSelected()) giorniDisponibilità += "Ven-";
        
        datiAnnuncio.add(giorniDisponibilità);

        datiAnnuncio.add(campoDescrizioneAnnuncio.getText());
        
        datiAnnuncio.add(campoIndirizzo1.getValue().name());
        datiAnnuncio.add(campoIndirizzo2.getText());
        datiAnnuncio.add(campoIndirizzo3.getText());
        datiAnnuncio.add(campoCap.getText());

        String tipologiaAnnuncio = "";
        if (campoVendita.isSelected()) tipologiaAnnuncio = "Vendita";
        else if (campoRegalo.isSelected()) tipologiaAnnuncio = "Regalo";
        else tipologiaAnnuncio = "Scambio";
        
        datiAnnuncio.add(tipologiaAnnuncio);
        
        if(tipologiaAnnuncio == "Vendita")
        {
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
	        datiAnnuncio.add(stringaPrezzo);
	        
    	}
    
        switch(controller.checkDatiAnnuncio(datiAnnuncio, this.fileSelezionato, controller.getStudente())) {
	        case 0:
	        	ShowPopupAlert("Annuncio creato con successo!", "Dati inseriti con successo in UninaSwap");
	        	controller.copiaFileCaricato(this.fileSelezionato);
	        	inizializzaCampi();
	        	this.fileSelezionato = null;
	        break;
	        
        	case 1:
        		ShowPopupError("Titolo non valido", "Il titolo dell'annuncio non può essere vuoto e deve contenere al massimo 50 caratteri.");
        		campoTitoloAnnuncio.clear();
        	break;
        	
        	case 2:
        		ShowPopupError("Categoria non valida", "La cateogira dell'annuncio non può essere vuota.");
        	break;
        	
        	case 3:
        		ShowPopupError("Fascia oraria non valida", "La fascia oraria della disponibilità non può essere vuota.");
        	break;
        	
        	case 4:
        		ShowPopupError("Giorni disponibilità non validi", "Specificare almeno un giorno della disponibilità.");
        	break;
        	
        	case 5:
        		ShowPopupError("Descrizione non valida", "La descrizione dell'annuncio non può essere vuota e deve contenere al massimo 255 caratteri.");
        		campoDescrizioneAnnuncio.clear();
        	break;
        	
        	case 6:
        		ShowPopupError("Sede non valida", "I campi dell'indirizzo della sede sono possono essere vuoti e devono rispettare il formato corretto.");
        		campoIndirizzo2.clear();
        		campoIndirizzo3.clear();
        		campoCap.clear();
        	break;
        	
        	case 7:
        		ShowPopupError("Tipologia non valida", "La tipologia dell'annuncio non può essere vuota.");
        	break;
        	
         	case 8:
        		ShowPopupError("Prezzo non valido", "Il prezzo inserito non è valido");
        	    campoPrezzoIntero.clear();
        	    campoPrezzoDecimale.clear();
			break;
			
        	case 9:
        		ShowPopupError("Immagine non valida", "L'immagine inserita non è valida oppure non ha un nome valido.");
				this.fileSelezionato = null;
			break;
        	
        	default:
        		System.out.println("Dati dell'annuncio non validi.");
        	break;
        }
    }
    
    private void ShowPopupAlert(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupAlert.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
			stage.setTitle("UninaSwap - " + title);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);		
		    stage.show();
			stage.getIcons().addAll(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    
    private void inizializzaCampi() {
    	
    	campoTitoloAnnuncio.clear();
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        
        inizioDisponibilità.getSelectionModel().selectFirst();
        fineDisponibilità.getSelectionModel().selectLast();
        
    	checklun.setSelected(false);
    	checkmar.setSelected(false);
    	checkmer.setSelected(false);
    	checkgio.setSelected(false);
    	checkven.setSelected(false);

    	campoDescrizioneAnnuncio.clear();

    	campoIndirizzo1.getSelectionModel().selectFirst();
    	campoIndirizzo2.clear();
    	campoIndirizzo3.clear();
    	campoCap.clear();
    	
    	campoVendita.setSelected(true);
    	campiPrezzo.setVisible(true);
    	campoPrezzoIntero.clear();
    	campoPrezzoDecimale.clear();
    	
    	immagineCaricata.setImage(new Image(getClass().getResource("..\\IMG\\immaginiProgramma\\no_image.png").toExternalForm()));
    }
}
