package application.boundary;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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

public class ProdottiBoundary {
    @FXML private HBox containerCatalogoProdotti;
    @FXML private Label usernameDashboard;
    @FXML private Label labelAnnunciTrovati;
    @FXML private GridPane gridProdotti;
    @FXML private AnchorPane contentPane;
    @FXML private ImageView immagineNav;
    @FXML private TextField campoRicerca;    
    @FXML private ChoiceBox<Categorie> campoCategoriaOggetto;
    @FXML private ChoiceBox<Tipologia> campoTipologia;
    
    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
    
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
        Musica,
        Cibo,
        Altro,
    }
    
    enum Tipologia {
    	Nessuno,
        Vendita,
        Regalo,
        Scambio
    }

    public void costruisciPagina() {
		usernameDashboard.setText(controller.getUsername(controller.getStudente()));
		String immagineP = controller.getImmagineProfilo(controller.getStudente());
		try {
            File file = new File(immagineP);
            Image image;
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
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
    
    @FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        Label label = (Label) source;
        String nomePagina = label.getText();
    	sceneManager.SelezionaPagina(nomePagina, e);
    }
    
    public void CostruisciCatalogoProdotti() {

        ArrayList<Annuncio> annunciVisibili;
    	
    	if(controller.getAnnunciVisibili().isEmpty())
    	{
    		annunciVisibili = controller.getInfoAnnunci(controller.getStudente());
    		controller.setAnnunciVisibili(annunciVisibili);
    	}
    	else annunciVisibili = controller.getAnnunciVisibili();
        	
    	if(annunciVisibili.isEmpty()) labelAnnunciTrovati.setText("Nessun annuncio trovato");
    	else if(annunciVisibili.size() == 1) labelAnnunciTrovati.setText(annunciVisibili.size() + " annuncio trovato");
    	else labelAnnunciTrovati.setText(annunciVisibili.size() + " annunci trovati");
    	
        pulisciCatalogo();
        
        int column = 0;
        int row = 0;

        for (Annuncio a : annunciVisibili) {
            VBox card = creaCardAnnuncio(a);
            gridProdotti.add(card, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox creaCardAnnuncio(Annuncio annuncio) {
        VBox box = new VBox();
        box.setPrefWidth(230);
        box.setPrefHeight(300);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");

        ImageView imageView = new ImageView();
        try {
            String path = controller.getImmagineOggetto(controller.getOggettoAnnuncio(annuncio));
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
            System.out.println("Immagine non trovata: " + controller.getImmagineOggetto(controller.getOggettoAnnuncio(annuncio)));
        }

        box.getChildren().add(imageView);

        Label titolo = new Label(controller.getTitoloAnnuncio(annuncio));
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titolo.setWrapText(true);
        titolo.setMaxWidth(200);
        titolo.setAlignment(Pos.CENTER);

        Label prezzo = new Label(String.format("\u20AC %.2f", controller.getPrezzoAnnuncio(annuncio)));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 14;");

        Label tipo = new Label(controller.getCategoriaOggetto(controller.getOggettoAnnuncio(annuncio)) + " - " + controller.getTipologiaAnnuncio(annuncio));
        tipo.setStyle("-fx-text-fill: gray;");

        Label venditore = new Label("Pubblicato da " + controller.getUsername(controller.getStudenteOggetto(controller.getOggettoAnnuncio(annuncio))));
        venditore.setStyle("-fx-text-fill: #153464;");
        
        Label disponibilità = new Label("Disponibile il " + (controller.giorniDisponibilitaAnnuncio(annuncio) != null ? controller.giorniDisponibilitaAnnuncio(annuncio) : "N/D")
        + "\ndalle " + controller.getFasciaInizioAnnuncio(annuncio) + " alle " + controller.getFasciaFineAnnuncio(annuncio));

        Button button = new Button("Scopri");
        button.getStyleClass().add("tasto-secondario");
        button.setPrefWidth(130);
        VBox.setMargin(button, new Insets(4, 0, 4, 0));
        button.setOnMouseClicked(e -> showPopupOfferte(e, annuncio));

        box.getChildren().addAll(titolo, prezzo, tipo, venditore, disponibilità, button);

        return box;
    }
    
    public void showPopupOfferte(MouseEvent e, Annuncio annuncio) {
        sceneManager.showPopupOfferte(e, annuncio);
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
        String newKeyword = campoRicerca.getText().trim();
        String newCategoria = campoCategoriaOggetto.getValue() != null ? campoCategoriaOggetto.getValue().name() : "Nessuno";
        String newTipologia = campoTipologia.getValue() != null ? campoTipologia.getValue().name() : "Nessuno";

        if ((newKeyword == null || newKeyword.isEmpty()) && newCategoria.equals("Nessuno") && newTipologia.equals("Nessuno")) {
        	resetFiltri();
        }

        if (newKeyword.equals(this.keyword) && newCategoria.equals(this.categoria) && newTipologia.equals(this.tipologia)) {
            return;
        }

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
        campoRicerca.clear();
        campoCategoriaOggetto.getSelectionModel().selectFirst();
        campoTipologia.getSelectionModel().selectFirst();

        this.keyword = "";
        this.categoria = "Nessuno";
        this.tipologia = "Nessuno";

        gridProdotti.getChildren().clear();
        CostruisciCatalogoProdotti();
    }
}