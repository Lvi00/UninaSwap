package application.boundary;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    @FXML
    public void VisualizzaDati(MouseEvent e) {
    	try {
    		File destinationDir = new File(System.getProperty("user.dir"), "src/application/IMG/uploads");
    		if (!destinationDir.exists()) destinationDir.mkdirs();
    		File destinationFile = new File(destinationDir, this.fileSelezionato.getName());
    		Files.copy(this.fileSelezionato.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    	catch (IOException ex) {
            ex.printStackTrace();
        }
    	
        // Titolo e descrizione
        String titolo = campoTitoloAnnuncio.getText();
        String descrizione = campoDescrizioneAnnuncio.getText();

        // Categoria
        String categoria = campoCategoriaOggetto.getValue().name();

        // Indirizzo
        String indirizzoTop = campoIndirizzo1.getValue().name();
        String indirizzo2 = campoIndirizzo2.getText();
        String indirizzo3 = campoIndirizzo3.getText();
        String indirizzo4 = campoCap.getText();
        String indirizzoCompleto = indirizzoTop + " " + indirizzo2 + " " + indirizzo3 + " " + indirizzo4;

        // Disponibilità oraria
        String inizio = inizioDisponibilità.getValue();
        String fine = fineDisponibilità.getValue();

        // Giorni selezionati
        ArrayList<String> giorniSelezionati = new ArrayList<String>();
        if (checklun.isSelected()) giorniSelezionati.add("Lunedì");
        if (checkmar.isSelected()) giorniSelezionati.add("Martedì");
        if (checkmer.isSelected()) giorniSelezionati.add("Mercoledì");
        if (checkgio.isSelected()) giorniSelezionati.add("Giovedì");
        if (checkven.isSelected()) giorniSelezionati.add("Venerdì");

        // Tipologia annuncio
        String tipologia;
        if (campoVendita.isSelected()) tipologia = "Vendita";
        else if (campoRegalo.isSelected()) tipologia = "Regalo";
        else tipologia = "Scambio";

        // Stampa dei dati in console
        System.out.println("---- Dati Annuncio ----");
        System.out.println("Titolo: " + titolo);
        System.out.println("Descrizione: " + descrizione);
        System.out.println("Categoria: " + categoria);
        System.out.println("Indirizzo: " + indirizzoCompleto);
        System.out.println("Disponibilità: " + inizio + " - " + fine);
        System.out.println("Giorni selezionati: " + giorniSelezionati);
        System.out.println("Tipologia: " + tipologia);
    }
}
