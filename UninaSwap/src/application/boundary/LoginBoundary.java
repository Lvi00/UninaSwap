package application.boundary;

import java.awt.Desktop;
import java.net.URI;

import application.control.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginBoundary {
	private Controller controller = new Controller();
	
	@FXML private Pane PaneLogin;
	@FXML private ScrollPane InformazioniLogin;
	@FXML private ImageView ImmagineInfo;
	@FXML private ImageView ImmagineLogin;
	@FXML private Button ButtonInfo;
	@FXML private Button ButtonReturnLogin;
	@FXML private Pane PaneLabelInfo;
	@FXML private Pane LinkFooter;
	
	//Campi login
	@FXML private TextField UsernameLogin;
	@FXML private PasswordField PasswordLogin;
	
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
	public void MostraRegistrazione(MouseEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Registrazione.fxml"));
			Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("UninaSwap - Registazione");
			stage.setScene(scene);
			stage.centerOnScreen();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void LinkLuigi(MouseEvent e) {
		try {
            Desktop.getDesktop().browse(new URI("https://github.com/Lvi00"));
        }
		catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@FXML
	public void LinkGiuseppe(MouseEvent e) {
		try {
            Desktop.getDesktop().browse(new URI("https://github.com/giuseeee88"));
        }
		catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@FXML
	public void invioDatiLogin(ActionEvent e) {
		if(UsernameLogin.getText().trim().isEmpty() || PasswordLogin.getText().trim().isEmpty()){
			ShowPopupError("Campi Vuoti", "I campi Username e Password non possono essere vuoti!");
		}
		else {
			String username = UsernameLogin.getText().trim();
			String password = PasswordLogin.getText().trim();

			if(controller.LogStudente(username, password)==1)
			{
                ShowPopupError("Utente non esistente", "Le credenziali inserite non sono corrette");
			}
			else {
				
			}
		}
	}
	
	private void ShowPopupError(String title, String message) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			
	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	        
			stage.setTitle("UninaSwap - " + title);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);		
		    stage.show();
			stage.getIcons().addAll(
                new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm())
            );
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
