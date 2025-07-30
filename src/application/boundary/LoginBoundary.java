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
	
	//Visibilità Password
	@FXML private ImageView TastoShowPassword;
	@FXML private ImageView TastoHidePassword;
	@FXML private TextField VisualizzaPasswordLogin;
	private boolean visibilitaPassword = false;
	
	@FXML
	public void MostraLogin(MouseEvent e) {
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
	    }
	    catch(Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	@FXML
	public void MostraRegistrazione(MouseEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrazione.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("UninaSwap - Registrazione");
            stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
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
		if(visibilitaPassword) VisibilitàPassword(e);
		
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
				try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
		            Parent root = loader.load();
		            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		            Scene scene = new Scene(root);
		            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
		            DashboardBoundary dashboardController = loader.getController();
		            dashboardController.CostruisciDashboard(studente);
		            stage.setScene(scene);
		            stage.centerOnScreen();
		            stage.setTitle("UninaSwap - Dashboard");
		            stage.getIcons().add(new Image(getClass().getResource("../IMG/logoApp.png").toExternalForm()));
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
	
	@FXML
	public void VisibilitàPassword(MouseEvent e) {
		if(!visibilitaPassword) {
			PasswordLogin.setVisible(false);
			VisualizzaPasswordLogin.setVisible(true);
			VisualizzaPasswordLogin.setText(PasswordLogin.getText());
			TastoShowPassword.setVisible(false);
			TastoHidePassword.setVisible(true);
			visibilitaPassword = true;
		}
		else {
			VisualizzaPasswordLogin.setVisible(false);
			PasswordLogin.setVisible(true);
			PasswordLogin.setText(VisualizzaPasswordLogin.getText());
			TastoShowPassword.setVisible(true);
			TastoHidePassword.setVisible(false);
			visibilitaPassword = false;
		}
	}
}
