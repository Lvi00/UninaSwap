package application.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DAO.AnnuncioDAO;
import application.DAO.StudenteDAO;
import application.entity.Annuncio;
import application.entity.Studente;

public class Controller {
	
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
	   	studenteDAO.Save(new Studente(credenziali.get(2), credenziali.get(3), credenziali.get(0), credenziali.get(1), credenziali.get(5), credenziali.get(4)));

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
		return new StudenteDAO().LoginStudente(username, password);
	}
	
	public ArrayList<Annuncio> getAnnunci(){
		AnnuncioDAO annuncioDAO = new AnnuncioDAO();
		return annuncioDAO.getAnnunci();
	}
}
