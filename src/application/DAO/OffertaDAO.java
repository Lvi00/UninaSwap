package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class OffertaDAO {
	
	private Controller controller = Controller.getController();

	public int SaveOfferta(Offerta offerta) {
	    try {
	        String matStudente = controller.getMatricola(controller.getStudenteOfferta(offerta));
	        int idannuncio = new AnnuncioDAO().getIdByAnnuncio(offerta.getAnnuncio());

	        Connection conn = ConnessioneDB.getConnection();

	        // Controllo duplicati
	        String queryDuplicate = "SELECT 1 FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
	        PreparedStatement dupStatement = conn.prepareStatement(queryDuplicate);
	        dupStatement.setString(1, matStudente);
	        dupStatement.setInt(2, idannuncio);
	        ResultSet dupResult = dupStatement.executeQuery();
	        if (dupResult.next()) {
	            return -1;
	        }

	        // Query di inserimento
	        String insert = "INSERT INTO OFFERTA(statoofferta, prezzoofferta, tipologia, matstudente, idannuncio, motivazione, dataPubblicazione) "
	                      + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	        PreparedStatement statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

	        // Campi comuni
	        statement.setString(1, offerta.getStatoOfferta());
	        statement.setString(4, matStudente);
	        statement.setInt(5, idannuncio);
	        statement.setTimestamp(7, offerta.getDataPubblicazione());

	        // Campi specifici
	        if (offerta instanceof OffertaVendita) {
	            statement.setDouble(2, ((OffertaVendita) offerta).getPrezzoOfferta());
	            statement.setString(3, "Vendita");
	            statement.setString(6, null);
	        } else if (offerta instanceof OffertaRegalo) {
	            statement.setNull(2, java.sql.Types.DOUBLE);
	            statement.setString(3, "Regalo");
	            statement.setString(6, ((OffertaRegalo) offerta).getMotivazione());
	        } else if (offerta instanceof OffertaScambio) {
	            statement.setNull(2, java.sql.Types.DOUBLE);
	            statement.setString(3, "Scambio");
	            statement.setString(6, null);
	        } else {
	            return -1;
	        }

	        int rowsInserted = statement.executeUpdate();

	        if (offerta instanceof OffertaScambio) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }
	            return -1;
	        }

	        return rowsInserted > 0 ? 0 : -1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	    }
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
                String tipologia = rs.getString("tipologia");
                Offerta offerta = null;

                switch (tipologia) {
                    case "Vendita":
                        offerta = new OffertaVendita(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            a,
                            rs.getDouble("prezzoofferta")
                        );
                        break;

                    case "Regalo":
                        offerta = new OffertaRegalo(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            a,
                            rs.getString("motivazione")
                        );
                        break;

                    case "Scambio":
                        offerta = new OffertaScambio(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            a
                        );
                        // carico anche gli oggetti scambiati
                        ((OffertaScambio) offerta).getOggettiOfferti().addAll(
                            getOggettiOffertiByOfferta(offerta)
                        );
                        break;

                    default:
                        System.out.println("Tipologia non riconosciuta: " + tipologia);
                        continue;
                }

                if (offerta != null) {
                    offerta.setStatoOfferta(rs.getString("statoofferta"));
                    offerte.add(offerta);
                }
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

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM Offerta WHERE matstudente = ? ORDER BY dataPubblicazione";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, s.getMatricola());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipologia = rs.getString("tipologia");
                Annuncio annuncio = controller.getAnnuncioById(rs.getInt("idannuncio"));
                Offerta offerta = null;

                switch (tipologia) {
                    case "Vendita":
                        offerta = new OffertaVendita(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            annuncio,
                            rs.getDouble("prezzoofferta")
                        );
                        break;

                    case "Regalo":
                        offerta = new OffertaRegalo(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            annuncio,
                            rs.getString("motivazione")
                        );
                        break;

                    case "Scambio":
                        offerta = new OffertaScambio(
                            rs.getTimestamp("dataPubblicazione"),
                            controller.getStudenteByMatricola(rs.getString("matstudente")),
                            annuncio
                        );
                        // carico oggetti offerti per lo scambio
                        ((OffertaScambio) offerta).getOggettiOfferti().addAll(
                            getOggettiOffertiByOfferta(offerta)
                        );
                        break;

                    default:
                        System.out.println("Tipologia non riconosciuta: " + tipologia);
                        continue;
                }

                if (offerta != null) {
                    offerta.setStatoOfferta(rs.getString("statoofferta"));
                    offerte.add(offerta);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offerte;
    }
    
    public int eliminaOfferta(Offerta o) {
        try{
        	Connection conn = ConnessioneDB.getConnection();
            if (o instanceof OffertaScambio) {
                String eliminaOggettiOfferti = "DELETE FROM OGGETTIOFFERTI WHERE idOfferta = ?";
                try (PreparedStatement stmtEliminaOggetti = conn.prepareStatement(eliminaOggettiOfferti)) {
                    stmtEliminaOggetti.setInt(1, controller.getIdByOfferta(o));
                    stmtEliminaOggetti.executeUpdate();
                }
            }

            // Poi elimino l'offerta
            String eliminaOfferta = "DELETE FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            try (PreparedStatement stmtEliminaOfferta = conn.prepareStatement(eliminaOfferta)) {
                stmtEliminaOfferta.setString(1, o.getStudente().getMatricola());
                stmtEliminaOfferta.setInt(2, controller.getIdByAnnuncio(o.getAnnuncio()));

                int righeEliminate = stmtEliminaOfferta.executeUpdate();
                if (righeEliminate > 0) {
                    System.out.println("Offerta eliminata con successo.");
                    return 0;
                } else {
                    System.out.println("Nessuna offerta eliminata.");
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }
}