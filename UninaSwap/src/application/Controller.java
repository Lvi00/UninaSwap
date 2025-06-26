package application;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.awt.ScrollPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {
	
	private Pane PaneLogin;
	private ScrollPane InfoLogin; 
	
	public void MostraInfoLogin (MouseEvent e) 
	{
		try {
			PaneLogin.setVisible(false);
			InfoLogin.setVisible(true);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	} 
	
	public void Registrazione (MouseEvent e) 
	{
			try {
				Parent root = FXMLLoader.load(getClass().getResource("Registrazione.fxml"));
				Scene scene = new Scene(root);
				//Permette di chiudere la precedente paggina
	            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setTitle("UninaSwap-Registazione");
				stage.setScene(scene);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
	}
	
	public void Login (MouseEvent e) 
	{
			try {
				Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene scene = new Scene(root);
				//Permette di chiudere la precedente paggina
	            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	            stage.setTitle("UninaSwap-Login");
				stage.setScene(scene);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
	}
}
