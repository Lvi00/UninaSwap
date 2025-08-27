package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class PopupOfferteBoundary {

    private Controller controller;
    @FXML private Label titoloAnnuncio;
    @FXML private Label descrizioneAnnuncio;
    @FXML private Label prezzoAnnuncio;
    @FXML private Label tipoAnnuncio;
    @FXML private Label usernameAnnuncio;
    @FXML private Label disponibilitàAnnuncio;
    @FXML private Label categoriaAnnuncio;
    @FXML private ImageView immagineAnnuncio;
    @FXML private ImageView immagineProfilo;

    private Annuncio annuncio;

    public void setController(Controller controller) {
        this.controller = controller;
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

            tipoAnnuncio.setText(annuncio.getTipologia());
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
                immagineProfilo.setFitWidth(140);
                immagineProfilo.setFitHeight(140);
                immagineProfilo.setPreserveRatio(false);

                Circle clip1 = new Circle(70, 70, 70);
                immagineProfilo.setClip(clip1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
