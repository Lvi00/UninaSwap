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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProdottiBoundary {

    private Controller controller;

    @FXML private Label usernameDashboard;
    @FXML private GridPane gridProdotti;
    @FXML private TextField searchField;
    @FXML private AnchorPane contentPane;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLabel(String s) {
        usernameDashboard.setText(s);
    }
    
    public void SelezionaPagina(MouseEvent e) {
        
    	//Prende l'oggetto cliccato
        Object source = e.getSource();
        
        //Controlla se è una Label
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
       
            switch (testo) {
            case "Crea annuncio":
                try {
        	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
        	        Parent root = loader.load();
        	        
                    CreaAnnuncioBoundary creaCtrl = loader.getController();
                    // PASSO IL CONTROLLER (stesso oggetto di sessione)
                    creaCtrl.setController(this.controller);
	                //Mette il nome in alto a destra
                    creaCtrl.setLabel(this.controller.getStudente().getUsername());
	                
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        	        Scene scene = new Scene(root);
        	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
        	        stage.setScene(scene);
        	        stage.centerOnScreen();
        	        stage.setTitle("UninaSwap - Crea Annuncio");
        	        stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
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
        box.setPrefWidth(200);
        box.getStyleClass().add("card-annuncio");

        ImageView imageView = new ImageView();
        try {
        	//a.getOggetto().getImmagineOggetto()
            Image img = new Image("https://pbs.twimg.com/profile_images/806149032091549696/PglCYB9X_400x400.jpg", 200, 150, true, true);
            imageView.setImage(img);
            imageView.getStyleClass().add("immagineCard");
        } catch (Exception e) {
            System.out.println("Immagine non trovata: " + a.getOggetto().getImmagineOggetto());
        }
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Aggiungi l'immagine al VBox
        box.getChildren().add(imageView);

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
        }
    }

    public void showProfile(MouseEvent e) {
    	System.out.println("Profilo utente");
    }
} 
