package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.entity.Studente;
import application.resources.ConnessioneDB;

public class StudenteDAO {
	
	public int SaveStudente(Studente studente) {
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "INSERT INTO STUDENTE(matricola,email,nome,cognome,passkey,username) VALUES (?,?,?,?,?,?)";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, studente.getMatricola());
		    statement.setString(2, studente.getEmail());
		    statement.setString(3, studente.getNome());
		    statement.setString(4, studente.getCognome());
		    statement.setString(5, studente.getPasskey());
		    statement.setString(6, studente.getUsername());
		    
		    int rowsInserted = statement.executeUpdate();
		    statement.close();

            if (rowsInserted == 0) {
                System.out.println("Errore: inserimento fallito.");
                return 1;
            }

		}
		catch (SQLException ex) {
		    System.out.println("Errore nella connessione al database");
		    ex.printStackTrace();
		}
		
		return 0;
	}
	
	public int LoginStudente(String username, String password){
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
		}
		catch (SQLException ex) {
		    System.out.println("Errore nella connessione al database");
		    ex.printStackTrace();
		}
		
		System.out.println("Utente esistente.");
		
		return 0;
	}
	
	public int CheckUtenteEsistenteRegistrazione(String matricola, String email, String username) {
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
