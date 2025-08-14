package application.boundary;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    @FXML private Pane primaPagina;
    @FXML private Pane secondaPagina;
    @FXML private Pane campiPrezzo;
    @FXML private ImageView immagineCaricata;
    @FXML private Spinner<Integer> campoPrezzoIntero;
    @FXML private Spinner<Integer> campoPrezzoDecimale;
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setLabel(String s) {
        usernameDashboard.setText(s);
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
                        prodottiCtrl.setLabel(this.controller.getStudente().getUsername());
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
                    System.out.println("Selezione non valida: " + testo);
                break;
            }
        }
    }

    @FXML
    public void logout(MouseEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("UninaSwap - Login");
            stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    public void showProfile(MouseEvent e) {
        System.out.println("Profilo utente");
    }
    
    @FXML
    public void setCampiForm() {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        
        campoIndirizzo1.setItems(FXCollections.observableArrayList(ParticellaToponomastica.values()));
        campoIndirizzo1.getSelectionModel().selectFirst();
        
        inizioDisponibilità.setItems(FXCollections.observableArrayList());
        fineDisponibilità.setItems(FXCollections.observableArrayList());
        
        campoPrezzoIntero.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 0)
        );

        campoPrezzoDecimale.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0)
        );
        
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
    
    public void MostraPrimaPaginaForm(MouseEvent e) {
        campiPrezzo.setVisible(false);
        primaPagina.setVisible(true);
        secondaPagina.setVisible(false);
    }
    
    @FXML
    public void MostraSecondaPaginaForm(MouseEvent e) {
        primaPagina.setVisible(false);
        secondaPagina.setVisible(true);

        if (campoVendita.isSelected()) campiPrezzo.setVisible(true);
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
    public void VisualizzaDati(MouseEvent e) {
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
        if (campoVendita.isSelected()) tipologiaAnnuncio += "Vendita";
        else if (campoRegalo.isSelected()) tipologiaAnnuncio += "Regalo";
        else tipologiaAnnuncio += "Scambio";
        
        datiAnnuncio.add(tipologiaAnnuncio);
        
        switch(controller.checkDatiAnnuncio(datiAnnuncio, this.fileSelezionato)) {
	        case 0:
	        	System.out.println("Dati dell'annuncio validi.");
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
        		inizioDisponibilità.getSelectionModel().clearSelection();
        		fineDisponibilità.getSelectionModel().clearSelection();
        	break;
        	
        	case 4:
        		ShowPopupError("Giorni disponibilità non validi", "Specificare almeno un giorno della disponibilità.");
        	break;
        	
        	case 5:
        		ShowPopupError("Descrizione non valida", "La descrizione dell'annuncio non può essere vuota e deve contenere al massimo 255 caratteri.");
        		campoDescrizioneAnnuncio.clear();
        	break;
        	
        	case 6:
        		ShowPopupError("Sede non valida", "I campi dell'indirizzo della sede non possono essere vuoti.");
        		campoIndirizzo2.clear();
        		campoIndirizzo3.clear();
        		campoCap.clear();
        	break;
        	
        	case 7:
        		ShowPopupError("Tipologia non valida", "La tipologia dell'annuncio non può essere vuota.");
        	break;
        	
        	default:
        		System.out.println("Dati dell'annuncio non validi.");
        	break;
        }
    }
}
