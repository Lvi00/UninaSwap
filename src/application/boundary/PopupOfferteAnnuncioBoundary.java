package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class PopupOfferteAnnuncioBoundary {
    @FXML private AnchorPane paneInfoOffertaScambio;
    @FXML private ScrollPane scrollPaneScambio;
    @FXML private GridPane gridPaneScambio;
    @FXML private AnchorPane paneInfoOffertaRegalo;
    @FXML private AnchorPane paneInfoOffertaVendita;
    @FXML private Label labelMessaggioMotivazionale;
    @FXML private Label labelControfferta;
    @FXML private Label labelOffertaScambio;

    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
    
    public void setPopupOffertaVendita(OffertaVendita offertaVendita) {
    	paneInfoOffertaVendita.setVisible(true);
        paneInfoOffertaRegalo.setVisible(false);
        paneInfoOffertaScambio.setVisible(false);
        String prezzoOfferta = String.format("%.2f", controller.getPrezzoOfferta(offertaVendita));
        labelControfferta.setText("Il prezzo offerto da " + controller.getUsername(controller.getStudente()) + " è di: €" + prezzoOfferta);
	}
    
    public void setPopupOffertaScambio(OffertaScambio offertaScambio) {
    	paneInfoOffertaScambio.setVisible(true);
        paneInfoOffertaVendita.setVisible(false);
        paneInfoOffertaRegalo.setVisible(false);
        labelOffertaScambio.setVisible(false);
        gridPaneScambio.getChildren().clear();

        ArrayList<Oggetto> listaOggetti = controller.getOggettiOffertiByOfferta(offertaScambio);

        if(listaOggetti.isEmpty()) {
            scrollPaneScambio.setVisible(false);
            labelOffertaScambio.setVisible(true);
        } else {
            scrollPaneScambio.setVisible(true);
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            gridPaneScambio.getColumnConstraints().add(col);

            int row = 0;
            for (Oggetto o : listaOggetti) {
                VBox card = creaCardOggettoOfferto(o);
                gridPaneScambio.add(card, 0, row);
                gridPaneScambio.setAlignment(Pos.TOP_CENTER);
                GridPane.setFillWidth(card, true);
                row++;
            }
        }
    }
    
    public void setPopupOffertaRegalo(OffertaRegalo offertaRegalo) {
    	paneInfoOffertaRegalo.setVisible(true);
        paneInfoOffertaVendita.setVisible(false);
        paneInfoOffertaScambio.setVisible(false);
        if(controller.getMotivazioneOfferta(offertaRegalo).equals("Assente"))
            labelMessaggioMotivazionale.setText("Non è stato inserito nessun messaggio motivazionale.");
        else
            labelMessaggioMotivazionale.setText(offertaRegalo.getMotivazione());
    }
    
    private VBox creaCardOggettoOfferto(Oggetto oggetto) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("card-annuncio");

        // immagine oggetto
        ImageView imageView = new ImageView();
        try {
            String path = controller.getImmagineOggetto(oggetto);
            Image img;
            File file = new File(path);

            if (file.exists()) {
                img = new Image(file.toURI().toString());
            } else {
                img = new Image(getClass().getResource(path).toExternalForm());
            }

            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(false);
            imageView.setCache(true);
            
            Rectangle clip = new Rectangle(150,100);
            imageView.setClip(clip);
            
            imageView.setImage(img);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + controller.getImmagineOggetto(oggetto));
        }

        // contenuto orizzontale: immagine + testo
        HBox content = new HBox(15);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMaxWidth(Double.MAX_VALUE);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setMaxWidth(Double.MAX_VALUE);

        Label categoria = new Label(controller.getCategoriaOggetto(oggetto));
        categoria.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        categoria.setWrapText(true);

        Label descrizione = new Label(controller.getDescrizioneOggetto(oggetto));
        descrizione.setStyle("-fx-text-fill: #555;");
        descrizione.setWrapText(true);
        descrizione.setMaxWidth(Double.MAX_VALUE);

        textBox.getChildren().addAll(categoria, descrizione);
        content.getChildren().addAll(imageView, textBox);

        card.getChildren().add(content);

        HBox.setHgrow(textBox, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);

        return card;
    }
}