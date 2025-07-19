package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class Controller {

	public String checkData(String nome, String cognome, String matricola, String email, String username, String password) {
	
	    if ((nome.length() > 40 || nome.length() < 2) || isValidNameSurname(nome) == 1) {
	        System.out.println("Nome non valido.");
	        return "nome";
	    }

	    
	    if ((cognome.length() > 40 || cognome.length() < 2) || isValidNameSurname(cognome) == 1) {
	        System.out.println("Cognome non valido.");
	        return "cognome";
	    }
	    
	    
	    if (matricola.length() != 9) {
	    	System.out.println("Matricola non valido.");
	        return "matricola";
	    }
	    
	    if(isValidEmail(email) == 1) {
	    	 System.out.println("Email non valido.");
	    	 return "email";
	    } 
	    
	    if (username.length() > 10) {
	    	 System.out.println("Username non valido.");
	        return "username";
	    }
	    if (password.length() < 8 || password.length() > 20) {
	    	 System.out.println("Password non valido.");
	        return "password";
	    }
	    
	    if(CheckUtenteEsistente(matricola, email, username) == 1) {
	    	System.out.println("Utente già esistente.");
	    	return "Utente Esistente";
	    }
	    
	    System.out.println(nome + " " + cognome + " " + matricola + " " + email + " " + username + " " + password);
	    
	    // Se arrivi qui, tutti i campi sono validi
	    return "Tutti i campi sono correttamente compilati.";
	}
	
	private int isValidEmail(String email) {
		
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

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
}
