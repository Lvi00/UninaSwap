package application.boundary;

import java.io.File;
import java.util.ArrayList;
import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AnnunciStudenteBoundary {

    private Controller controller;

    @FXML private HBox containerAnnunciStudente;
    @FXML private Label usernameDashboard;
    @FXML private GridPane gridProdotti;
    @FXML private TextField searchField;
    @FXML private AnchorPane contentPane;
    @FXML private ImageView immagineNav;
    @FXML private Label labelAnnunciPubblicati;

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
        // Prende l'oggetto cliccato
        Object source = e.getSource();

        // Controlla se è una Label
        if (source instanceof Label) {
            Label label = (Label) source;
            String testo = label.getText();
            
            System.out.println("Navigazione a: " + testo);

            switch (testo) {
                case "Prodotti":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                        Parent root = loader.load();
                        ProdottiBoundary prodottiCtrl = loader.getController();
                        prodottiCtrl.setController(this.controller);
                        prodottiCtrl.setUsername(this.controller.getStudente().getUsername());
                        prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
                        prodottiCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
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
		                offerteCtrl.setUsername(this.controller.getStudente().getUsername());
		                offerteCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
		                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	        Scene scene = new Scene(root);
		    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		    	        stage.setScene(scene);
		    	        stage.setTitle("UninaSwap - Offerte");
		    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		    	        stage.setResizable(false);
		    	        stage.show();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		            }
	            break;

                case "Crea annuncio":
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
                        Parent root = loader.load();

                        CreaAnnuncioBoundary creaCtrl = loader.getController();
                        creaCtrl.setController(this.controller);
                        creaCtrl.setUsername(this.controller.getStudente().getUsername());
                        creaCtrl.setCampiForm();
                        creaCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());

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
                	// Profilo utente
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profilo.fxml"));
                        Parent root = loader.load();
                        ProfiloBoundary ProfiloCtrl = loader.getController();
                        ProfiloCtrl.setController(this.controller);
                        ProfiloCtrl.setUsername(this.controller.getStudente().getUsername());
                        ProfiloCtrl.setNome(this.controller.getStudente().getNome());
                        ProfiloCtrl.setCognome(this.controller.getStudente().getCognome());
                        ProfiloCtrl.setMatricola(this.controller.getStudente().getMatricola());
                        ProfiloCtrl.setEmail(this.controller.getStudente().getEmail());
                        ProfiloCtrl.setImmagine(this.controller.getStudente().getImmagineProfilo());
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

    public boolean CostruisciProdottiUtente(Studente s) {
        ArrayList<Annuncio> annunci = controller.getAnnunciStudente(s.getMatricola());
        String titolo = "";
        
        if(annunci.size() == 0) titolo = "Non ci sono annunci attivi di ";
        else titolo = "Annunci attivi di ";
        
        labelAnnunciPubblicati.setText(titolo + s.getUsername());
        
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

        return true;
    }

    private VBox creaCardAnnuncio(Annuncio a) {
        VBox box = new VBox();
        box.setPrefWidth(230);
        box.setPrefHeight(300);
        box.setSpacing(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.getStyleClass().add("card-annuncio");

        // Wrapper per immagine + button
        AnchorPane imagePane = new AnchorPane();
        imagePane.setPrefWidth(230);
        imagePane.setPrefHeight(150);

        ImageView imageView = new ImageView();
        try {
            String path = a.getOggetto().getImmagineOggetto();
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

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Immagine non trovata: " + a.getOggetto().getImmagineOggetto());
        }

        Button button = new Button();
        button.getStyleClass().add("tasto-terziario");
        button.setPrefSize(24, 24);
        button.setStyle("-fx-cursor: hand;");
        button.setOnMouseClicked(event -> rimuoviAnnuncio(a));
        AnchorPane.setTopAnchor(button, 5.0);
        AnchorPane.setRightAnchor(button, 5.0);
        
        ImageView icon = new ImageView();
        try {
            String iconPath = "../IMG/immaginiProgramma/delete_card.png";
            Image imgIcon = new Image(getClass().getResource(iconPath).toExternalForm());
            icon.setImage(imgIcon);
            icon.setFitWidth(22);
            icon.setFitHeight(22);
            icon.setPreserveRatio(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        button.setGraphic(icon);

        imagePane.getChildren().addAll(imageView, button);

        Label titolo = new Label(a.getTitoloAnnuncio());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        titolo.setWrapText(true);
        titolo.setMaxWidth(200);
        titolo.setAlignment(Pos.CENTER);

        Label stato = new Label(a.isStatoAnnuncio() ? "Attivo" : "Non attivo");
        stato.getStyleClass().add(a.isStatoAnnuncio() ? "label-attivo" : "label-non-attivo");
        VBox.setMargin(stato, new Insets(4, 0, 4, 0));

        Label prezzo = new Label(String.format("\u20AC %.2f", a.getPrezzo()));
        prezzo.setStyle("-fx-text-fill: #153464; -fx-font-size: 12;");

        Label tipo = new Label(a.getOggetto().getCategoria() + " - " + a.getTipologia());
        tipo.setStyle("-fx-text-fill: gray;");

        Label disponibilità = new Label("Disponibile il " + (a.getGiorni() != null ? a.getGiorni() : "N/D")
                + "\ndalle " + a.getFasciaOrariaInizio() + " alle " + a.getFasciaOrariaFine());

        VBox boxPrezzo = new VBox();
        boxPrezzo.setAlignment(Pos.CENTER);
        boxPrezzo.setSpacing(4);
        boxPrezzo.getChildren().addAll(stato, prezzo);

        // Aggiungi tutto alla card
        box.getChildren().addAll(imagePane, titolo, boxPrezzo, tipo, disponibilità);

        return box;
    }
    
    private void rimuoviAnnuncio(Annuncio a) {
        if(controller.rimuoviAnnuncio(a) == 0) {
            gridProdotti.getChildren().clear();
            CostruisciProdottiUtente(controller.getStudente());
            ShowPopupAlert("Rimozione avvenuta", "L'annuncio è stato rimosso con successo.");
        }
        else {
			ShowPopupAlert("Rimozione fallita", "Si è verificato un errore durante la rimozione dell'annuncio.");
		}
    }
    
    private void ShowPopupAlert(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupAlert.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) containerAnnunciStudente.getScene().getWindow();
	        Stage stage = new Stage();
	        stage.initOwner(mainStage);
	        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("UninaSwap - " + title);
	        stage.setResizable(false);
	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));

	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	       
	        mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
	        stage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

	        stage.show();
	        
	        stage.setX(mainStage.getX() + (mainStage.getWidth() - stage.getWidth()) / 2);
	        stage.setY(mainStage.getY() + (mainStage.getHeight() - stage.getHeight()) / 2 - 50);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}