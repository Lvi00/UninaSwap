package application.boundary;

import java.io.File;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfiloBoundary {
	
    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
	
    @FXML private Label usernameDashboard;
    @FXML private Label usernameProfilo;
    @FXML private Label nomeProfilo;
    @FXML private Label cognomeProfilo;
    @FXML private Label matricolaProfilo;
    @FXML private Label emailProfilo;
    @FXML private ImageView immagineProfilo;
    @FXML private ImageView immagineNav;
    
    public void costruisciPagina() { 	
    	usernameDashboard.setText(controller.getUsername(controller.getStudente()));
        usernameProfilo.setText(controller.getUsername(controller.getStudente()));
        
        String nome = controller.getNome(controller.getStudente());
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        nomeProfilo.setText(nome);
        
        String cognome = controller.getCognome(controller.getStudente());
        cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1).toLowerCase();
        cognomeProfilo.setText(cognome);
        
        matricolaProfilo.setText(controller.getMatricola(controller.getStudente()));
        emailProfilo.setText(controller.getEmail(controller.getStudente()));
        String immagineP = controller.getImmagineProfilo(controller.getStudente());
        
        try {
            File file = new File(immagineP);
            Image image;
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                image = new Image(getClass().getResource(immagineP).toExternalForm());
            }

            immagineProfilo.setImage(image);
            immagineProfilo.setFitWidth(140);
            immagineProfilo.setFitHeight(140);  
            immagineProfilo.setPreserveRatio(false); 
            Circle clip1 = new Circle(70, 70, 70);
            immagineProfilo.setClip(clip1);

            immagineNav.setImage(image);
            immagineNav.setFitWidth(33);
            immagineNav.setFitHeight(33);  
            immagineNav.setPreserveRatio(false);
            Circle clip2 = new Circle(16.5, 16.5, 16.5);
            immagineNav.setClip(clip2);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore caricando immagine: " + immagineP);
        }
    }
    
    @FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        Label label = (Label) source;
        String nomePagina = label.getText();
    	sceneManager.SelezionaPagina(nomePagina, e);
    }
    
    public void cambiaFoto(MouseEvent e)
    {
    	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
    	
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            this.immagineProfilo.setImage(image);
            this.immagineNav.setImage(image);
            controller.setImmagineProfiloSelezionata(selectedFile);
            controller.copiaImmagineProfiloCaricata(controller.getImmagineProfiloSelezionata());
            controller.caricaFileImmagine(controller.getImmagineProfiloSelezionata().getName());
        }
    }
	
    @FXML
    public void logout(MouseEvent e) { 
    	controller.logout();
        sceneManager.SelezionaPagina("Login", e);
    }
}
