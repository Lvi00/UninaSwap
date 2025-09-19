package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.Oggetto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class PopupInfoOfferta {

    private Controller controller;
    @FXML private AnchorPane paneInfoOffertaScambio;
    @FXML private ScrollPane scrollPaneScambio;
    @FXML private GridPane gridPaneScambio;
    @FXML private AnchorPane paneInfoOffertaRegalo;
    @FXML private AnchorPane paneInfoOffertaVendita;
    @FXML private Label labelMessaggioMotivazionale;
    @FXML private Label labelControfferta;
    @FXML private Label labelOffertaScambio;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setPopupInfoOfferta(Offerta offerta, Annuncio a) {
    	switch(offerta.getTipologia()) {
	    	case "Vendita":
	    		paneInfoOffertaVendita.setVisible(true);
	    		paneInfoOffertaRegalo.setVisible(false);
	    		paneInfoOffertaVendita.setVisible(true);
	    		String prezzoOfferta = String.format("%.2f", offerta.getPrezzoOfferta());
	    		labelControfferta.setText("Il prezzo offerto da " +  offerta.getStudente().getUsername() +  " è di: €" + prezzoOfferta);
	    	break;
	    	
	    	case "Scambio":
	    	    paneInfoOffertaScambio.setVisible(true);
	    	    paneInfoOffertaVendita.setVisible(false);
	    	    paneInfoOffertaRegalo.setVisible(false);
	    	    labelOffertaScambio.setVisible(false);
	    	    gridPaneScambio.getChildren().clear();  
	    	    
	    	    ArrayList<Oggetto> listaOggetti = controller.getOggettiOffertiByOfferta(offerta,a);
	    	    
	    	    if(listaOggetti.isEmpty()) {
	    	    	scrollPaneScambio.setVisible(false);
	    	    	labelOffertaScambio.setVisible(true);
	    	    }
	    	    
	    	    else {
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
    	    break;
	    	
	    	case "Regalo":
	    		paneInfoOffertaRegalo.setVisible(true);
	    		paneInfoOffertaVendita.setVisible(false);
	    		paneInfoOffertaRegalo.setVisible(true);
	    		if(offerta.getMotivazione().equals("Assente"))
	    			labelMessaggioMotivazionale.setText("Non è stato inserito nessun messaggio motivazionale.");
	    		else
	    			labelMessaggioMotivazionale.setText(offerta.getMotivazione());
	    	break;
    	}
    }
    
    private VBox creaCardOggettoOfferto(Oggetto o) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("card-annuncio");

        // immagine oggetto
        ImageView imageView = new ImageView();
        try {
            String path = o.getImmagineOggetto();
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + o.getImmagineOggetto());
        }

        // contenuto orizzontale: immagine + testo
        HBox content = new HBox(15);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMaxWidth(Double.MAX_VALUE);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setMaxWidth(Double.MAX_VALUE);

        Label categoria = new Label(o.getCategoria());
        categoria.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        categoria.setWrapText(true);

        Label descrizione = new Label(o.getDescrizione());
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