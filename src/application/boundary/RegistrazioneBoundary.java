package application.boundary;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;

import application.control.Controller;
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

public class RegistrazioneBoundary {
	
	private Controller controller = new Controller();
	
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
	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
	        stage.setResizable(false);
	        stage.show();
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
		if(visibilitaPassword) VisibilitàPassword(e);
							
		ArrayList<String> credenziali = new ArrayList<String>();
		
        credenziali.add(nomeFieldReg.getText().toLowerCase().trim());
        credenziali.add(cognomeFieldReg.getText().toLowerCase().trim());
        credenziali.add(matricolaFieldReg.getText().toUpperCase().trim());
        credenziali.add(emailFieldReg.getText().toLowerCase().trim());
        credenziali.add(usernameFieldReg.getText().trim());
        credenziali.add(passwordFieldReg.getText().trim());
        
        switch (controller.checkData(credenziali)) {
        	case 0:
        		System.out.println("Tutti i campi sono validi");
        		controller.InserisciStudente(credenziali);
        		MostraLogin(e);
        	break;
        	
        	case 1:
	        	ShowPopupError("Campi mancanti", "Mancano dei campi obbligatori. Compila tutti i campi per procedere.");
	        break;
	        
            case 2:
                nomeFieldReg.clear();
                ShowPopupError("Errore nel nome", "Nome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
            break;
            
            case 3:
                cognomeFieldReg.clear();
                ShowPopupError("Errore nel cognome", "Cognome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
            break;
            
            case 4:
                matricolaFieldReg.clear();
                ShowPopupError("Errore nella matricola", "Matricola non valida. Deve essere lunga 9 caratteri e non contenere spazzi.");
            break;
            
            case 5:
                emailFieldReg.clear();
                ShowPopupError("Errrore nell'email", "Email non valida. Non rispetta il formato standard.");
            break;
            
            case 6:
                usernameFieldReg.clear();
                ShowPopupError("Errore nell'username", "Username non valido. Deve essere lungo al massimo 10 caratteri e non contenere spazzi.");
            break;
            
            case 7:
                passwordFieldReg.clear();
                VisualizzaPasswordReg.clear();
                ShowPopupError("Errore nella password", "Password non valida. Deve essere lunga tra 8 e 20 caratteri.");
            break;
            
            case 8:
            	matricolaFieldReg.clear();
                nomeFieldReg.clear();
                cognomeFieldReg.clear();
                emailFieldReg.clear();
                passwordFieldReg.clear();
                VisualizzaPasswordReg.clear();
                usernameFieldReg.clear();
                ShowPopupError("Utente già esistente", "Un utente con la stessa matricola, email o username è già registrato.");
            break;
            
            default:
        		System.out.println("Errore sconosciuto durante la registrazione.");
        	break;
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
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
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
	public void VisibilitàPassword(MouseEvent e) {
		if(!visibilitaPassword) {
			passwordFieldReg.setVisible(false);
			VisualizzaPasswordReg.setVisible(true);
			VisualizzaPasswordReg.setText(passwordFieldReg.getText());
			TastoShowPassword.setVisible(false);
			TastoHidePassword.setVisible(true);
			visibilitaPassword = true;
		}
		else {
			passwordFieldReg.setText(VisualizzaPasswordReg.getText());
			VisualizzaPasswordReg.setVisible(false);
			passwordFieldReg.setVisible(true);
			TastoShowPassword.setVisible(true);
			TastoHidePassword.setVisible(false);
			visibilitaPassword = false;
		}
	}
}
