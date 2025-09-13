package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.control.Controller;
import application.entity.Oggetto;
import application.resources.ConnessioneDB;

public class OggettoDAO {
	private Controller controller = new Controller();
	
	public int SaveOggetto(Oggetto oggetto) {
	    int idOggettoInserito = 0;

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
	        } else {
	            String query = "INSERT INTO OGGETTO(immagineoggetto,categoria,descrizione,matstudente) VALUES (?,?,?,?)";
	            PreparedStatement statement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	            statement.setString(1, oggetto.getImmagineOggetto());
	            statement.setString(2, oggetto.getCategoria());
	            statement.setString(3, oggetto.getDescrizione());
	            statement.setString(4, matStudente);

	            int rowsInserted = statement.executeUpdate();

	            if (rowsInserted == 0) {
	                System.out.println("Errore: inserimento fallito.");
	            } else {
	                ResultSet generatedKeys = statement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    idOggettoInserito = generatedKeys.getInt(1);
	                }
	                generatedKeys.close();
	            }
	            statement.close();
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
	    String matStudente = oggetto.getStudente().getMatricola();
        String query = "SELECT idoggetto FROM OGGETTO WHERE immagineoggetto = ? AND categoria = ? AND descrizione = ? AND matstudente = ?";

        try {
        	Connection conn = ConnessioneDB.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, oggetto.getImmagineOggetto());
            statement.setString(2, oggetto.getCategoria());
            statement.setString(3, oggetto.getDescrizione());
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
            String deleteOfferte = "DELETE FROM OGGETTO WHERE idoggetto = ?";
            PreparedStatement deleteOfferteStmt = conn.prepareStatement(deleteOfferte);
			deleteOfferteStmt.setInt(1, idOggetto);
	        deleteOfferteStmt.executeUpdate();
	        deleteOfferteStmt.close();
		}
        catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
        return 0;
    }
}