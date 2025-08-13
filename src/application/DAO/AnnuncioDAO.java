package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {
	
	private Controller controller = new Controller();

    public ArrayList<Annuncio> getAnnunci(String matricola) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO WHERE matstudente <> ? LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, matricola);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // Conversione dei campi TIME in LocalTime
                LocalTime fasciaInizio = rs.getTime("fasciaorariainizio").toLocalTime();
                LocalTime fasciaFine = rs.getTime("fasciaorariafine").toLocalTime();

                annunci.add(new Annuncio(
                    rs.getString("titoloannuncio"),
                    rs.getBoolean("statoannuncio"),
                    fasciaInizio,
                    fasciaFine,
                    rs.getDouble("prezzo"),
                    rs.getString("tipologia"),
                    rs.getString("descrizioneannuncio"),
                    controller.getOggetto(rs.getInt("idoggetto")),
                    rs.getString("giorni")
                ));
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
}
