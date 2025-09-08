package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}