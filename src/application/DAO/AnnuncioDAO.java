package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.entity.Annuncio;
import application.entity.Oggetto;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {

    public ArrayList<Annuncio> getAnnunci() {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO AS A INNER JOIN OGGETTO AS O ON A.idoggetto = O.idoggetto LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
            	Oggetto oggetto = new Oggetto(
                    rs.getInt("idoggetto"),
                    rs.getString("immagineoggetto"),
                    rs.getString("categoria"),
                    rs.getString("descrizione"),
                    rs.getString("matstudente"),
                    rs.getString("nomeoggetto")
            	);
                
                Annuncio annuncio = new Annuncio(
                	rs.getInt("idannuncio"),
                    rs.getString("titoloannuncio"),
                    rs.getBoolean("statoannuncio"),
                    rs.getTimestamp("fasciaorariainizio").toLocalDateTime(),
                    rs.getTimestamp("fasciaorariafine").toLocalDateTime(),
                    rs.getDouble("prezzo"),
                    rs.getString("tipologia"),
                    rs.getString("descrizioneannuncio"),
                    oggetto
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
