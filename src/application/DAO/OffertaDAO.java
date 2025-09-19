package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.Oggetto;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class OffertaDAO {
	
	private Controller controller = new Controller();

    public int SaveOfferta(Offerta offerta, String matricola, String motivazione) {
    	
    	//Codici di ritorno:
    	//-1: errore generico
    	//0: tutto ok nel caso di offerta di tipo Regalo o Vendita
    	//idOfferta: offerta di tipo Scambio inserita con successo
    	
        int returnValue = 0;
        
    	try {
            String matStudente = matricola;
            int idannuncio = new AnnuncioDAO().getIdByAnnuncio(offerta.getAnnuncio());

            Connection conn = ConnessioneDB.getConnection();

            // Controllo duplicati: uno studente può fare solo un'offerta per annuncio
            String queryDuplicate = "SELECT * FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            PreparedStatement dupStatement = conn.prepareStatement(queryDuplicate);
            dupStatement.setString(1, matStudente);
            dupStatement.setInt(2, idannuncio);

            ResultSet dupResult = dupStatement.executeQuery();
            if (dupResult.next()) {
                System.out.println("Offerta già esistente per questo annuncio.");
                dupResult.close();
                dupStatement.close();
                return -1;
            }
            dupResult.close();
            dupStatement.close();

            // Imposta motivazione
            if (!"Regalo".equalsIgnoreCase(offerta.getTipologia())) {
                motivazione = "Nessuna";
            }

            // Inserimento offerta
            String insert = "INSERT INTO OFFERTA(statoofferta, prezzoofferta, tipologia, matstudente, idannuncio, motivazione, dataPubblicazione) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(insert);
            
            if ("Scambio".equalsIgnoreCase(offerta.getTipologia())) {
                // per Scambio, abilita il ritorno dell'ID generato
                statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            } else {
                statement = conn.prepareStatement(insert);
            }
            
            statement.setString(1, offerta.getStatoOfferta());
            statement.setDouble(2, offerta.getPrezzoOfferta());
            statement.setString(3, offerta.getTipologia());
            statement.setString(4, matStudente);
            statement.setInt(5, idannuncio);
            statement.setString(6, motivazione);
            statement.setTimestamp(7, offerta.getDataPubblicazione());

            int rowsInserted = statement.executeUpdate();

            if ("Scambio".equalsIgnoreCase(offerta.getTipologia())) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    returnValue = generatedKeys.getInt(1);
                }
                generatedKeys.close();
            }
            
            statement.close();

            if (rowsInserted == 0) {
                System.out.println("Errore: inserimento fallito.");
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        
        if ("Scambio".equalsIgnoreCase(offerta.getTipologia())) return returnValue;
        
        return 0;
    }
    
    public int rimuoviOfferteByIdAnnuncio(int idAnnuncio) {
        try {
            Connection conn = ConnessioneDB.getConnection();
            
            String selectOfferte = "SELECT idOfferta FROM OFFERTA WHERE idAnnuncio = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectOfferte);
            selectStmt.setInt(1, idAnnuncio);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                controller.rimuoviOggettiOfferti(rs.getInt("idOfferta"));
            }
            rs.close();
            selectStmt.close();
            
            String deleteOfferte = "DELETE FROM OFFERTA WHERE idAnnuncio = ?";
            PreparedStatement deleteOfferteStmt = conn.prepareStatement(deleteOfferte);
            deleteOfferteStmt.setInt(1, idAnnuncio);
            deleteOfferteStmt.executeUpdate();
            deleteOfferteStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public ArrayList<Offerta> getOffertebyAnnuncio(Annuncio a) {
        ArrayList<Offerta> offerte = new ArrayList<>();

        try {
        	Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OFFERTA WHERE idannuncio = ? ORDER BY statoofferta";
            PreparedStatement selectStmt = conn.prepareStatement(query);
            selectStmt.setInt(1, controller.getIdByAnnuncio(a)); 
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                Offerta offerta = new Offerta(
            		rs.getString("tipologia"),
                    rs.getTimestamp("dataPubblicazione"),
                    a
                );
                
                offerta.setPrezzoOfferta(rs.getDouble("prezzoofferta"));
                offerta.setStatoOfferta(rs.getString("statoofferta"));
                offerta.setMotivazione(rs.getString("motivazione"));
                offerta.setStudente(controller.getStudenteByMatricola(rs.getString("matstudente")));
                
                offerte.add(offerta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offerte;
    }
    
    public int accettaOfferta(Offerta o) {
    	try {
			Connection conn = ConnessioneDB.getConnection();
			String accettaOfferta = "UPDATE OFFERTA SET statoofferta = 'Accettata' WHERE matstudente = ? AND idannuncio = ? RETURNING idofferta";
			PreparedStatement stmtAccettaOfferta = conn.prepareStatement(accettaOfferta);
			stmtAccettaOfferta.setString(1, o.getStudente().getMatricola());
			stmtAccettaOfferta.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));
			
			ResultSet rs = stmtAccettaOfferta.executeQuery();
			
			if (rs.next()) {
				int idOffertaModificata = rs.getInt("idofferta");
				String rifiutaOfferteRestanti = "UPDATE OFFERTA SET statoofferta = 'Rifiutata' WHERE idofferta <> ? AND idannuncio = ?";
				PreparedStatement stmtRifiutaOfferte = conn.prepareStatement(rifiutaOfferteRestanti);
				stmtRifiutaOfferte.setInt(1, idOffertaModificata);
				stmtRifiutaOfferte.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));
				stmtRifiutaOfferte.executeUpdate();
				
				String chiudiAnnuncio = "UPDATE ANNUNCIO SET statoannuncio = false WHERE idannuncio = ?";
				PreparedStatement stmtChiudiAnnuncio = conn.prepareStatement(chiudiAnnuncio);
				int idAnnuncio = controller.getIdByAnnuncio(o.getAnnuncio());
				System.out.println("ID Annuncio da chiudere: " + idAnnuncio);
				stmtChiudiAnnuncio.setInt(1, idAnnuncio);
				stmtChiudiAnnuncio.executeUpdate();
				
				return 0;
			}
			else {
				System.out.println("Nessuna offerta aggiornata.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
    }
    
    public int rifiutaOfferta(Offerta o) {
    	try {
			Connection conn = ConnessioneDB.getConnection();
			String accettaOfferta = "UPDATE OFFERTA SET statoofferta = 'Rifiutata' WHERE matstudente = ? AND idannuncio = ?";
			PreparedStatement stmtAccettaOfferta = conn.prepareStatement(accettaOfferta);
			stmtAccettaOfferta.setString(1, o.getStudente().getMatricola());
			stmtAccettaOfferta.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));
			
			if (stmtAccettaOfferta.executeUpdate() > 0) {
				System.out.println("Offerta rifiutata con successo.");
				return 0;
			}
			else {
				System.out.println("Nessuna offerta aggiornata.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
    }
    
    public int getIdByOfferta(Offerta o) {
        int idOfferta = -1;

        try {
            Connection conn = ConnessioneDB.getConnection();

            String query = "SELECT idOfferta FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, o.getStudente().getMatricola());
            stmt.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idOfferta = rs.getInt("idOfferta");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idOfferta;
    }
    
    public ArrayList<Oggetto> getOggettiOffertiByOfferta(Offerta o) {
        ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();
        
        try {
            Connection conn = ConnessioneDB.getConnection();
            String queryOggettiOfferti = "SELECT * FROM OGGETTIOFFERTI AS OO NATURAL JOIN OGGETTO AS OG NATURAL JOIN OFFERTA AS OF WHERE OO.idOfferta = ? AND OF.idannuncio = ?";
            PreparedStatement stmtOggettiOfferti = conn.prepareStatement(queryOggettiOfferti);
            stmtOggettiOfferti.setInt(1, controller.getIdByOfferta(o));
            stmtOggettiOfferti.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));

            ResultSet rsOggettiOfferti = stmtOggettiOfferti.executeQuery();
            while (rsOggettiOfferti.next()) {
            	Oggetto oggetto = new Oggetto(
        			rsOggettiOfferti.getString("immagineoggetto"),
        			rsOggettiOfferti.getString("categoria"),
        			rsOggettiOfferti.getString("descrizione"),
					controller.getStudenteByMatricola(rsOggettiOfferti.getString("matstudente"))
				);
                oggetti.add(oggetto);
            }

            rsOggettiOfferti.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oggetti;
    }
    
    public ArrayList<Offerta> getOffertebyMatricola(Studente s) {	
        ArrayList<Offerta> offerte = new ArrayList<>();

        try{
        	Connection conn = ConnessioneDB.getConnection();
            String queryOggettiOfferti = "SELECT * FROM Offerta WHERE matstudente = ?";
            PreparedStatement stmtOggettiOfferti = conn.prepareStatement(queryOggettiOfferti);
            stmtOggettiOfferti.setString(1, s.getMatricola());

            ResultSet rsOggettiOfferti = stmtOggettiOfferti.executeQuery();
            
            while (rsOggettiOfferti.next()) {	
                Offerta offerta = new Offerta(
            		rsOggettiOfferti.getString("tipologia"),
            		rsOggettiOfferti.getTimestamp("dataPubblicazione"),
            		controller.getAnnuncioById(rsOggettiOfferti.getInt("idannuncio"))
                );

                offerta.setStatoOfferta(rsOggettiOfferti.getString("statoOfferta"));
                offerta.setPrezzoOfferta(rsOggettiOfferti.getDouble("prezzoofferta"));
                offerta.setMotivazione(rsOggettiOfferti.getString("motivazione"));
                offerta.setStudente(controller.getStudenteByMatricola(rsOggettiOfferti.getString("matstudente")));

                offerte.add(offerta);
            }

            rsOggettiOfferti.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offerte;
    }
}