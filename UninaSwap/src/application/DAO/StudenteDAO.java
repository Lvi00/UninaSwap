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

            if (rowsInserted > 0) {
                System.out.println("Studente inserito con successo.");
                return 0;
            } else {
                System.out.println("Errore: inserimento fallito.");
                return 1; // codice errore: inserimento fallito
            }

		}
		catch (SQLException ex) {
		    System.out.println("Errore nella connessione al database");
		    ex.printStackTrace();
		}
		return 0;
	}
}
