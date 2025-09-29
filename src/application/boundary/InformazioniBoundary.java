package application.boundary;

import java.io.File;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class InformazioniBoundary {
    @FXML private HBox containerCatalogoProdotti;
    @FXML private Label usernameDashboard;
    @FXML private ImageView immagineNav;
    
    private SceneManager sceneManager = SceneManager.sceneManager();
	private Controller controller = Controller.getController();

	public void costruisciPagina() {
		usernameDashboard.setText(controller.getUsername(controller.getStudente()));
		String immagineP = controller.getImmagineProfilo(controller.getStudente());
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
    
    @FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        Label label = (Label) source;
        String nomePagina = label.getText();
    	sceneManager.SelezionaPagina(nomePagina, e);
    }
}
