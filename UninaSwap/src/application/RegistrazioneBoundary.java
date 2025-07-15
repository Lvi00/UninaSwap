package application;

import java.awt.Desktop;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
	
	//Campi di Reggistrazione
	@FXML private TextField  nomeFieldReg;
	@FXML private TextField  cognomeFieldReg;
	@FXML private TextField  matricolaFieldReg;
	@FXML private TextField  emailFieldReg;
	@FXML private TextField  usernameFieldReg;
	@FXML private PasswordField   passwordFieldReg;
	
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
			System.out.println("merda");
		else {
			String result = controller.checkData(nome,cognome,matricola,email,username,password);
			
	        switch (result) {
            case "nome":
                nomeFieldReg.clear();
                nomeFieldReg.setPromptText("Nome non valido");
                break;
            case "cognome":
                cognomeFieldReg.clear();
                cognomeFieldReg.setPromptText("Cognome non valido");
                break;
            case "matricola":
                matricolaFieldReg.clear();
                matricolaFieldReg.setPromptText("Matricola non valida");
                break;
            case "email":
                emailFieldReg.clear();
                emailFieldReg.setPromptText("Email non valida");
                break;
            case "username":
                usernameFieldReg.clear();
                usernameFieldReg.setPromptText("Username non valido");
                break;
            case "password":
                passwordFieldReg.clear();
                passwordFieldReg.setPromptText("Password non valida");
                break;
            default: System.out.println("bene");
	        }
		}
        // Qui puoi aggiungere logica di validazione o salvataggio
		
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
}