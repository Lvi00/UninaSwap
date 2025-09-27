package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.boundary.CreaAnnuncioBoundary.Categorie;
import application.boundary.CreaAnnuncioBoundary.ParticellaToponomastica;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.Oggetto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PopupEditOffertaBoundary {

    @FXML private VBox GeneralOffersPane;
    @FXML private VBox paneOffertaVendita;
    @FXML private VBox paneOffertaScambio;
    @FXML private VBox paneOffertaRegalo;
    @FXML private TextArea messaggioMotivazionale;
    @FXML private TextField campoPrezzoIntero;
    @FXML private TextField campoPrezzoDecimale;
    @FXML private Label labelOffertaScambio;
    @FXML private Label labelOffertaVendita;
    @FXML private Label labelOffertaRegalo;
    @FXML private Button inviaDatiOfferta;
    @FXML private GridPane gridOggettiOfferti;
    private Controller controller;
	private File fileSelezionato = null;

    enum Categorie {
        Abbigliamento,
        Informatica,
        Elettronica,
        Cancelleria,
        Cultura,
        Musica
    }
    
    
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void CostruisciPopupEdit(Offerta o, Stage stage) {

        // Nascondi tutti i pannelli inizialmente
        paneOffertaVendita.setVisible(false);
        paneOffertaVendita.setManaged(false);
        paneOffertaScambio.setVisible(false);
        paneOffertaScambio.setManaged(false);
        paneOffertaRegalo.setVisible(false);
        paneOffertaRegalo.setManaged(false);

        switch (o.getTipologia()) {
            case "Vendita":
                paneOffertaVendita.setVisible(true);
                paneOffertaVendita.setManaged(true);

                // Imposta prezzo
                double prezzo = o.getPrezzoOfferta();
                String prezzoString = String.format("%.2f", prezzo).replace(".", ",");
                String[] parti = prezzoString.split(",");
                campoPrezzoIntero.setText(parti[0]);
                campoPrezzoDecimale.setText(parti[1]);
                
                VBox.setMargin(labelOffertaVendita, new Insets(20, 0, 0, 0));

                stage.setWidth(450);
                stage.setHeight(175);
            break;

            case "Scambio":
                paneOffertaScambio.setVisible(true);
                paneOffertaScambio.setManaged(true);

                ArrayList<Oggetto> listaOggetti = controller.getOggettiOffertiByOfferta(o);
                controller.SetOggettiOfferti(listaOggetti);
                gridOggettiOfferti.getChildren().clear();
                gridOggettiOfferti.getColumnConstraints().clear();

                int numeroCol = listaOggetti.size();
                int larghezzaCard = 300;
                int altezzaCard = 400;

                gridOggettiOfferti.setHgap(35);
                gridOggettiOfferti.setVgap(10);
                gridOggettiOfferti.setAlignment(Pos.CENTER);
                VBox.setMargin(gridOggettiOfferti, new Insets(10));
                
                int colonna = 0;
                for (Oggetto oggetto : listaOggetti) {
                	StackPane card = creaCardOggettoOfferto(oggetto);
                    card.getStyleClass().add("card-annuncio");

                    gridOggettiOfferti.add(card, colonna, 0);
                    GridPane.setFillWidth(card, false);

                    // colonna cresce automaticamente
                    ColumnConstraints col = new ColumnConstraints();
                    col.setHgrow(Priority.NEVER);
                    gridOggettiOfferti.getColumnConstraints().add(col);

                    colonna++;
                }

                double larghezzaTotaleCard = (larghezzaCard * numeroCol) / 1.2;
                stage.setWidth(larghezzaTotaleCard);
                stage.setHeight(altezzaCard);
            break;

            case "Regalo":
                paneOffertaRegalo.setVisible(true);
                paneOffertaRegalo.setManaged(true);

                if (o.getMotivazione().equals("Assente")) messaggioMotivazionale.setText("");
                else messaggioMotivazionale.setText(o.getMotivazione());
                
                VBox.setMargin(labelOffertaRegalo, new Insets(20, 0, 0, 0));
                stage.setWidth(450);
                stage.setHeight(175);
            break;
        }

        inviaDatiOfferta.setOnAction(event -> prelevaDatiOfferta(o));
    }

    private void prelevaDatiOfferta(Offerta o) {
        switch(o.getTipologia()) {
            case "Vendita":
                String prezzoIntero = campoPrezzoIntero.getText().trim();
                String prezzoDecimale = campoPrezzoDecimale.getText().trim();
                controller.editOffertaVendita(o, prezzoIntero, prezzoDecimale);
            break;

            case "Scambio":
                // logica per scambio, se necessario
            break;

            case "Regalo":
                String motivazione = messaggioMotivazionale.getText().trim();
                controller.editOffertaRegalo(o, motivazione);
            break;
        }
    }
    
    private StackPane creaCardOggettoOfferto(Oggetto o) {
        VBox content = new VBox(8);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(10));

        ImageView localImageView = new ImageView();
        try {
            String path = o.getImmagineOggetto();
            Image img;
            File file = new File(path);
            if (file.exists()) {
                img = new Image(file.toURI().toString());
            } else {
                img = new Image(getClass().getResource(path).toExternalForm());
            }
            localImageView.setFitWidth(150);
            localImageView.setFitHeight(100);
            localImageView.setPreserveRatio(false);
            Rectangle clip = new Rectangle(150, 100);
            localImageView.setClip(clip);
            localImageView.setImage(img);
            localImageView.setStyle("-fx-cursor: hand;");
            //localImageView.setOnMouseClicked(event -> showFileChooser(event, o, localImageView));
        } catch (Exception e) { e.printStackTrace(); }
        
        Label categoria = new Label(o.getCategoria());
        categoria.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        categoria.setVisible(true);
        categoria.setStyle(
        	    "-fx-font-size: 14px; " +
        	    "-fx-font-weight: bold; " +
        	    "-fx-background-color: #f4f4f4; " +
        	    "-fx-border-color: #bdbdbd; " +
        	    "-fx-border-radius: 5; " +
        	    "-fx-background-radius: 5; " +
        	    "-fx-padding: 6 10; " +
        	    "-fx-pref-width: 150;"
        	);
        
        ChoiceBox<Categorie> choiceCategoria = new ChoiceBox<>();
        choiceCategoria.getItems().addAll(Categorie.values());
        choiceCategoria.setValue(Categorie.valueOf(o.getCategoria()));
        choiceCategoria.setPrefWidth(150);
        choiceCategoria.setStyle("-fx-cursor: hand;");
        choiceCategoria.setVisible(false);
        choiceCategoria.setManaged(false);
        
        Label descrizione = new Label(o.getDescrizione());
        descrizione.setStyle("-fx-font-size: 12px;");
        descrizione.setWrapText(true);
        descrizione.setPrefWidth(150);
        descrizione.setVisible(true);
        descrizione.setStyle(
        	    "-fx-font-size: 12px; " +
        	    "-fx-background-color: #f4f4f4; " +
        	    "-fx-border-color: #bdbdbd; " +
        	    "-fx-border-radius: 5; " +
        	    "-fx-background-radius: 5; " +
        	    "-fx-padding: 6 10; " +
        	    "-fx-pref-width: 150; " +
        	    "-fx-pref-height: 220;" +
        	    "-fx-alignment: top-left;"
        	);
        
        TextArea textDescrizione = new TextArea();
        textDescrizione.setText(o.getDescrizione());
        textDescrizione.setWrapText(true);
        textDescrizione.setPrefRowCount(3);
        textDescrizione.setPrefWidth(150);
        textDescrizione.setStyle("-fx-padding: -10");
        textDescrizione.setVisible(false);
        textDescrizione.setManaged(false);

        content.getChildren().addAll(localImageView, categoria, choiceCategoria, descrizione, textDescrizione);

        ImageView iconDelete = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/delete_card.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconDelete.setImage(imgIcon);
            iconDelete.setFitWidth(22);
            iconDelete.setFitHeight(22);
            iconDelete.setPreserveRatio(true);
        } catch (Exception e) { e.printStackTrace(); }

        Button buttonDelete = new Button();
        buttonDelete.getStyleClass().add("tasto-terziario");
        buttonDelete.setPrefSize(24, 24);
        buttonDelete.setStyle("-fx-cursor: hand;");
        buttonDelete.setGraphic(iconDelete);
        buttonDelete.setOnMouseClicked(event -> rimuoviOggettoOfferto(o));

        AnchorPane topRightLayer = new AnchorPane(buttonDelete);
        topRightLayer.setPickOnBounds(false); 
        AnchorPane.setTopAnchor(buttonDelete, -11.0);
        AnchorPane.setRightAnchor(buttonDelete, -11.0);
        
        ImageView iconEdit = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/edit.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconEdit.setImage(imgIcon);
            iconEdit.setFitWidth(22);
            iconEdit.setFitHeight(22);
            iconEdit.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Button buttonEdit = new Button();
        buttonEdit.getStyleClass().add("tasto-edit");
        buttonEdit.setPrefSize(24, 24);
        buttonEdit.setStyle("-fx-cursor: hand;");
        buttonEdit.setGraphic(iconEdit);
        buttonEdit.setOnMouseClicked(event -> showCampiModifica(
        	    categoria, choiceCategoria, descrizione, textDescrizione
        	));

        AnchorPane topLeftLayer = new AnchorPane(buttonEdit);
        topLeftLayer.setPickOnBounds(false); 
        AnchorPane.setTopAnchor(buttonEdit, -11.0);
        AnchorPane.setLeftAnchor(buttonEdit, -11.0);
        
        StackPane card = new StackPane(content, topRightLayer, topLeftLayer);
        card.getStyleClass().add("card-annuncio");

        return card;
    }

    
    private void rimuoviOggettoOfferto(Oggetto o) {
      
    }
    
    private void showCampiModifica(Label categoria, ChoiceBox<Categorie> choiceCategoria, Label descrizione, TextArea textDescrizione) {
        categoria.setVisible(false);
        descrizione.setVisible(false);
        categoria.setManaged(false);
        descrizione.setManaged(false);
        choiceCategoria.setVisible(true);
        textDescrizione.setVisible(true);
        choiceCategoria.setManaged(true);
        textDescrizione.setManaged(true);
    }
    
    public void showFileChooser(MouseEvent e, Oggetto o, ImageView targetImageView) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            targetImageView.setImage(image);   
            this.fileSelezionato = selectedFile;
        }
    }
}
