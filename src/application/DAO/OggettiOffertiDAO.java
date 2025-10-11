package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.resources.ConnessioneDB;

public class OggettiOffertiDAO {
	public void SaveOggettoOfferto(int idOfferta, int idOggetto) {
		try {
			Connection conn = ConnessioneDB.getConnection();
			
			String insert = "INSERT INTO OGGETTIOFFERTI (idofferta, idoggetto) VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(insert);
			statement.setInt(1, idOfferta);
			statement.setInt(2, idOggetto);
			
			statement.executeUpdate();
			statement.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList<Integer> rimuoviOggettiOffertiByIdOfferta(int idOfferta) {
	    try {
	        Connection conn = ConnessioneDB.getConnection();

	        String selectOggetti = "SELECT idOggetto FROM OggettiOfferti WHERE idOfferta = ?";
	        PreparedStatement selectStmt = conn.prepareStatement(selectOggetti);
	        selectStmt.setInt(1, idOfferta);
	        ResultSet rs = selectStmt.executeQuery();

	        ArrayList<Integer> oggettiDaRimuovere = new ArrayList<Integer>();
	        
	        while (rs.next()) {
	            oggettiDaRimuovere.add(rs.getInt("idOggetto"));
	        }
	        rs.close();
	        selectStmt.close();

	        String deleteOfferte = "DELETE FROM OggettiOfferti WHERE idOfferta = ?";
	        PreparedStatement deleteOfferteStmt = conn.prepareStatement(deleteOfferte);
	        deleteOfferteStmt.setInt(1, idOfferta);
	        deleteOfferteStmt.executeUpdate();
	        deleteOfferteStmt.close();
	        
	        return oggettiDaRimuovere;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void rimuoviOggettoOffertoById(int idOggetto, int idOfferta) {
		try {
			Connection conn = ConnessioneDB.getConnection();
			
			String delete = "DELETE FROM OGGETTIOFFERTI WHERE idofferta = ? AND idoggetto = ?";
			PreparedStatement statement = conn.prepareStatement(delete);
			statement.setInt(1, idOfferta);
			statement.setInt(2, idOggetto);
			
			statement.executeUpdate();
			statement.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}