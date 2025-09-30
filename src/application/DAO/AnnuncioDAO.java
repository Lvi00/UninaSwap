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
	
	private Controller controller = Controller.getController();

	public void SaveAnnuncio(Annuncio annuncio) {
		try {
			String matStudente = annuncio.getOggetto().getStudente().getMatricola();
			int idoggetto = controller.getIdByOggetto(annuncio.getOggetto());
			int idsede = controller.getIdBySede(annuncio.getSede());
			
		    Connection conn = ConnessioneDB.getConnection();
		    String queryCheck = "SELECT * FROM ANNUNCIO WHERE titoloannuncio = ? AND statoannuncio = ? AND fasciaorariainizio = ? AND fasciaorariafine = ? "
		    		+ "AND prezzo = ? AND tipologia = ? AND descrizioneannuncio = ? AND matstudente = ? AND idoggetto = ? AND idsede = ? AND giorni = ? AND dataPubblicazione = ?";
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
		    checkStatement.setTimestamp(12, annuncio.getDataPubblicazione());
		    
		    ResultSet resultSet = checkStatement.executeQuery();
		    
		    if (resultSet.next()) {
		        System.out.println("Sede già esistente.");
		        resultSet.close();
		        checkStatement.close();
		    }
		    
		    else {
	            String insert = "INSERT INTO ANNUNCIO(titoloannuncio, statoannuncio, fasciaorariainizio, fasciaorariafine, " +
                        "prezzo, tipologia, descrizioneannuncio, matstudente, idoggetto, idsede, giorni, dataPubblicazione) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		        statement.setTimestamp(12, annuncio.getDataPubblicazione());
		        
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
            String query = "SELECT * FROM ANNUNCIO WHERE matstudente <> ? AND statoannuncio = ? LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, matricola);
            statement.setBoolean(2, true);
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
                    rs.getString("giorni"),
                    rs.getTimestamp("dataPubblicazione")
                ));
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
    
    public ArrayList<Annuncio> getAnnunciStudente(String matricola) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO WHERE matstudente = ? ORDER BY statoannuncio DESC LIMIT 100";
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
                    rs.getString("giorni"),
                    rs.getTimestamp("dataPubblicazione")
                ));
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
    
    public ArrayList<Annuncio> getAnnunciByFiltri(String matricola, String keyword, String categoria, String tipologia) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query1 = "SELECT * FROM ANNUNCIO NATURAL JOIN OGGETTO WHERE statoannuncio = ? AND matstudente <> ? ";
            String query2 = "";
            String query3 = "";
            String query4 = "";

            if (!keyword.isEmpty()) {
                query2 = "AND LOWER(titoloannuncio) LIKE LOWER(?) ";
            }
            if (!categoria.equals("Nessuno")) {
                query3 = "AND categoria = ? ";
            }
            if (!tipologia.equals("Nessuno")) {
                query4 = "AND tipologia = ? ";
            }

            String query = query1 + query2 + query3 + query4 + "LIMIT 100;";

            PreparedStatement statement = conn.prepareStatement(query);

            int index = 1;
            //index++ ritorna prima il valore corrente, poi incrementa la variabile.
            statement.setBoolean(index++, true);
            statement.setString(index++, matricola);

            if (!query2.isEmpty()) {
                statement.setString(index++, keyword + "%"); // LIKE con wildcard
            }
            if (!query3.isEmpty()) {
                statement.setString(index++, categoria);
            }
            if (!query4.isEmpty()) {
                statement.setString(index++, tipologia);
            }

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
                    rs.getString("giorni"),
                    rs.getTimestamp("dataPubblicazione")
                ));
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
   
   public void cambiaStatoAnnuncio(Annuncio annuncio) {
	    try {
	        String matStudente = annuncio.getOggetto().getStudente().getMatricola();
	        int idOggetto = controller.getIdByOggetto(annuncio.getOggetto());
	        int idSede = controller.getIdBySede(annuncio.getSede());

	        Connection conn = ConnessioneDB.getConnection();
		    String query = "UPDATE ANNUNCIO SET statoannuncio = false WHERE titoloannuncio = ? AND statoannuncio = ? AND fasciaorariainizio = ? AND fasciaorariafine = ? "
		    		+ "AND prezzo = ? AND tipologia = ? AND descrizioneannuncio = ? AND matstudente = ? AND idoggetto = ? AND idsede = ? AND giorni = ? AND dataPubblicazione = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, annuncio.getTitoloAnnuncio());
            ps.setBoolean(2, annuncio.isStatoAnnuncio());
            ps.setString(3, annuncio.getFasciaOrariaInizio());
            ps.setString(4, annuncio.getFasciaOrariaFine());
            ps.setDouble(5, annuncio.getPrezzo());
            ps.setString(6, annuncio.getTipologia());
            ps.setString(7, annuncio.getDescrizioneAnnuncio());
            ps.setString(8, matStudente);
            ps.setInt(9, idOggetto);
            ps.setInt(10, idSede);
            ps.setString(11, annuncio.getGiorni());
            ps.setTimestamp(12, annuncio.getDataPubblicazione());

            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Annuncio aggiornato con successo.");
            } else {
                System.out.println("Nessun annuncio trovato da aggiornare.");
            }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
   
   public int getIdByAnnuncio(Annuncio annuncio) {
	   int id = 0;
	   
	   try {
		    String matStudente = annuncio.getOggetto().getStudente().getMatricola();
		    int idOggetto = controller.getIdByOggetto(annuncio.getOggetto());
		    int idSede = controller.getIdBySede(annuncio.getSede());
	
		    String query = "SELECT idannuncio FROM ANNUNCIO WHERE titoloannuncio = ? AND statoannuncio = ? "
		            + "AND fasciaorariainizio = ? AND fasciaorariafine = ? AND prezzo = ? "
		            + "AND tipologia = ? AND descrizioneannuncio = ? AND matstudente = ? "
		            + "AND idoggetto = ? AND idsede = ? AND giorni = ? AND dataPubblicazione = ?";
	
	    	Connection conn = ConnessioneDB.getConnection();
	        PreparedStatement statement = conn.prepareStatement(query);
	
	        statement.setString(1, annuncio.getTitoloAnnuncio());
	        statement.setBoolean(2, annuncio.isStatoAnnuncio());
	        statement.setString(3, annuncio.getFasciaOrariaInizio());
	        statement.setString(4, annuncio.getFasciaOrariaFine());
	        statement.setDouble(5, annuncio.getPrezzo());
	        statement.setString(6, annuncio.getTipologia());
	        statement.setString(7, annuncio.getDescrizioneAnnuncio());
	        statement.setString(8, matStudente);
	        statement.setInt(9, idOggetto);
	        statement.setInt(10, idSede);
	        statement.setString(11, annuncio.getGiorni());
	        statement.setTimestamp(12, annuncio.getDataPubblicazione());
	
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            id = rs.getInt("idannuncio");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }

	    return id;
	}
   
   public int rimuoviAnnuncio(Annuncio annuncio) {
	    try {
	        String matStudente = annuncio.getOggetto().getStudente().getMatricola();
	        int idOggetto = controller.getIdByOggetto(annuncio.getOggetto());
	        int idSede = controller.getIdBySede(annuncio.getSede());

	        Connection conn = ConnessioneDB.getConnection();

	        // Trova l'id dell'annuncio
	        String queryCheck = "SELECT idannuncio FROM ANNUNCIO WHERE titoloannuncio = ? AND statoannuncio = ? AND fasciaorariainizio = ? AND fasciaorariafine = ? "
	                + "AND prezzo = ? AND tipologia = ? AND descrizioneannuncio = ? AND matstudente = ? AND idoggetto = ? AND idsede = ? AND giorni = ? AND dataPubblicazione = ?";
	        PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
	        checkStatement.setString(1, annuncio.getTitoloAnnuncio());
	        checkStatement.setBoolean(2, annuncio.isStatoAnnuncio());
	        checkStatement.setString(3, annuncio.getFasciaOrariaInizio());
	        checkStatement.setString(4, annuncio.getFasciaOrariaFine());
	        checkStatement.setDouble(5, annuncio.getPrezzo());
	        checkStatement.setString(6, annuncio.getTipologia());
	        checkStatement.setString(7, annuncio.getDescrizioneAnnuncio());
	        checkStatement.setString(8, matStudente);
	        checkStatement.setInt(9, idOggetto);
	        checkStatement.setInt(10, idSede);
	        checkStatement.setString(11, annuncio.getGiorni());
	        checkStatement.setTimestamp(12, annuncio.getDataPubblicazione());

	        ResultSet resultSet = checkStatement.executeQuery();

	        if (!resultSet.next()) {
	            System.out.println("Errore: annuncio non trovato.");
	            resultSet.close();
	            checkStatement.close();
	            return 1;
	        }

	        int idAnnuncio = resultSet.getInt("idannuncio");
	        resultSet.close();
	        checkStatement.close();
	        
	        controller.rimuoviOfferte(idAnnuncio);
	        	        	        	        
	        String deleteAnnuncio = "DELETE FROM ANNUNCIO WHERE idAnnuncio = ?";
	        PreparedStatement deleteAnnuncioStmt = conn.prepareStatement(deleteAnnuncio);
	        deleteAnnuncioStmt.setInt(1, idAnnuncio);
	        int deletedRows = deleteAnnuncioStmt.executeUpdate();
	        deleteAnnuncioStmt.close();

	        if (deletedRows == 0) {
	            System.out.println("Errore: eliminazione annuncio fallita.");
	            return 1;
	        }
	        
	        controller.rimuoviOggetto(idOggetto);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
   
   public Annuncio getAnnuncioById(int idAnnuncio) {
	   try {
	        Connection conn = ConnessioneDB.getConnection();
	        String query = "SELECT * FROM ANNUNCIO WHERE idannuncio = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setInt(1, idAnnuncio);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            Annuncio annuncio = new Annuncio(
	                rs.getString("titoloannuncio"),
	                rs.getBoolean("statoannuncio"),
	                rs.getString("fasciaOrariaInizio"),
	                rs.getString("fasciaOrariaFine"),
	                rs.getDouble("prezzo"),
	                rs.getString("tipologia"),
	                rs.getString("descrizioneAnnuncio"),
	                controller.getOggettoById(rs.getInt("idoggetto")),
	                controller.getSedeById(rs.getInt("idSede")),
	                rs.getString("giorni"),
	                rs.getTimestamp("dataPubblicazione")
	            );
	            
	            return annuncio;
	        }

	        rs.close();
	        statement.close();
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }

	    return null;
   }
}
