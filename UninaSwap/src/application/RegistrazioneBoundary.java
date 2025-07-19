package application;

import java.awt.Desktop;
import java.net.URI;

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

public class RegistrazioneBoundary {
	Controller controller = new Controller();
	@FXML private Pane PaneRegistrazione;
	@FXML private ScrollPane InformazioniRegistrazione;
	@FXML private ImageView ImmagineInfoReg;
	@FXML private ImageView ImmagineRegistrazione;
	@FXML private Button ButtonInfoReg;
	@FXML private Button ButtonReturnRegistrazione;
	@FXML private Pane PaneLabelInfoReg;
	@FXML private Pane LinkFooterRegistrazione;
	
	//Campi di Registrazione
	@FXML private TextField  nomeFieldReg;
	@FXML private TextField  cognomeFieldReg;
	@FXML private TextField  matricolaFieldReg;
	@FXML private TextField  emailFieldReg;
	@FXML private TextField  usernameFieldReg;
	@FXML private PasswordField   passwordFieldReg;
	
	//Visibilità Password
	@FXML private ImageView TastoShowPassword;
	@FXML private ImageView TastoHidePassword;
	@FXML private TextField VisualizzaPasswordReg;
	boolean visibilitaPassword = false;
	
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
	
	@FXML
	public void MostraInfoRegistrazione (MouseEvent e) {
		PaneRegistrazione.setVisible(false);
		InformazioniRegistrazione.setVisible(true);
		
		PaneLabelInfoReg.setVisible(true);
	
		LinkFooterRegistrazione.setVisible(true);
		
		ImmagineInfoReg.setVisible(false);
		ImmagineRegistrazione.setVisible(true);
		
		ButtonInfoReg.setVisible(false);
		ButtonReturnRegistrazione.setVisible(true);
	}
	
	@FXML
	public void NascondiInfoRegistrazione (MouseEvent e) {
		InformazioniRegistrazione.setVisible(false);
		PaneRegistrazione.setVisible(true);
		
		PaneLabelInfoReg.setVisible(false);
		
		LinkFooterRegistrazione.setVisible(false);
		
		ImmagineRegistrazione.setVisible(false);
		ImmagineInfoReg.setVisible(true);
		
		ButtonReturnRegistrazione.setVisible(false);
		ButtonInfoReg.setVisible(true);
	}
	
	@FXML	
	public void PrelevaDati(MouseEvent e) {
		
        String nome = nomeFieldReg.getText().trim();
        String cognome = cognomeFieldReg.getText().trim();
        String matricola = matricolaFieldReg.getText().trim();
        String email = emailFieldReg.getText().trim();
        String username = usernameFieldReg.getText().trim();
        String password = passwordFieldReg.getText().trim();

		if(nome == "" || cognome == "" ||  matricola == "" || email == "" || username ==  "" || password ==  "")
			ShowPopupError("Campi mancanti", "Mancano dei campi obbligatori. Compila tutti i campi per procedere.");
		else {
			String result = controller.checkData(nome, cognome, matricola, email, username, password);
			
	        switch (result) {
	            case "nome":
	                nomeFieldReg.clear();
	                ShowPopupError("Errore nel nome", "Nome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
                break;
                
	            case "cognome":
	                cognomeFieldReg.clear();
	                ShowPopupError("Errore nel cognome", "Cognome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
                break;
                
	            case "matricola":
	                matricolaFieldReg.clear();
	                ShowPopupError("Errore nella matricola", "Matricola non valida. Deve essere lunga 9 caratteri.");
                break;
                
	            case "email":
	                emailFieldReg.clear();
	                ShowPopupError("Errrore nell'email", "Email non valida. Non rispetta il formato standard.");
                break;
                
	            case "username":
	                usernameFieldReg.clear();
	                ShowPopupError("Errore nell'username", "Username non valido. Deve essere lungo al massimo 10 caratteri.");
                break;
                
	            case "password":
	                passwordFieldReg.clear();
	                ShowPopupError("Errore nella password", "Password non valida. Deve essere lunga tra 8 e 20 caratteri.");
                break;
                
	            default:
	        		System.out.println("bene");
	        	break;
	        }
		}		
	}
	
	private void ShowPopupError(String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			
	        PopupErrorController popupController = loader.getController();
	        popupController.setLabels(title, message);
	        
			stage.setTitle("UninaSwap - " + title);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);		
		    stage.show();
			stage.getIcons().addAll(
                new Image(getClass().getResource("IMG/logoApp.png").toExternalForm())
            );
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
	public void VisibilitàPassword(MouseEvent e) {
		if(!visibilitaPassword) {
			VisualizzaPasswordReg.setText(passwordFieldReg.getText());
			passwordFieldReg.setVisible(false);
			TastoShowPassword.setVisible(false);
			TastoHidePassword.setVisible(true);
			VisualizzaPasswordReg.setVisible(true);
			visibilitaPassword = true;
		}
		else {
			VisualizzaPasswordReg.setVisible(false);
			passwordFieldReg.setVisible(true);
			TastoShowPassword.setVisible(true);
			TastoHidePassword.setVisible(false);
			visibilitaPassword = false;
		}
	}
}
