package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.control.Controller;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class StudenteDAO{
	
	Controller controller = new Controller();
	
	public int Save(Studente studente) {
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "INSERT INTO STUDENTE(matricola,email,nome,cognome,passkey,username) VALUES (?,?,?,?,?)";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, studente.getMatricola());
		    statement.setString(2, studente.getEmail());
		    statement.setString(3, studente.getNome());
		    statement.setString(4, studente.getCognome());
		    statement.setString(5, studente.getUsername());
		    
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
	
	public Studente LoginStudente(String username, String password){
		
		Studente studente = null;
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "SELECT * FROM STUDENTE WHERE username = ? AND passkey = ?";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, username);
		    statement.setString(2, password);
	        ResultSet rs = statement.executeQuery();
	        if (!rs.next()) {
	            System.out.println("Utente non esistente.");
	            rs.close();
	            statement.close();
	            return null;
	        }
	        
        	System.out.println("Utente esistente.");
            String Matricola = rs.getString(1);
            String Email = rs.getString(2);
            String Nome = rs.getString(3);
            String Cognome = rs.getString(4);
            String Username = rs.getString(6);
            studente = new Studente (Matricola,Email,Nome,Cognome,Username);
            
            rs.close();
            statement.close();

		}
		catch (SQLException ex) {
		    ex.printStackTrace();
		}
	
		return studente;
	}
	
	public int CheckStudenteEsistente(String matricola, String email, String username) {
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
		    ex.printStackTrace();
		}
		
		return 0;
	}
}
