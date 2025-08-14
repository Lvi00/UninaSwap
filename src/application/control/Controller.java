package application.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DAO.AnnuncioDAO;
import application.DAO.OggettoDAO;
import application.DAO.SedeDAO;
import application.DAO.StudenteDAO;
import application.boundary.CreaAnnuncioBoundary;
import application.boundary.PopupErrorBoundary;
import application.boundary.ProdottiBoundary;
import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import application.entity.Studente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {
	
	private Studente studente;
	
	public int checkData(ArrayList<String> credenziali) {
		String nome = credenziali.get(0);
		String cognome = credenziali.get(1);
		String matricola = credenziali.get(2);
		String email = credenziali.get(3);
		String username = credenziali.get(4);
		String password = credenziali.get(5);
	
		if(nome == "" || cognome == "" ||  matricola == "" || email == "" || username ==  "" || password ==  "") {
			System.out.println("Campi vuoti.");
			return 1;
		}
		//Controllo nome
	    if ((nome.length() > 40 || nome.length() < 2) || isValidNameSurname(nome) == 1) {
	        System.out.println("Nome non valido.");
	        return 2;
	    }

	  //Controllo cognome
	    if ((cognome.length() > 40 || cognome.length() < 2) || isValidNameSurname(cognome) == 1) {
	        System.out.println("Cognome non valido.");
	        return 3;
	    }
	    
	    //Controllo matricola
	    if (matricola.length() != 9 || matricola.contains(" ") || matricola.contains("\t")) {
	    	System.out.println("Matricola non valido.");
	        return 4;
	    }
	    
	    //Controllo email
	    if(isValidEmail(email) == 1) {
	    	 System.out.println("Email non valido.");
	    	 return 5;
	    } 
	    
	    //Controllo username
	    if (username.length() > 10 || username.contains(" ") || username.contains("\t")) {
	    	 System.out.println("Username non valido.");
	        return 6;
	    }
	    
	    //Controllo password
	    if (password.length() < 8 || password.length() > 20) {
	    	 System.out.println("Password non valido.");
	        return 7;
	    }
	    
	    //Controllo se l'utente esiste già
	    if(new StudenteDAO().CheckStudenteEsistente(matricola, email, username) == 1) {
	    	System.out.println("Utente già esistente.");
	    	return 8;
	    }
	    
	    return 0;
	}
	
	public void InserisciStudente(ArrayList<String> credenziali){
	    // Se arrivi qui, tutti i campi sono validi
	    StudenteDAO studenteDAO = new StudenteDAO();
	   	studenteDAO.Save(new Studente(credenziali.get(2), credenziali.get(3), credenziali.get(0), credenziali.get(1), credenziali.get(4)), credenziali.get(5));
	}
	
	private int isValidEmail(String email) {
	    if (email == null || email.contains(" ") || email.contains("\t")) {
	        return 1; // email non valida per spazi o null
	    }

		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) return 0;
        
        return 1;
	}
	
	private int isValidNameSurname(String s) {
		
		String sRegex = "^[A-Za-zÀ-ÿ'\\s]+$";

        Pattern pattern = Pattern.compile(sRegex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) return 0;
        
        return 1;
	}
	
	public Studente CheckLoginStudente(String username, String password){
		Studente studente = new StudenteDAO().LoginStudente(username, password);
		this.studente = studente;
		return this.studente;
	}
	
	public Oggetto getOggetto(int idOggetto) {
		return new OggettoDAO().getOggetto(idOggetto);
	}
	
	public ArrayList<Annuncio> getInfoAnnunci(String s) {
	    return new AnnuncioDAO().getAnnunci(s);
	}
	
	public Studente getStudente() {
	    return this.studente;
	}
	
	public int checkDatiAnnuncio(ArrayList<String> datiAnnuncio, File fileSelezionato) {
		String titolo = datiAnnuncio.get(0);
		
		if(titolo == "" || titolo.length() > 50) return 1;
		
		String categoria = datiAnnuncio.get(1);
		
		if(categoria == "") return 2;
		
		String inizioOrarioDisponibilità = datiAnnuncio.get(2);
		String fineOrarioDisponibilità = datiAnnuncio.get(3);
		
		if(inizioOrarioDisponibilità == "" || fineOrarioDisponibilità == "") return 3;
		
		String giorniDisponibilità = datiAnnuncio.get(4);
		
		if(giorniDisponibilità == "") return 4;
		
		String descrizione = datiAnnuncio.get(5);
		
		if(descrizione == "" || descrizione.length() > 255) return 5;
		
		String particellatoponomastica = datiAnnuncio.get(6);
		String descrizioneIndirizzo = datiAnnuncio.get(7);
		String civico = datiAnnuncio.get(8);
		String cap = datiAnnuncio.get(9);
		
		String capRegex = "^[0-9]{5}$";
		
		if(particellatoponomastica == "" || descrizioneIndirizzo == "" || descrizioneIndirizzo.length() > 255 || civico == "" ||
		civico.length() > 4 || cap == "" || cap.length() != 5 || !cap.matches(capRegex)) return 6;
		
		String tipologia = datiAnnuncio.get(7);
		
		if(tipologia == "") return 7;
		
		if(fileSelezionato == null || fileSelezionato.getName().isEmpty()) return 8;
		
		Sede sede = new Sede(particellatoponomastica, descrizioneIndirizzo, civico, cap);
		
		new SedeDAO().Save(sede);
		
		return 0;
	}
	
	public void copiaFileCaricato(File fileSelezionato) {
    	try {
    		File destinationDir = new File(System.getProperty("user.dir"), "src/application/IMG/uploads");
    		if (!destinationDir.exists()) destinationDir.mkdirs();
    		File destinationFile = new File(destinationDir, fileSelezionato.getName());
    		Files.copy(fileSelezionato.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    		System.out.println("File copiato con successo");
        }
    	catch (IOException ex) {
            System.err.println("Errore durante la copia del file: " + ex.getMessage());
        }
	}
}