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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProdottiBoundary {

    private Controller controller;

    @FXML private Label usernameDashboard;
    @FXML private GridPane gridProdotti;
    @FXML private AnchorPane contentPane;
    @FXML private ImageView immagineNav;
    @FXML private TextField campoRicerca;    
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private ChoiceBox<Tipologia> campoTipologia;

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
    
    public boolean CostruisciCatalogoProdotti(Studente s) {

        ArrayList<Annuncio> annunci = controller.getInfoAnnunci(s.getMatricola());

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
        
        return true;
    }

    private VBox creaCardAnnuncio(Annuncio a) {
        VBox box = new VBox();
        box.setSpacing(8);
        box.setPrefWidth(230);
        box.getStyleClass().add("card-annuncio");

        ImageView imageView = new ImageView();
        
        try {
            String path = a.getOggetto().getImmagineOggetto();
            Image img;
            File file = new File(path);
            
            if (file.exists()) {
                // Se il file esiste nel filesystem
                img = new Image(file.toURI().toString(), 230, 150, true, true);
            } else {
                // Altrimenti prova a caricare da risorsa classpath
                img = new Image(getClass().getResource(path).toExternalForm(), 230, 150, true, true);
            }
            imageView.setFitWidth(230);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(false);
            imageView.setImage(img);
            imageView.getStyleClass().add("immagineCard");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + a.getOggetto().getImmagineOggetto());
        }

        // Aggiungi l'immagine al VBox
        box.getChildren().add(imageView);

        Label titolo = new Label(a.getTitoloAnnuncio());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label prezzo = new Label(String.format("\u20AC %.2f", a.getPrezzo()));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 14;");

        Label tipo = new Label(a.getOggetto().getCategoria() +" - "+ a.getTipologia());
        tipo.setStyle("-fx-text-fill: gray;");
        
        Label venditore = new Label("Pubblicata da " + a.getOggetto().getStudente().getUsername());
        venditore.setStyle("-fx-text-fill: #153464;");

        Label disponibilità =  new Label("Disponibile il " + (a.getGiorni() != null ? a.getGiorni() : "N/D")
        + "\ndalle " + a.getFasciaOrariaInizio() + " alle " +
        a.getFasciaOrariaFine());
        
        Button btn = new Button("Scopri");
        btn.getStyleClass().add("tasto-secondario");
        btn.setPrefWidth(150);
        btn.setOnMouseClicked(e -> showPopupOfferte(e, a));
        VBox.setMargin(btn, new Insets(5, 0, 5, 0));

        box.getChildren().addAll(titolo, prezzo, tipo, venditore, disponibilità, btn);

        return box;
    }
    
    public void showPopupOfferte(MouseEvent e, Annuncio a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOfferte.fxml"));
            Parent root = loader.load();

            PopupOfferteBoundary popupCtrl = loader.getController();
            popupCtrl.setController(this.controller);   
            popupCtrl.setAnnuncio(a);                   

            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("UninaSwap - Offerte");
            popupStage.getIcons().add(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
            popupStage.setResizable(false);
            mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
            popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

            popupStage.show();

            popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 50);

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
    
    @FXML
    public void getInfoFiltri(){ 	
    	String keyword = campoRicerca.getText();
	    String categoria = campoCategoriaOggetto.getValue() != null ? campoCategoriaOggetto.getValue().name() : "";
	    String tipologia = campoTipologia.getValue() != null ? campoTipologia.getValue().name() : "";

	    if ((keyword == null || keyword.isEmpty()) && categoria.equals("Nessuno") && tipologia.equals("Nessuno")) return;
    	
    	gridProdotti.getChildren().clear();
    	
    	ArrayList<Annuncio> annunci = controller.getAnnunciByFiltri(keyword, categoria, tipologia);
        
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
    
    @FXML
    public void resetFiltri() {
		campoRicerca.clear();
		campoCategoriaOggetto.getSelectionModel().selectFirst();
		campoTipologia.getSelectionModel().selectFirst();
		gridProdotti.getChildren().clear();
		CostruisciCatalogoProdotti(this.controller.getStudente());
	}
}
