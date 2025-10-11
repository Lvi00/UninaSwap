package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.control.Controller;
import application.entity.Oggetto;
import application.resources.ConnessioneDB;

public class OggettoDAO {
	private Controller controller = Controller.getController();
	
	public int SaveOggetto(Oggetto oggetto) {
	    int idOggettoInserito = -1;

	    try {
	        Connection conn = ConnessioneDB.getConnection();
	        String matStudente = controller.getMatricola(controller.getStudenteOggetto(oggetto));
	        String queryCheck = "SELECT * FROM OGGETTO WHERE immagineoggetto = ? AND categoria = ? AND descrizione = ? AND matstudente = ?";
	        PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
	        checkStatement.setString(1, controller.getImmagineOggetto(oggetto));
	        checkStatement.setString(2, controller.getCategoriaOggetto(oggetto));
	        checkStatement.setString(3, controller.getDescrizioneOggetto(oggetto));
	        checkStatement.setString(4, matStudente);

	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Oggetto già esistente.");
	            idOggettoInserito = resultSet.getInt("idoggetto");
	        }
	        
	        else{
	        	String queryInsert = "INSERT INTO OGGETTO (immagineoggetto, categoria, descrizione, matstudente) VALUES (?, ?, ?, ?) RETURNING idoggetto";
	        	
	            PreparedStatement insertStatement = conn.prepareStatement(queryInsert);
	            insertStatement.setString(1, controller.getImmagineOggetto(oggetto));
	            insertStatement.setString(2, controller.getCategoriaOggetto(oggetto));
	            insertStatement.setString(3, controller.getDescrizioneOggetto(oggetto));
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
            String query = "SELECT * FROM OGGETTO WHERE idoggetto = ?";
            PreparedStatement statement = conn.prepareStatement(query);
		    statement.setInt(1, idOggetto);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {            	
            	oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
	            	controller.getStudenteByMatricola(rs.getString("matstudente"))
				);
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return oggetto;
    }
    
    public int getIdByOggetto(Oggetto oggetto) {
    	int id = 0;
    	try {
		    String matStudente = controller.getMatricola(controller.getStudenteOggetto(oggetto));
	        String query = "SELECT idoggetto FROM OGGETTO WHERE immagineoggetto = ? AND categoria = ? AND descrizione = ? AND matstudente = ?";
	        
        	Connection conn = ConnessioneDB.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, controller.getImmagineOggetto(oggetto));
            statement.setString(2, controller.getCategoriaOggetto(oggetto));
            statement.setString(3, controller.getDescrizioneOggetto(oggetto));
            statement.setString(4, matStudente);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idoggetto");
            }
        

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
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

            int id = getIdByOggetto(oggetto);

            statement.setString(1, pathImmagine);
            statement.setString(2, categoria);
            statement.setString(3, descrizione);
            statement.setInt(4, id);

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

			int id = getIdByOggetto(oggetto);

			statement.setString(1, path);
			statement.setInt(2, id);

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