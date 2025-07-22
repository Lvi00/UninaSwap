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
		if(!UsernameLogin.getText().isEmpty() && !PasswordLogin.getText().isEmpty()){
			String username = UsernameLogin.getText();
			String password = PasswordLogin.getText();
			System.out.println(username);
			System.out.println(password);
		}
		else System.out.println("Username e Password non possono essere vuoti.");
	}
}
