package application.PostgresDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class AnnuncioDAO {
	public int SaveAnnuncio(Annuncio annuncio, Oggetto oggetto, Sede sede) {
		try {			
		    Connection conn = ConnessioneDB.getConnection();
		    
		    String queryCheck = "SELECT * FROM ANNUNCIO AS A INNER JOIN SEDE AS S ON A.idSede = S.idSede INNER JOIN OGGETTO AS O ON A.idOggetto = O.idOggetto WHERE A.titoloannuncio = ? AND A.statoannuncio = ? AND A.fasciaorariainizio = ? AND A.fasciaorariafine = ? "
    		+ "AND A.prezzo = ? AND A.tipologia = ? AND A.descrizioneannuncio = ? AND O.matstudente = ? AND O.idoggetto = ? AND S.idsede = ? AND A.giorni = ? AND A.dataPubblicazione = ?";
		    
		    PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
		    checkStatement.setString(1, annuncio.getTitoloAnnuncio());
		    checkStatement.setBoolean(2, annuncio.isStatoAnnuncio());
		    checkStatement.setString(3, annuncio.getFasciaOrariaInizio());
		    checkStatement.setString(4, annuncio.getFasciaOrariaFine());
		    checkStatement.setDouble(5, annuncio.getPrezzo());
		    checkStatement.setString(6, annuncio.getTipologia());
		    checkStatement.setString(7, annuncio.getDescrizioneAnnuncio());
		    checkStatement.setString(8, oggetto.getStudente().getMatricola());
		    checkStatement.setInt(9, oggetto.getIdOggetto());
		    checkStatement.setInt(10, sede.getIdSede());		    
		    checkStatement.setString(11, annuncio.getGiorni());
		    checkStatement.setTimestamp(12, annuncio.getDataPubblicazione());
		    
		    ResultSet resultSet = checkStatement.executeQuery();
		    
		    if (resultSet.next()) {
		    	System.out.println("Annuncio già presente nel database. Inserimento annullato.");
		        resultSet.close();
		        checkStatement.close();
		        return 1;
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
	            statement.setString(8, oggetto.getStudente().getMatricola());
	            statement.setInt(9, oggetto.getIdOggetto());
	            statement.setInt(10, sede.getIdSede());		    
	            statement.setString(11, annuncio.getGiorni());
	            statement.setTimestamp(12, annuncio.getDataPubblicazione());

	            int rowsInserted = statement.executeUpdate();
	            statement.close();
			    
	            if (rowsInserted == 0) {
	                System.out.println("Errore: inserimento fallito.");
	                return 1;
	            }
		    }
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
	
    public ArrayList<Annuncio> getAnnunci(Studente studente) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO AS A NATURAL JOIN SEDE AS S NATURAL JOIN OGGETTO AS O WHERE O.matstudente <> ? AND A.statoannuncio = ? LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, studente.getMatricola());
            statement.setBoolean(2, true);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
            	Oggetto oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					studente
    			);
            	
            	oggetto.setIdOggetto(rs.getInt("idOggetto"));
            	
            	Sede sede = new Sede(
					rs.getString("ptop"),
					rs.getString("descrizione"),
					rs.getString("civico"),
					rs.getString("cap")
				);
            	sede.setIdSede(rs.getInt("idSede"));
            	
            	Annuncio annuncio = new Annuncio(
					rs.getString("titoloannuncio"),
					rs.getBoolean("statoannuncio"),
					rs.getString("fasciaOrariaInizio"),
					rs.getString("fasciaOrariaFine"),
					rs.getDouble("prezzo"),
					rs.getString("tipologia"),
					rs.getString("descrizioneAnnuncio"),
					oggetto,
					sede,
					rs.getString("giorni"),
					rs.getTimestamp("dataPubblicazione")
				);
            	
            	annuncio.setIdAnnuncio(rs.getInt("idannuncio"));
            	
            	annunci.add(annuncio);
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
    
    public ArrayList<Annuncio> getAnnunciStudente(Studente studente) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM ANNUNCIO AS A NATURAL JOIN SEDE AS S NATURAL JOIN OGGETTO AS O WHERE O.matstudente = ? ORDER BY A.statoannuncio DESC LIMIT 100";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, studente.getMatricola());
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
            	Oggetto oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					studente
    			);
            	
            	oggetto.setIdOggetto(rs.getInt("idOggetto"));
            	
            	Sede sede = new Sede(
					rs.getString("ptop"),
					rs.getString("descrizione"),
					rs.getString("civico"),
					rs.getString("cap")
				);
            	sede.setIdSede(rs.getInt("idSede"));
            	
            	Annuncio annuncio = new Annuncio(
					rs.getString("titoloannuncio"),
					rs.getBoolean("statoannuncio"),
					rs.getString("fasciaOrariaInizio"),
					rs.getString("fasciaOrariaFine"),
					rs.getDouble("prezzo"),
					rs.getString("tipologia"),
					rs.getString("descrizioneAnnuncio"),
					oggetto,
					sede,
					rs.getString("giorni"),
					rs.getTimestamp("dataPubblicazione")
				);
            	
            	annuncio.setIdAnnuncio(rs.getInt("idannuncio"));
            	
            	annunci.add(annuncio);
            }

            rs.close();
            statement.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return annunci;
    }
    
    public ArrayList<Annuncio> getAnnunciByFiltri(Studente studente, String keyword, String categoria, String tipologia) {
        ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query1 = "SELECT * FROM ANNUNCIO AS A NATURAL JOIN SEDE AS S NATURAL JOIN OGGETTO AS O WHERE A.statoannuncio = ? AND O.matstudente <> ?";
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
            
            statement.setBoolean(index++, true);
            statement.setString(index++, studente.getMatricola());

            if (!query2.isEmpty()) {
                statement.setString(index++, keyword + "%");
            }
            if (!query3.isEmpty()) {
                statement.setString(index++, categoria);
            }
            if (!query4.isEmpty()) {
                statement.setString(index++, tipologia);
            }

            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
            	Oggetto oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					studente
    			);
            	
            	oggetto.setIdOggetto(rs.getInt("idOggetto"));
            	
            	Sede sede = new Sede(
					rs.getString("ptop"),
					rs.getString("descrizione"),
					rs.getString("civico"),
					rs.getString("cap")
				);
            	sede.setIdSede(rs.getInt("idSede"));
            	
            	Annuncio annuncio = new Annuncio(
					rs.getString("titoloannuncio"),
					rs.getBoolean("statoannuncio"),
					rs.getString("fasciaOrariaInizio"),
					rs.getString("fasciaOrariaFine"),
					rs.getDouble("prezzo"),
					rs.getString("tipologia"),
					rs.getString("descrizioneAnnuncio"),
					oggetto,
					sede,
					rs.getString("giorni"),
					rs.getTimestamp("dataPubblicazione")
				);
            	
            	annuncio.setIdAnnuncio(rs.getInt("idannuncio"));
            	
            	annunci.add(annuncio);
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
	        Connection conn = ConnessioneDB.getConnection();
		    String query = "UPDATE ANNUNCIO SET statoannuncio = false WHERE idAnnuncio = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, annuncio.getIdAnnuncio());

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
   
   public int rimuoviAnnuncio(Annuncio annuncio) {
	    try {
	    	Connection conn = ConnessioneDB.getConnection();
	        	        	        	        
	        String deleteAnnuncio = "DELETE FROM ANNUNCIO WHERE idAnnuncio = ?";
	        PreparedStatement deleteAnnuncioStmt = conn.prepareStatement(deleteAnnuncio);
	        deleteAnnuncioStmt.setInt(1, annuncio.getIdAnnuncio());
	        
	        int deletedRows = deleteAnnuncioStmt.executeUpdate();
	        deleteAnnuncioStmt.close();

	        if (deletedRows == 0) {
	            System.out.println("Errore: eliminazione annuncio fallita.");
	            return 1;
	        }


	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
   
   public Annuncio getAnnuncioById(Studente studente, int idAnnuncio) {
	   try {
	        Connection conn = ConnessioneDB.getConnection();
	        String query = "SELECT * FROM ANNUNCIO AS A NATURAL JOIN SEDE AS S NATURAL JOIN OGGETTO AS O WHERE A.idannuncio = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setInt(1, idAnnuncio);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
            	Oggetto oggetto = new Oggetto(
					rs.getString("immagineoggetto"),
					rs.getString("categoria"),
					rs.getString("descrizione"),
					studente
    			);
            	
            	oggetto.setIdOggetto(rs.getInt("idOggetto"));
            	
        		Sede sede = new Sede(
					rs.getString("ptop"),
					rs.getString("descrizione"),
					rs.getString("civico"),
					rs.getString("cap")
				);
            	sede.setIdSede(rs.getInt("idSede"));
	            	
	            Annuncio annuncio = new Annuncio(
	                rs.getString("titoloannuncio"),
	                rs.getBoolean("statoannuncio"),
	                rs.getString("fasciaOrariaInizio"),
	                rs.getString("fasciaOrariaFine"),
	                rs.getDouble("prezzo"),
	                rs.getString("tipologia"),
	                rs.getString("descrizioneAnnuncio"),
	                oggetto,
	                sede,
	                rs.getString("giorni"),
	                rs.getTimestamp("dataPubblicazione")
	            );
	            
	            annuncio.setIdAnnuncio(rs.getInt("idannuncio"));
	            
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