package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;

public class PopupOfferteBoundary {

    private Controller controller;
    private ProdottiBoundary prodottiBoundary;
    @FXML private Label titoloAnnuncio;
    @FXML private Label descrizioneAnnuncio;
    @FXML private Label prezzoAnnuncio;
    @FXML private Label usernameAnnuncio;
    @FXML private Label disponibilitàAnnuncio;
    @FXML private Label categoriaAnnuncio;
    @FXML private ImageView immagineAnnuncio;
    @FXML private ImageView immagineProfilo;
    @FXML private Button buttonOfferta;
    @FXML private Button sendButton;
    @FXML private ImageView sendImage;
    @FXML private AnchorPane PaneControfferta;
    @FXML private Button controffertaButton;
    @FXML private ImageView backImage;
    @FXML private Button backButton;
    
    private Annuncio annuncio;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void setProdottiBoundary(ProdottiBoundary prodottiBoundary) {
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
	                break;
	                
	                case "Vendita":
						buttonOfferta.setText("Acquista");
					break;
					
					case "Regalo":
						buttonOfferta.setText("Richiedi");
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
            prodottiBoundary.CostruisciCatalogoProdotti(this.controller.getStudente());
            prodottiBoundary.setFiltri();
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void MostraControfferta()
    {
    	if(!PaneControfferta.isVisible())
    	{	
    		PaneControfferta.setVisible(true);
    		backImage.setVisible(true);
    	    backButton.setVisible(true);
    	    controffertaButton.setVisible(false);

    	    buttonOfferta.setVisible(false);
    	    sendButton.setVisible(true);
    	    sendImage.setVisible(true);
    	}
    	else 
    	{
    		PaneControfferta.setVisible(false);
    		backImage.setVisible(false);
    	    backButton.setVisible(false);
    	    controffertaButton.setVisible(true);
    	    
    	    buttonOfferta.setVisible(true);
    	    sendButton.setVisible(false);
    	    sendImage.setVisible(false);
    	}
    }    
   
}
