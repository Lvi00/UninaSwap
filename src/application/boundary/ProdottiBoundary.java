package application.boundary;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Studente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProdottiBoundary {

    private Controller controller;

    @FXML private HBox containerCatalogoProdotti;
    @FXML private Label usernameDashboard;
    @FXML private Label labelAnnunciTrovati;
    @FXML private GridPane gridProdotti;
    @FXML private AnchorPane contentPane;
    @FXML private ImageView immagineNav;
    @FXML private TextField campoRicerca;    
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private ChoiceBox<Tipologia> campoTipologia;
    
    private String keyword = null;
    private String categoria = null;
    private String tipologia = null;

    enum Categorie {
    	Nessuno,
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Cultura,
        Musica
    }
    
    enum Tipologia {
    	Nessuno,
        Vendita,
        Regalo,
        Scambio
    }
    
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
        
    	//Prende l'oggetto cliccato
        Object source = e.getSource();
        
        //Controlla se è una Label
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
            
            System.out.println("Hai cliccato su: " + testo);
       
            switch (testo) {
	            case "Crea annuncio":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
		    	        Parent root = loader.load();
		                CreaAnnuncioBoundary creaCtrl = loader.getController();
		                creaCtrl.setController(this.controller);
		                creaCtrl.setUsername(this.controller.getStudente().getUsername());
		                creaCtrl.setCampiForm();
		                creaCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
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
	            
	            case "Offerte":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offerte.fxml"));
		    	        Parent root = loader.load();
		                OfferteBoundary offerteCtrl = loader.getController();
		                offerteCtrl.setController(this.controller);
		                offerteCtrl.setUsername(this.controller.getStudente().getUsername());
		                offerteCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
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
	            
	            case "Prodotti":
	            	System.out.println("Sei già nella pagina Prodotti.");
	            break;
	            
	            case "I tuoi annunci":
		            try {
		    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnnunciStudente.fxml"));
		    	        Parent root = loader.load();
		                AnnunciStudenteBoundary annunciCtrl = loader.getController();
		                annunciCtrl.setController(this.controller);
		                annunciCtrl.CostruisciProdottiUtente(this.controller.getStudente());
		                annunciCtrl.setUsername(this.controller.getStudente().getUsername());
		                annunciCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - I tuoi annunci");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;
	            
	            default:
	            	//Profilo utente
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
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;
            }
        }
    }
    
    public void CostruisciCatalogoProdotti(Studente s) {

        ArrayList<Annuncio> annunci = controller.getInfoAnnunci(s.getMatricola());
        	
    	if(annunci.isEmpty()) labelAnnunciTrovati.setText("Nessun annuncio trovato");
    	else if(annunci.size() == 1) labelAnnunciTrovati.setText(annunci.size() + " annuncio trovato");
    	else labelAnnunciTrovati.setText(annunci.size() + " annunci trovati");
    	
        pulisciCatalogo();
        
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
    }

    private VBox creaCardAnnuncio(Annuncio a) {
        VBox box = new VBox();
        box.setPrefWidth(230);
        box.setPrefHeight(300);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");

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
            imageView.setViewport(new Rectangle2D(0, 0, img.getWidth(), img.getHeight()));
            imageView.getStyleClass().add("immagineCard");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + a.getOggetto().getImmagineOggetto());
        }

        box.getChildren().add(imageView);

        Label titolo = new Label(a.getTitoloAnnuncio());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titolo.setWrapText(true);
        titolo.setMaxWidth(200);
        titolo.setAlignment(Pos.CENTER);

        Label prezzo = new Label(String.format("\u20AC %.2f", a.getPrezzo()));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 14;");

        Label tipo = new Label(a.getOggetto().getCategoria() +" - "+ a.getTipologia());
        tipo.setStyle("-fx-text-fill: gray;");

        Label venditore = new Label("Pubblicato da " + a.getOggetto().getStudente().getUsername());
        venditore.setStyle("-fx-text-fill: #153464;");
        
        Label disponibilità = new Label("Disponibile il " + (a.getGiorni() != null ? a.getGiorni() : "N/D")
        + "\ndalle " + a.getFasciaOrariaInizio() + " alle " + a.getFasciaOrariaFine());

        Button button = new Button("Scopri");
        button.getStyleClass().add("tasto-secondario");
        button.setPrefWidth(130);
        VBox.setMargin(button, new Insets(4, 0, 4, 0));
        button.setOnMouseClicked(e -> showPopupOfferte(e, a));

        box.getChildren().addAll(titolo, prezzo, tipo, venditore, disponibilità, button);

        return box;
    }
    
    public void showPopupOfferte(MouseEvent e, Annuncio annuncio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOfferte.fxml"));
            Parent root = loader.load();

            PopupOfferteBoundary popupCtrl = loader.getController();
            popupCtrl.setController(this.controller);
            popupCtrl.setProdottiBoundary(this);
            popupCtrl.setAnnuncio(annuncio);                   

            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("UninaSwap - " +annuncio.getTipologia());
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
    
    @FXML
    public void setFiltri() {
        campoCategoriaOggetto.setItems(FXCollections.observableArrayList(Categorie.values()));
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        
        campoTipologia.setItems(FXCollections.observableArrayList(Tipologia.values()));
        campoTipologia.getSelectionModel().selectFirst();
    }
    
    public void pulisciCatalogo() {
		gridProdotti.getChildren().clear();
	}
    
    @FXML
    public void getInfoFiltri() {
        String newKeyword = campoRicerca.getText();
        String newCategoria = campoCategoriaOggetto.getValue() != null ? campoCategoriaOggetto.getValue().name() : "Nessuno";
        String newTipologia = campoTipologia.getValue() != null ? campoTipologia.getValue().name() : "Nessuno";

        // Controllo campi vuoti
        if ((newKeyword == null || newKeyword.isEmpty()) && newCategoria.equals("Nessuno") && newTipologia.equals("Nessuno")) {
            return;
        }

        // Controllo se i filtri sono uguali ai precedenti
        if (newKeyword.equals(this.keyword) && newCategoria.equals(this.categoria) && newTipologia.equals(this.tipologia)) {
            return;
        }

        // Salvo i nuovi filtri
        this.keyword = newKeyword;
        this.categoria = newCategoria;
        this.tipologia = newTipologia;

        pulisciCatalogo();

        ArrayList<Annuncio> annunciFiltrati = controller.getAnnunciByFiltri(keyword, categoria, tipologia);
        
    	if(annunciFiltrati.isEmpty()) labelAnnunciTrovati.setText("Nessun annuncio trovato");
    	else if(annunciFiltrati.size() == 1) labelAnnunciTrovati.setText(annunciFiltrati.size() + " annuncio trovato");
    	else labelAnnunciTrovati.setText(annunciFiltrati.size() + " annunci trovati");

        int column = 0;
        int row = 0;

        for (Annuncio a : annunciFiltrati) {
            VBox card = creaCardAnnuncio(a);
            gridProdotti.add(card, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }

    }
    
    @FXML
    public void resetFiltri() {    	
        // Controllo se i filtri sono già allo stato di default
        String categoriaCorrente = this.categoria != null ? this.categoria : "Nessuno";
        String tipologiaCorrente = this.tipologia != null ? this.tipologia : "Nessuno";

        if ((this.keyword == null || this.keyword.isEmpty()) && categoriaCorrente.equals("Nessuno") && tipologiaCorrente.equals("Nessuno")) {
            return;
        }

        // Reset dei campi UI
        campoRicerca.clear();
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        campoTipologia.getSelectionModel().selectFirst();

        // Reset dei valori interni
        this.keyword = "";
        this.categoria = "Nessuno";
        this.tipologia = "Nessuno";

        // Ripristino catalogo completo
        gridProdotti.getChildren().clear();
        CostruisciCatalogoProdotti(this.controller.getStudente());

    }
}