package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {
	
	private Controller controller = new Controller();


	public void SaveAnnuncio(Annuncio annuncio) {
		try {
			String matStudente = annuncio.getOggetto().getStudente().getMatricola();
			int idoggetto = controller.getIdByOggetto(annuncio.getOggetto());
			int idsede = controller.getIdBySede(annuncio.getSede());
			
		    Connection conn = ConnessioneDB.getConnection();
		    String queryCheck = "SELECT * FROM ANNUNCIO WHERE titoloannuncio = ? AND statoannuncio = ? AND fasciaorariainizio = ? AND fasciaorariafine = ? "
		    		+ "AND prezzo = ? AND tipologia = ? AND descrizioneannuncio = ? AND matstudente = ? AND idoggetto = ? AND idsede = ? AND giorni = ?";
		    PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
		    checkStatement.setString(1, annuncio.getTitoloAnnuncio());
		    checkStatement.setBoolean(2, annuncio.isStatoAnnuncio());
		    checkStatement.setString(3, annuncio.getFasciaOrariaInizio());
		    checkStatement.setString(4, annuncio.getFasciaOrariaFine());
		    checkStatement.setDouble(5, annuncio.getPrezzo());
		    checkStatement.setString(6, annuncio.getTipologia());
		    checkStatement.setString(7, annuncio.getDescrizioneAnnuncio());
		    checkStatement.setString(8, matStudente);
		    checkStatement.setInt(9, idoggetto);
		    checkStatement.setInt(10, idsede);		    
		    checkStatement.setString(11, annuncio.getGiorni());

		    
		    
		    ResultSet resultSet = checkStatement.executeQuery();
		    
		    if (resultSet.next()) {
		        System.out.println("Sede già esistente.");
		        resultSet.close();
		        checkStatement.close();
		    }
		    
		    else {
	            String insert = "INSERT INTO ANNUNCIO(titoloannuncio, statoannuncio, fasciaorariainizio, fasciaorariafine, " +
                        "prezzo, tipologia, descrizioneannuncio, matstudente, idoggetto, idsede, giorni) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        PreparedStatement statement = conn.prepareStatement(insert);
		        statement.setString(1, annuncio.getTitoloAnnuncio());
		        statement.setBoolean(2, annuncio.isStatoAnnuncio());
		        statement.setString(3, annuncio.getFasciaOrariaInizio());
		        statement.setString(4, annuncio.getFasciaOrariaFine());
		        statement.setDouble(5, annuncio.getPrezzo());
		        statement.setString(6, annuncio.getTipologia());
		        statement.setString(7, annuncio.getDescrizioneAnnuncio());
		        statement.setString(8, matStudente);
		        statement.setInt(9, idoggetto);
		        statement.setInt(10, idsede);
		        statement.setString(11, annuncio.getGiorni());
		        
			    int rowsInserted = statement.executeUpdate();
			    statement.close();
			    
	            if (rowsInserted == 0) {
	                System.out.println("Errore: inserimento fallito.");
	            }
		    }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public ArrayList<Annuncio> getAnnunci(String matricola) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO WHERE matstudente <> ? LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, matricola);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                annunci.add(new Annuncio(
                    rs.getString("titoloannuncio"),
                    rs.getBoolean("statoannuncio"),
                    rs.getString("fasciaOrariaInizio"),
                    rs.getString("fasciaOrariaFine"),
                    rs.getDouble("prezzo"),
                    rs.getString("tipologia"),
                    rs.getString("descrizioneAnnuncio"),
                    controller.getOggettoById(rs.getInt("idoggetto")),
                    controller.getSedeById(rs.getInt("idSede")),
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
