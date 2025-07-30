package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import application.entity.Annuncio;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {

    public ArrayList<Annuncio> getAnnunci() {
        ArrayList<Annuncio> annunci = new ArrayList<>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Annuncio annuncio = new Annuncio(
                    rs.getString("titoloannuncio"),
                    rs.getBoolean("statoannuncio"),
                    rs.getTimestamp("fasciaorariainizio").toLocalDateTime(),
                    rs.getTimestamp("fasciaorariafine").toLocalDateTime(),
                    rs.getDouble("prezzo"),
                    rs.getString("tipologia"),
                    rs.getString("descrizioneannuncio")
                );
                annunci.add(annuncio);
            }

            rs.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
}
