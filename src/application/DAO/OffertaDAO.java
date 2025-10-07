package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	        int idannuncio = new AnnuncioDAO().getIdByAnnuncio(controller.getAnnuncioOfferta(offerta));

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
	        statement.setString(1, controller.getStatoOfferta(offerta));
	        statement.setString(4, matStudente);
	        statement.setInt(5, idannuncio);
	        statement.setTimestamp(7, controller.getDataPubblicazioneOfferta(offerta));

	        if (offerta instanceof OffertaVendita) {
	            OffertaVendita offertaVendita = (OffertaVendita) offerta;
	            statement.setDouble(2, controller.getPrezzoOfferta(offertaVendita));
	            statement.setString(3, "Vendita");
	            statement.setString(6, "Assente");
	        } else if (offerta instanceof OffertaRegalo) {
	            OffertaRegalo offertaRegalo = (OffertaRegalo) offerta;
	            statement.setNull(2, java.sql.Types.DOUBLE);
	            statement.setString(3, "Regalo");
	            statement.setString(6, controller.getMotivazioneOfferta(offertaRegalo));
	        } else if (offerta instanceof OffertaScambio) {
	            statement.setNull(2, java.sql.Types.DOUBLE);
	            statement.setString(3, "Scambio");
	            statement.setString(6, "Assente");
	        } else {
	            return -1;
	        }

	        int rowsInserted = statement.executeUpdate();

	        if (offerta instanceof OffertaScambio) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int idOfferta = generatedKeys.getInt(1);

	                // Inserimento oggetti collegati
	                OffertaScambio offertaScambio = (OffertaScambio) offerta;
	                for (Oggetto ogg : offertaScambio.getOggettiOfferti()) {
	                    String insertOggetto = "INSERT INTO OGGETTIOFFERTI (idOfferta, immagineoggetto, categoria, descrizione, matstudente) VALUES (?, ?, ?, ?, ?)";
	                    PreparedStatement stmtOggetto = conn.prepareStatement(insertOggetto);
	                    stmtOggetto.setInt(1, idOfferta);
	                    stmtOggetto.setString(2, ogg.getImmagineOggetto());
	                    stmtOggetto.setString(3, ogg.getCategoria());
	                    stmtOggetto.setString(4, ogg.getDescrizione());
	                    stmtOggetto.setString(5, ogg.getStudente().getMatricola());
	                    stmtOggetto.executeUpdate();
	                }

	                return idOfferta; // ritorno l'id generato
	            }
	            return -1;
	        }

	        return rowsInserted > 0 ? 0 : -1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	    }
	}
	
	public int UpdateOffertaVendita(double prezzo, Studente studente, Annuncio annuncio) {
	    try {
	        Connection conn = ConnessioneDB.getConnection();
			String modificaOfferta = "UPDATE offerta SET prezzoofferta = ?, statoofferta = ? WHERE matstudente = ? AND idannuncio = ?";
			PreparedStatement stmtModificaAccettaOfferta = conn.prepareStatement(modificaOfferta);
	        stmtModificaAccettaOfferta.setDouble(1, prezzo);
	        stmtModificaAccettaOfferta.setString(2, "Attesa");
			stmtModificaAccettaOfferta.setString(3, controller.getMatricola(studente));
			stmtModificaAccettaOfferta.setInt(4, controller.getIdByAnnuncio(annuncio));
			

	        int righeAggiornate = stmtModificaAccettaOfferta.executeUpdate();

	        if (righeAggiornate == 0) {
	            return 2;
	        }
	        
			stmtModificaAccettaOfferta.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 2;
	    } 
	    
	    return 0;
	}
    
	public int UpdateOffertaRegalo(String motivazione, Studente studente, Annuncio annuncio) {

	    try {
	        Connection conn = ConnessioneDB.getConnection();
			String modificaOfferta = "UPDATE offerta SET motivazione = ?, statoofferta = ? WHERE matstudente = ? AND idannuncio = ?";
			PreparedStatement stmtModificaAccettaOfferta = conn.prepareStatement(modificaOfferta);
	        stmtModificaAccettaOfferta.setString(1, motivazione);
	        stmtModificaAccettaOfferta.setString(2, "Attesa");
			stmtModificaAccettaOfferta.setString(3, controller.getMatricola(studente));
			stmtModificaAccettaOfferta.setInt(4, controller.getIdByAnnuncio(annuncio));
			

	        int righeAggiornate = stmtModificaAccettaOfferta.executeUpdate();

	        if (righeAggiornate == 0) {
	            return 2;
	        }
	        
			stmtModificaAccettaOfferta.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 2;
	    } 
	    
	    return 0;
	}
	
	public int UpdateStatoOfferta(Offerta offerta) {
	    try {
	        Connection conn = ConnessioneDB.getConnection();
			String modificaOfferta = "UPDATE offerta SET statoofferta = ? WHERE matstudente = ? AND idannuncio = ?";
			PreparedStatement stmtModificaAccettaOfferta = conn.prepareStatement(modificaOfferta);
	        stmtModificaAccettaOfferta.setString(1, "Attesa");
			stmtModificaAccettaOfferta.setString(2, controller.getMatricola(controller.getStudenteOfferta(offerta)));
			stmtModificaAccettaOfferta.setInt(3, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));
			
	        int righeAggiornate = stmtModificaAccettaOfferta.executeUpdate();

	        if (righeAggiornate == 0) {
	            return 1;
	        }
	        
			stmtModificaAccettaOfferta.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 1;
	    } 
	    
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
    
    public ArrayList<Offerta> getOffertebyAnnuncio(Annuncio annuncio) {
        ArrayList<Offerta> offerte = new ArrayList<Offerta>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OFFERTA WHERE idannuncio = ? ORDER BY statoofferta";
            PreparedStatement selectStmt = conn.prepareStatement(query);
            selectStmt.setInt(1, controller.getIdByAnnuncio(annuncio));
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String tipologia = rs.getString("tipologia");
                System.out.println("Tipologia offerta: " + tipologia);
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
                        controller.setOggettiOfferti((OffertaScambio) offerta, controller.getOggettiOffertiByOfferta(offerta));
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
    
    public int accettaOfferta(Offerta offerta) {
    	try {
			Connection conn = ConnessioneDB.getConnection();
			String accettaOfferta = "UPDATE OFFERTA SET statoofferta = 'Accettata' WHERE matstudente = ? AND idannuncio = ? RETURNING idofferta";
			PreparedStatement stmtAccettaOfferta = conn.prepareStatement(accettaOfferta);
			stmtAccettaOfferta.setString(1, controller.getMatricola(offerta.getStudente()));
			stmtAccettaOfferta.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));
			
			ResultSet rs = stmtAccettaOfferta.executeQuery();
			
			if (rs.next()) {
				int idOffertaModificata = rs.getInt("idofferta");
				String rifiutaOfferteRestanti = "UPDATE OFFERTA SET statoofferta = 'Rifiutata' WHERE idofferta <> ? AND idannuncio = ?";
				PreparedStatement stmtRifiutaOfferte = conn.prepareStatement(rifiutaOfferteRestanti);
				stmtRifiutaOfferte.setInt(1, idOffertaModificata);
				stmtRifiutaOfferte.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));
				stmtRifiutaOfferte.executeUpdate();
				
				String chiudiAnnuncio = "UPDATE ANNUNCIO SET statoannuncio = false WHERE idannuncio = ?";
				PreparedStatement stmtChiudiAnnuncio = conn.prepareStatement(chiudiAnnuncio);
				int idAnnuncio = controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta));
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
    
    public int rifiutaOfferta(Offerta offerta) {
    	try {
			Connection conn = ConnessioneDB.getConnection();
			String accettaOfferta = "UPDATE OFFERTA SET statoofferta = 'Rifiutata' WHERE matstudente = ? AND idannuncio = ?";
			PreparedStatement stmtAccettaOfferta = conn.prepareStatement(accettaOfferta);
			stmtAccettaOfferta.setString(1, controller.getMatricola(controller.getStudenteOfferta(offerta)));
			stmtAccettaOfferta.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));
			
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
    
    public int getIdByOfferta(Offerta offerta) {
        int idOfferta = -1;

        try {
            Connection conn = ConnessioneDB.getConnection();

            String query = "SELECT idOfferta FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, controller.getMatricola(controller.getStudenteOfferta(offerta)));
            stmt.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));
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
    
    public ArrayList<Oggetto> getOggettiOffertiByOfferta(Offerta offerta) {
        ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();
        
        try {
            Connection conn = ConnessioneDB.getConnection();
            String queryOggettiOfferti = "SELECT * FROM OGGETTIOFFERTI AS OO NATURAL JOIN OGGETTO AS OG NATURAL JOIN OFFERTA AS OF WHERE OO.idOfferta = ? AND OF.idannuncio = ?";
            PreparedStatement stmtOggettiOfferti = conn.prepareStatement(queryOggettiOfferti);
            stmtOggettiOfferti.setInt(1, controller.getIdByOfferta(offerta));
            stmtOggettiOfferti.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));

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
    
    public ArrayList<Offerta> getOffertebyMatricola(Studente studente) {	
        ArrayList<Offerta> offerte = new ArrayList<>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM Offerta WHERE matstudente = ? ORDER BY dataPubblicazione";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, controller.getMatricola(studente));

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
                        controller.setOggettiOfferti((OffertaScambio) offerta, controller.getOggettiOffertiByOfferta(offerta));
                    break;

                    default:
                        System.out.println("Tipologia non riconosciuta: " + tipologia);
                    break;
                }

                if (offerta != null) {
                    controller.setStatoOfferta(offerta, rs.getString("statoofferta"));
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
    
    public int eliminaOfferta(Offerta offerta) {
        try{
        	Connection conn = ConnessioneDB.getConnection();
            if (offerta instanceof OffertaScambio) {
                String eliminaOggettiOfferti = "DELETE FROM OGGETTIOFFERTI WHERE idOfferta = ?";
                try (PreparedStatement stmtEliminaOggetti = conn.prepareStatement(eliminaOggettiOfferti)) {
                    stmtEliminaOggetti.setInt(1, controller.getIdByOfferta(offerta));
                    stmtEliminaOggetti.executeUpdate();
                }
            }

            // Poi elimino l'offerta
            String eliminaOfferta = "DELETE FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            try (PreparedStatement stmtEliminaOfferta = conn.prepareStatement(eliminaOfferta)) {
                stmtEliminaOfferta.setString(1, controller.getMatricola(controller.getStudenteOfferta(offerta)));
                stmtEliminaOfferta.setInt(2, controller.getIdByAnnuncio(controller.getAnnuncioOfferta(offerta)));

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
    
    public int getNumeroOfferteInviate(Studente studente, String tipologia) {
		int count = 0;
				
		try {
			Connection conn = ConnessioneDB.getConnection();
			String query = "SELECT COUNT(*) AS numeroOfferteInviate FROM OFFERTA WHERE matstudente = ?";
			
			switch(tipologia) {
				case "Vendita":
					query += " AND tipologia = 'Vendita'";
				break;
				
				case "Scambio":
					query += " AND tipologia = 'Scambio'";
				break;
					
				case "Regalo":
					query += " AND tipologia = 'Regalo'";
				break;
					
				default:
					System.out.println("Nessuna tipologia specificata");
				break;
			}
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, controller.getMatricola(studente));
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("numeroOfferteInviate");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
    
    public int getNumeroOfferteAccettate(Studente studente, String tipologia) {
		int count = 0;
		try {
			Connection conn = ConnessioneDB.getConnection();
			String query = "SELECT COUNT(*) AS numeroOfferteAccettate FROM OFFERTA WHERE matstudente = ? AND statoofferta = 'Accettata'";
			
			switch(tipologia) {
				case "Vendita":
					query += " AND tipologia = 'Vendita'";
				break;
				
				case "Scambio":
					query += " AND tipologia = 'Scambio'";
				break;
					
				case "Regalo":
					query += " AND tipologia = 'Regalo'";
				break;
					
				default:
					System.out.println("Nessuna tipologia specificata");
				break;
			}
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, controller.getMatricola(studente));
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("numeroOfferteAccettate");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
    
    public double getPrezzoMinimoOfferte(Studente studente, String tipoFunzione) {
		double result = 0.0;
		try {
			Connection conn = ConnessioneDB.getConnection();
			String query = "SELECT MIN(prezzoofferta), MAX(prezzoofferta), AVG(prezzoofferta) FROM ANNUNCIO AS A "
					+ "INNER JOIN OFFERTA AS O ON A.idannuncio = O.idannuncio "
					+ "WHERE A.tipologia = ? AND O.statoofferta = ? AND A.matstudente = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "Vendita");
			stmt.setString(2, "Accettata");
			stmt.setString(3, controller.getMatricola(studente));
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				switch(tipoFunzione) {
					case "Minimo":
						result = rs.getDouble(1);
					break;
					
					case "Massimo":
						result = rs.getDouble(2);
					break;
						
					case "Media":
						result = rs.getDouble(3);
					break;
				}
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}