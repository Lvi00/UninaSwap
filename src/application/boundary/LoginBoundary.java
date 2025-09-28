package application.boundary;

import java.awt.Desktop;
import java.net.URI;
import application.control.Controller;
import application.entity.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginBoundary {	
	@FXML private Pane PaneLogin;
	@FXML private ScrollPane InformazioniLogin;
	@FXML private ImageView ImmagineInfo;
	@FXML private ImageView ImmagineLogin;
	@FXML private Button ButtonInfo;
	@FXML private Button ButtonReturnLogin;
	@FXML private Pane PaneLabelInfo;
	@FXML private Pane LinkFooter;
	@FXML private TextField UsernameLogin;
	@FXML private PasswordField PasswordLogin;
	@FXML private ImageView TastoShowPassword;
	@FXML private ImageView TastoHidePassword;
	@FXML private TextField VisualizzaPasswordLogin;
	
	private Controller controller = Controller.getController();
	private SceneManager sceneManager = SceneManager.sceneManager();
	
	@FXML
	public void MostraRegistrazione(MouseEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrazione.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("UninaSwap - Registrazione");
            stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            stage.show();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void MostraInfoLogin (MouseEvent e) {
		PaneLogin.setVisible(false);
		InformazioniLogin.setVisible(true);
		
		PaneLabelInfo.setVisible(true);
		LinkFooter.setVisible(true);
		
		ImmagineInfo.setVisible(false);
		ImmagineLogin.setVisible(true);
		
		ButtonInfo.setVisible(false);
		ButtonReturnLogin.setVisible(true);
	}
	
	@FXML
	public void NascondiInfoLogin (MouseEvent e) {
		InformazioniLogin.setVisible(false);
		PaneLogin.setVisible(true);
		
		PaneLabelInfo.setVisible(false);
		LinkFooter.setVisible(false);
		
		ImmagineLogin.setVisible(false);
		ImmagineInfo.setVisible(true);
		
		ButtonReturnLogin.setVisible(false);
		ButtonInfo.setVisible(true);
	}
	
	@FXML
	public void LinkProfiloGithub(MouseEvent e) {
	    Node source = (Node) e.getSource();
	    String text = "";
		String name = "";

	    if (source instanceof Labeled) {
	        text = ((Labeled) source).getText();
	        System.out.println(text);
	        
	        if(text.equals("Luigi Castaldo")) name = "Lvi00";
	        else if(text.equals("Giuseppe Cautiero")) name = "giuseeee88";
	        else System.out.println("Nome non riconosciuto.");
	        
	        try {
	            Desktop.getDesktop().browse(new URI("https://github.com/" + name));
	        }
			catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    else System.out.println("Elemento cliccato non contiene testo.");
	}
	
	@FXML
	public void invioDatiLogin(MouseEvent e) {
		if(VisualizzaPasswordLogin.isVisible()) VisibilitàPassword(e);
		
		if(UsernameLogin.getText().trim().isEmpty() || PasswordLogin.getText().trim().isEmpty()){
			ShowPopupError("Campi Vuoti", "I campi Username e Password non possono essere vuoti!");
		}
		else {
			String username = UsernameLogin.getText().trim();
			String password = PasswordLogin.getText().trim();
			Studente studente = controller.CheckLoginStudente(username, password);
			
			if(studente == null)
				ShowPopupError("Utente non esistente", "Le credenziali inserite non sono corrette");
			else {
				this.controller.setStudente(studente);
				try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
		            Parent root = loader.load();
                    ProdottiBoundary prodottiCtrl = loader.getController();
	                prodottiCtrl.CostruisciCatalogoProdotti(this.controller.getStudente());
	                prodottiCtrl.setUsername(controller.getUsername(this.controller.getStudente()));
                    prodottiCtrl.setImmagine(controller.getImmagineProfilo(this.controller.getStudente()));
                    prodottiCtrl.setFiltri();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		            Scene scene = new Scene(root);
		            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		            stage.setScene(scene);
		            stage.setTitle("UninaSwap - Prodotti");
		            stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
		            stage.setResizable(false);
		            stage.show();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private void ShowPopupError(String title, String message) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) PaneLogin.getScene().getWindow();
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

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	@FXML
	public void VisibilitàPassword(MouseEvent e) {
		if(!VisualizzaPasswordLogin.isVisible()) {
			PasswordLogin.setVisible(false);
			VisualizzaPasswordLogin.setVisible(true);
			VisualizzaPasswordLogin.setText(PasswordLogin.getText());
			TastoShowPassword.setVisible(false);
			TastoHidePassword.setVisible(true);
		}
		else {
			VisualizzaPasswordLogin.setVisible(false);
			PasswordLogin.setVisible(true);
			PasswordLogin.setText(VisualizzaPasswordLogin.getText());
			TastoShowPassword.setVisible(true);
			TastoHidePassword.setVisible(false);
		}
	}
}
