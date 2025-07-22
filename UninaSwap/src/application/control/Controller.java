package application.control;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DAO.StudenteDAO;
import application.entity.Studente;
import application.resources.ConnessioneDB;

import java.sql.*;

public class Controller {

	public int checkData(ArrayList<String> credenziali) {
		
		//Controllo nome
	    if ((credenziali.get(0).length() > 40 || credenziali.get(0).length() < 2) || isValidNameSurname(credenziali.get(0)) == 1) {
	        System.out.println("Nome non valido.");
	        return 1;
	    }

	  //Controllo cognome
	    if ((credenziali.get(1).length() > 40 || credenziali.get(1).length() < 2) || isValidNameSurname(credenziali.get(1)) == 1) {
	        System.out.println("Cognome non valido.");
	        return 2;
	    }
	    
	    //Controllo matricola
	    if (credenziali.get(2).length() != 9 || credenziali.get(2).contains(" ") || credenziali.get(2).contains("\t")) {
	    	System.out.println("Matricola non valido.");
	        return 3;
	    }
	    
	    //Controllo email
	    if(isValidEmail(credenziali.get(3)) == 1) {
	    	 System.out.println("Email non valido.");
	    	 return 4;
	    } 
	    
	    //Controllo username
	    if (credenziali.get(4).length() > 10 || credenziali.get(4).contains(" ") || credenziali.get(4).contains("\t")) {
	    	 System.out.println("Username non valido.");
	        return 5;
	    }
	    
	    //Controllo password
	    if (credenziali.get(5).length() < 8 || credenziali.get(5).length() > 20) {
	    	 System.out.println("Password non valido.");
	        return 6;
	    }
	    
	    //Controllo se l'utente esiste già
	    if(CheckUtenteEsistente(credenziali.get(2), credenziali.get(3), credenziali.get(4)) == 1) {
	    	System.out.println("Utente già esistente.");
	    	return 7;
	    }
	    
	    return 0;
	}
	

	public void InserisciStudente(ArrayList<String> credenziali)
	{
	    // Se arrivi qui, tutti i campi sono validi
	    StudenteDAO studenteDAO = new StudenteDAO();
	    studenteDAO.SaveStudente(new Studente(credenziali.get(2),credenziali.get(3),credenziali.get(0),credenziali.get(1),credenziali.get(5),credenziali.get(4)));
	    
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
	
	private int CheckUtenteEsistente(String matricola, String email, String username) {
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "SELECT 1 FROM STUDENTE WHERE matricola = ? OR email = ? OR username = ?;";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, matricola);
		    statement.setString(2, email);
		    statement.setString(3, username);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            System.out.println("Errore: Utente già esistente.");
	            rs.close();
	            statement.close();
	            return 1;
	        }
		}
		catch (SQLException ex) {
		    System.out.println("Errore nella connessione al database");
		    ex.printStackTrace();
		}
		
		System.out.println("Utente non esistente.");
		
		return 0;
	}
	
	public int LogStudente(String username, String password)
	{	
		
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "SELECT 1 FROM STUDENTE WHERE username = ? AND passkey = ?";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, username);
		    statement.setString(2, password);
	        ResultSet rs = statement.executeQuery();
	        if (!rs.next()) {
	            System.out.println("Utente non esistente.");
	            rs.close();
	            statement.close();
	            return 1;
	        }
	        else {
	        	System.out.println("Utente esistente.");
	        }
		}
		catch (SQLException ex) {
		    System.out.println("Errore nella connessione al database");
		    ex.printStackTrace();
		}
		
		return 0;
	}
	
}
