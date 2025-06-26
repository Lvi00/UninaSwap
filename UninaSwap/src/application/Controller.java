package application;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller {
	@FXML
	private Pane PaneLogin;
	
	@FXML
	private ScrollPane InformazioniLogin;
	
	@FXML
	private Label LabelInfo;
	
	@FXML
	private Label LabelLogin;
	
	@FXML
	private ImageView ImmagineInfo;
	
	@FXML
	private ImageView ImmagineLogin;
	
	@FXML
	public void MostraInfoLogin (MouseEvent e) {
		PaneLogin.setVisible(false);
		InformazioniLogin.setVisible(true);
		
		LabelInfo.setVisible(false);
		LabelLogin.setVisible(true);
		
		ImmagineInfo.setVisible(false);
		ImmagineLogin.setVisible(true);
	}
	
	@FXML
	public void NascondiInfoLogin (MouseEvent e) {
		InformazioniLogin.setVisible(false);
		PaneLogin.setVisible(true);
		
		LabelLogin.setVisible(false);
		LabelInfo.setVisible(true);
		
		ImmagineLogin.setVisible(false);
		ImmagineInfo.setVisible(true);
	}
	
	@FXML
	public void MostraRegistrazione (MouseEvent e) {
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
	public void MostraLogin (MouseEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setTitle("UninaSwap - Login");
			stage.setScene(scene);
			stage.centerOnScreen();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
