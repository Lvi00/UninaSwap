package application.boundary;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;

import application.control.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RegistrazioneBoundary {
	
    private Controller controller = Controller.getController();
    private SceneManager sceneManager = SceneManager.sceneManager();
	
	@FXML private Pane PaneRegistrazione;
	@FXML private ScrollPane InformazioniRegistrazione;
	@FXML private ImageView ImmagineInfoReg;
	@FXML private ImageView ImmagineRegistrazione;
	@FXML private Button ButtonInfoReg;
	@FXML private Button ButtonReturnRegistrazione;
	@FXML private Pane PaneLabelInfoReg;
	@FXML private Pane LinkFooterRegistrazione;
	@FXML private TextField  nomeFieldReg;
	@FXML private TextField  cognomeFieldReg;
	@FXML private TextField  matricolaFieldReg;
	@FXML private TextField  emailFieldReg;
	@FXML private TextField  usernameFieldReg;
	@FXML private PasswordField   passwordFieldReg;
	@FXML private ImageView TastoShowPassword;
	@FXML private ImageView TastoHidePassword;
	@FXML private TextField VisualizzaPasswordReg;
	
	@FXML
	public void MostraLogin(MouseEvent e) {
		sceneManager.SelezionaPagina("Login", e);
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
		if(VisualizzaPasswordReg.isVisible()) VisibilitàPassword(e);
							
		ArrayList<String> credenziali = new ArrayList<String>();
		
        credenziali.add(nomeFieldReg.getText().toLowerCase().trim());
        credenziali.add(cognomeFieldReg.getText().toLowerCase().trim());
        credenziali.add(matricolaFieldReg.getText().toUpperCase().trim());
        credenziali.add(emailFieldReg.getText().toLowerCase().trim());
        credenziali.add(usernameFieldReg.getText().trim());
        credenziali.add(passwordFieldReg.getText().trim());
        
        switch (controller.checkDatiRegistrazione(credenziali)) {
	        case 0:
	            System.out.println("Tutti i campi sono validi");
	            controller.InserisciStudente(credenziali);
	            MostraLogin(e);
	            break;
	
	        case 1:
	            sceneManager.showPopupError(PaneRegistrazione, "Campi mancanti", "Mancano dei campi obbligatori. Compila tutti i campi per procedere.");
	            break;
	
	        case 2:
	            nomeFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errore nel nome", "Nome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
	            break;
	
	        case 3:
	            cognomeFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errore nel cognome", "Cognome non valido. Deve essere lungo tra 2 e 40 caratteri e non contenere numeri o simboli.");
	            break;
	
	        case 4:
	            matricolaFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errore nella matricola", "Matricola non valida. Deve essere lunga 9 caratteri e non contenere spazzi.");
	            break;
	
	        case 5:
	            emailFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errrore nell'email", "Email non valida. Non rispetta il formato standard.");
	            break;
	
	        case 6:
	            usernameFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errore nell'username", "Username non valido. Deve essere lungo al massimo 10 caratteri e non contenere spazzi.");
	            break;
	
	        case 7:
	            passwordFieldReg.clear();
	            VisualizzaPasswordReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Errore nella password", "Password non valida. Deve essere lunga tra 8 e 20 caratteri.");
	            break;
	
	        case 8:
	            matricolaFieldReg.clear();
	            nomeFieldReg.clear();
	            cognomeFieldReg.clear();
	            emailFieldReg.clear();
	            passwordFieldReg.clear();
	            VisualizzaPasswordReg.clear();
	            usernameFieldReg.clear();
	            sceneManager.showPopupError(PaneRegistrazione, "Utente già esistente", "Un utente con la stessa matricola, email o username è già registrato.");
	            break;
	
	        default:
	            System.out.println("Errore sconosciuto durante la registrazione.");
	            break;
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
		if(!VisualizzaPasswordReg.isVisible()) {
			passwordFieldReg.setVisible(false);
			VisualizzaPasswordReg.setVisible(true);
			VisualizzaPasswordReg.setText(passwordFieldReg.getText());
			TastoShowPassword.setVisible(false);
			TastoHidePassword.setVisible(true);
		}
		else {
			passwordFieldReg.setText(VisualizzaPasswordReg.getText());
			VisualizzaPasswordReg.setVisible(false);
			passwordFieldReg.setVisible(true);
			TastoShowPassword.setVisible(true);
			TastoHidePassword.setVisible(false);
		}
	}
}
