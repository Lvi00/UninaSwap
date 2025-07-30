package application.boundary;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardBoundary {

    private Studente studente;
    private Controller controller = new Controller();

    @FXML private Label usernameDashboard;
    @FXML private GridPane gridProdotti;
    @FXML private TextField searchField;

    public void CostruisciDashboard(Studente s) {
        this.studente = s;
        usernameDashboard.setText(this.studente.getUsername());

        ArrayList<Annuncio> annunci = controller.getAnnunci();

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
        box.setSpacing(8);
        box.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPrefWidth(200);

        Label titolo = new Label(a.getTitoloAnnuncio());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label prezzo = new Label(String.format("\u20AC %.2f", a.getPrezzo()));
        prezzo.setStyle("-fx-text-fill: #153464;");

        Label tipo = new Label(a.getTipologia());
        tipo.setStyle("-fx-text-fill: gray;");

        Label fascia = new Label("Disponibile: " +
                a.getFasciaOrariaInizio().toLocalTime() + " - " +
                a.getFasciaOrariaFine().toLocalTime());

        box.getChildren().addAll(titolo, prezzo, tipo, fascia);

        return box;
    }

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
            stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.studente = null;
        }
    }

    public void selezioneSezione(MouseEvent e) {
        Node source = (Node) e.getSource();
        String selezione = ((Labeled) source).getText();

        switch (selezione) {
            case "Catalogo":
                System.out.println("Hai selezionato: Catalogo");
                break;
            case "Le tue vendite":
                System.out.println("Hai selezionato: Le tue vendite");
                break;
            case "I tuoi acquisti":
                System.out.println("Hai selezionato: I tuoi acquisti");
                break;
            case "I tuoi scambi":
                System.out.println("Hai selezionato: I tuoi scambi");
                break;
            case "Statistiche":
                System.out.println("Hai selezionato: Statistiche");
                break;
            default:
                System.out.println("Si è verificato un errore nella selezione della sezione.");
                break;
        }
    }
} 
