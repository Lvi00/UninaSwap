package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.InterfacePostgresDAO.InterfaceOggettoDAO;
import application.entity.Oggetto;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class OggettoDAO implements InterfaceOggettoDAO {	
	public int SaveOggetto(Oggetto oggetto) {
	    int idOggettoInserito = -1;

	    try {
	        Connection conn = ConnessioneDB.getConnection();
	        String matStudente = oggetto.getStudente().getMatricola();
	        String queryCheck = "SELECT * FROM OGGETTO WHERE immagineoggetto = ? AND categoria = ? AND descrizione = ? AND matstudente = ?";
	        PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
	        checkStatement.setString(1, oggetto.getImmagineOggetto());
	        checkStatement.setString(2, oggetto.getCategoria());
	        checkStatement.setString(3, oggetto.getDescrizione());
	        checkStatement.setString(4, matStudente);

	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Oggetto già esistente.");
	            idOggettoInserito = resultSet.getInt("idoggetto");
	        }
	        
	        else{
	        	String queryInsert = "INSERT INTO OGGETTO (immagineoggetto, categoria, descrizione, matstudente) VALUES (?, ?, ?, ?) RETURNING idoggetto";
	        	
	            PreparedStatement insertStatement = conn.prepareStatement(queryInsert);
	            insertStatement.setString(1, oggetto.getImmagineOggetto());
	            insertStatement.setString(2, oggetto.getCategoria());
	            insertStatement.setString(3, oggetto.getDescrizione());
	            insertStatement.setString(4, matStudente);
	
	            ResultSet rsInsert = insertStatement.executeQuery();
	            if (rsInsert.next()) {
	                idOggettoInserito = rsInsert.getInt("idoggetto");
		        }
	            
            	insertStatement.close();
	        }

	        resultSet.close();
	        checkStatement.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return idOggettoInserito;
	}
	
    public Oggetto getOggettoById(int idOggetto) {
    	Oggetto oggetto = null;
    	
        try {        	
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OGGETTO AS O INNER JOIN STUDENTE AS S ON O.matstudente = S.matricola WHERE idoggetto = ?";
            PreparedStatement statement = conn.prepareStatement(query);
		    statement.setInt(1, idOggetto);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                Studente studente = new Studente(
					rs.getString("matricola"),
					rs.getString("email"),
					rs.getString("nome"),
					rs.getString("cognome"),
					rs.getString("username")
        		);
                
                oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					studente
				);
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return oggetto;
    }
    
    public int rimuoviOggettoByIdOggetto(int idOggetto) {
        try {
            Connection conn = ConnessioneDB.getConnection();
            String checkAnnuncio = "SELECT 1 FROM ANNUNCIO NATURAL JOIN OGGETTO WHERE idoggetto = ? LIMIT 1";
            PreparedStatement checkStmt = conn.prepareStatement(checkAnnuncio);
            checkStmt.setInt(1, idOggetto);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                rs.close();
                checkStmt.close();
                return 1; 
            }

            String deleteOfferte = "DELETE FROM OGGETTO WHERE idoggetto = ?";
            PreparedStatement deleteOfferteStmt = conn.prepareStatement(deleteOfferte);
            deleteOfferteStmt.setInt(1, idOggetto);
            int rowsAffected = deleteOfferteStmt.executeUpdate();
            deleteOfferteStmt.close();
            
            if (rowsAffected == 0) {
                return 1; 
            }
		}
        catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
        return 0;
    }
    
    public int UpdateOggetto(Oggetto oggetto, String pathImmagine, String categoria, String descrizione) {
        try {
        	Connection conn = ConnessioneDB.getConnection();
        	String query = "UPDATE OGGETTO SET immagineoggetto = ?, categoria = ?, descrizione = ? WHERE idoggetto = ?";
        	PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, pathImmagine);
            statement.setString(2, categoria);
            statement.setString(3, descrizione);
            statement.setInt(4, oggetto.getIdOggetto());

            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Aggiornamento completato con successo!");
                
            } else {
                System.out.println("Nessun record aggiornato (ID non trovato).");
                return 1;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 1;
        }
        
        return 0;
    }
    
    public int UpdateImmagineOggetto(Oggetto oggetto, String path) {
    	try {
			Connection conn = ConnessioneDB.getConnection();
			String query = "UPDATE OGGETTO SET immagineoggetto = ? WHERE idoggetto = ?";
			PreparedStatement statement = conn.prepareStatement(query); 

			statement.setString(1, path);
			statement.setInt(2, oggetto.getIdOggetto());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Aggiornamento immagine completato con successo!");
				
			} else {
				System.out.println("Nessun record aggiornato (ID non trovato).");
				return 1;
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 1;
		}
		
		return 0;
    }
}