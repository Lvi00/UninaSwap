package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {
	
	Controller controller = new Controller();

    public ArrayList<Annuncio> getAnnunci() {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                annunci.add(new Annuncio(
	                    rs.getString("titoloannuncio"),
	                    rs.getBoolean("statoannuncio"),
	                    rs.getTimestamp("fasciaorariainizio").toLocalDateTime(),
	                    rs.getTimestamp("fasciaorariafine").toLocalDateTime(),
	                    rs.getDouble("prezzo"),
	                    rs.getString("tipologia"),
	                    rs.getString("descrizioneannuncio"),
	                    controller.getOggetto(rs.getInt("idoggetto"))
	                )
                );
            }

            rs.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
}
