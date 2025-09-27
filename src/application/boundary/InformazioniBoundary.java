package application.boundary;

import java.io.File;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class InformazioniBoundary {
	private Controller controller;

    @FXML private HBox containerCatalogoProdotti;
    @FXML private Label usernameDashboard;
    @FXML private ImageView immagineNav;
    
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
            immagineNav.setImage(image);
            immagineNav.setFitWidth(33);
            immagineNav.setFitHeight(33);  
            immagineNav.setPreserveRatio(false);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore caricando immagine: " + immagineP);
        }
    }
    
    
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();

        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();

            switch (testo) {
                case "Prodotti":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                        Parent root = loader.load();
                        ProdottiBoundary prodottiCtrl = loader.getController();
                        prodottiCtrl.setController(this.controller);
                        prodottiCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
                        prodottiCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        prodottiCtrl.setFiltri();
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Crea annuncio");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "Offerte":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offerte.fxml"));
                        Parent root = loader.load();
                        OfferteBoundary offerteCtrl = loader.getController();
                        offerteCtrl.setController(this.controller);
                        offerteCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        offerteCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        offerteCtrl.CostruisciOfferteUtente(this.controller.getStudente());
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Offerte");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "Informazioni":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Informazioni.fxml"));
                        Parent root = loader.load();
                        InformazioniBoundary infoCtrl = loader.getController();
                        infoCtrl.setController(this.controller);
                        infoCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        infoCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Crea annuncio");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "Crea annuncio":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
                        Parent root = loader.load();
                        CreaAnnuncioBoundary creaCtrl = loader.getController();
                        creaCtrl.setController(this.controller);
                        creaCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                        creaCtrl.setCampiForm();
                        creaCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        creaCtrl.MostraPaneVendita(e);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Crea annuncio");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "I tuoi annunci":
                    System.out.println("Sei già nella pagina I tuoi annunci.");
                    break;

                default:
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profilo.fxml"));
                        Parent root = loader.load();
                        ProfiloBoundary ProfiloCtrl = loader.getController();
                        ProfiloCtrl.setController(this.controller);
                        ProfiloCtrl.setUsername(this.controller.getStudente().getUsername());
                        ProfiloCtrl.setNome(controller.getNome(this.controller.getStudente()));
                        ProfiloCtrl.setCognome(controller.getCognome(this.controller.getStudente()));
                        ProfiloCtrl.setMatricola(controller.getMatricola(this.controller.getStudente()));
                        ProfiloCtrl.setEmail(controller.getEmail(this.controller.getStudente()));
                        ProfiloCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setTitle("UninaSwap - Profilo");
                        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }
}
