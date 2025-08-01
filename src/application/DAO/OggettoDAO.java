package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.control.Controller;
import application.entity.Oggetto;
import application.resources.ConnessioneDB;

public class OggettoDAO {
	Controller controller = new Controller();

    public Oggetto getOggetto(int idOggetto) {
    	Oggetto oggetto = null;
    	
        try {        	
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OGGETTO WHERE idoggetto = ?";
            PreparedStatement statement = conn.prepareStatement(query);
		    statement.setInt(1, idOggetto);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
            	oggetto = new Oggetto(
					rs.getInt("idoggetto"),
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					rs.getString("matstudente"),
					rs.getString("nomeoggetto")
				);
            }

            rs.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return oggetto;
    }
}
