package application.boundary;

import java.io.File;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Offerta;
import application.entity.Oggetto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
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
    @FXML private Button inviaDatiOfferta;
    @FXML private GridPane gridOggettiOfferti;
    private Controller controller;

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

                stage.setWidth(450);
                stage.setHeight(175);
                break;

            case "Scambio":
                paneOffertaScambio.setVisible(true);
                paneOffertaScambio.setManaged(true);

                ArrayList<Oggetto> listaOggetti = controller.getOggettiOffertiByOfferta(o);

                gridOggettiOfferti.getChildren().clear(); // pulisce card precedenti
                gridOggettiOfferti.getColumnConstraints().clear(); // reset colonne

                int numeroCol = listaOggetti.size();
                int larghezzaCard = 300; // larghezza stimata per ogni card
                int altezzaCard = 300;   // altezza stimata per ogni card

                gridOggettiOfferti.setHgap(35);  // spazio tra le card
                gridOggettiOfferti.setVgap(10);
                gridOggettiOfferti.setAlignment(Pos.CENTER); // centra tutte le card nello stage

                int colonna = 0;
                for (Oggetto og : listaOggetti) {
                    VBox card = creaCardOggettoOfferto(og);
                    card.getStyleClass().add("card-annuncio");

                    gridOggettiOfferti.add(card, colonna, 0);
                    GridPane.setFillWidth(card, false); // evita che la card si allarghi

                    // colonna cresce automaticamente (opzionale)
                    ColumnConstraints col = new ColumnConstraints();
                    col.setHgrow(Priority.NEVER); // le card restano della larghezza prefissata
                    gridOggettiOfferti.getColumnConstraints().add(col);

                    colonna++;
                }

                // imposta dimensione stage in base al numero di card
                double larghezzaTotaleCard = (larghezzaCard * numeroCol) /1.25;
                stage.setWidth(larghezzaTotaleCard); // aggiungi margine minimo
                stage.setHeight(altezzaCard); // altezza fissa o regolabile
                break;

            case "Regalo":
                paneOffertaRegalo.setVisible(true);
                paneOffertaRegalo.setManaged(true);

                if (o.getMotivazione().equals("Assente"))
                    messaggioMotivazionale.setText("");
                else
                    messaggioMotivazionale.setText(o.getMotivazione());

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
    
    private VBox creaCardOggettoOfferto(Oggetto o) {
        // Contenitore “card invisibile” per ogni oggetto
        VBox box = new VBox(8); // spazio tra immagine e testo
        box.setAlignment(Pos.CENTER); // centra tutto
        box.setPadding(new Insets(10)); // piccolo padding interno

        // immagine
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
            Rectangle clip = new Rectangle(150, 100);
            imageView.setClip(clip);
            imageView.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // testo
        Label categoria = new Label(o.getCategoria());
        categoria.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        categoria.setWrapText(true);
        categoria.setAlignment(Pos.CENTER);

        Label descrizione = new Label(o.getDescrizione());
        descrizione.setStyle("-fx-text-fill: #555;");
        descrizione.setWrapText(true);
        descrizione.setAlignment(Pos.CENTER);

        // aggiungo immagine + testo al box
        box.getChildren().addAll(imageView, categoria, descrizione);

        // se vuoi puoi aggiungere un bordo leggero per distinguere visivamente ogni “card invisibile”
        box.setStyle(
            "-fx-border-color: transparent;" + // oppure #ccc per leggero bordo
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );

        return box;
    }

}
