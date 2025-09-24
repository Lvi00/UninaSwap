package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.control.Controller;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class StudenteDAO{
	
	private Controller controller = new Controller();
	
	public int Save(Studente studente, String password) {
		try {
	        Connection conn = ConnessioneDB.getConnection();
	        String query = "INSERT INTO STUDENTE(matricola,email,nome,cognome,passkey,username,immagineProfilo) VALUES (?,?,?,?,?,?,?)";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, studente.getMatricola());
	        statement.setString(2, studente.getEmail());
	        statement.setString(3, studente.getNome());
	        statement.setString(4, studente.getCognome());
	        statement.setString(5, password);
	        statement.setString(6, studente.getUsername());

	        String defaultImage = "../IMG/immaginiProfilo/account1.png"; 
	        statement.setString(7, defaultImage);
		    
		    int rowsInserted = statement.executeUpdate();
		    statement.close();
		    
            if (rowsInserted == 0) {
                System.out.println("Errore: inserimento fallito.");
                return 1;
            }
		}
		catch (SQLException ex) {
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
            String ImmagineProfilo = rs.getString(7);
            studente = new Studente (Matricola, Email, Nome, Cognome, Username);
            studente.setImmagine(ImmagineProfilo);
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
	        
	        int risultato = rs.next() ? 1 : 0;
	        
	        if(risultato == 1) return 1;
	        
            rs.close();
            statement.close();
		}
		catch (SQLException ex) {
		    ex.printStackTrace();
		}
		
		return 0;
	}

	public Studente getStudenteByMatricola(String matricola) {
	    Studente studente = null;
	    
	    try {
	        Connection conn = ConnessioneDB.getConnection();
	        String query = "SELECT * FROM STUDENTE WHERE matricola = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, matricola);
	        
	        ResultSet rs = statement.executeQuery();
	        
	        if (rs.next()) {
	            String Email = rs.getString("email");
	            String Nome = rs.getString("nome");
	            String Cognome = rs.getString("cognome");
	            String Username = rs.getString("username");
	            String ImmagineProfilo = rs.getString("immagineProfilo");
	            
	            studente = new Studente(matricola, Email, Nome, Cognome, Username);
	            studente.setImmagine(ImmagineProfilo);
	        }
	        
	        rs.close();
	        statement.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    
	    return studente;
	}

	public void cambiaFoto(String matricola, String percorsoImmagine) {
	    try {
	        Connection conn = ConnessioneDB.getConnection();
	        String query = "UPDATE STUDENTE SET immagineProfilo = ? WHERE matricola = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, percorsoImmagine);
	        statement.setString(2, matricola);

	        int rowsUpdated = statement.executeUpdate();
	        statement.close();

	        if (rowsUpdated == 0) {
	            System.err.println("Aggiornamento immagine fallito: matricola non trovata.");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
}
