package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import application.entity.Sede;
import application.entity.Studente;
import application.resources.ConnessioneDB;

public class OffertaDAO {
	public int SaveOfferta(Offerta offerta) {
	    try {
	        String matStudente = offerta.getStudente().getMatricola();
	        int idannuncio = offerta.getAnnuncio().getIdAnnuncio();

	        Connection conn = ConnessioneDB.getConnection();

	        String queryDuplicate = "SELECT 1 FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
	        PreparedStatement dupStatement = conn.prepareStatement(queryDuplicate);
	        dupStatement.setString(1, matStudente);
	        dupStatement.setInt(2, idannuncio);
	        ResultSet dupResult = dupStatement.executeQuery();
	        if (dupResult.next()) {
	            return -1;
	        }

	        String insert = "INSERT INTO OFFERTA(statoofferta, prezzoofferta, tipologia, matstudente, idannuncio, motivazione, dataPubblicazione) "
	                      + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	        PreparedStatement statement = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);

	        statement.setString(1, offerta.getStatoOfferta());
	        statement.setString(4, matStudente);
	        statement.setInt(5, idannuncio);
	        statement.setTimestamp(7, offerta.getDataPubblicazione());

	        if (offerta instanceof OffertaVendita) {
	            OffertaVendita offertaVendita = (OffertaVendita) offerta;
	            statement.setDouble(2, offertaVendita.getPrezzoOfferta());
	            statement.setString(3, "Vendita");
	            statement.setString(6, "Assente");
	        } else if (offerta instanceof OffertaRegalo) {
	            OffertaRegalo offertaRegalo = (OffertaRegalo) offerta;
	            statement.setNull(2, java.sql.Types.DOUBLE);
	            statement.setString(3, "Regalo");
	            statement.setString(6, offertaRegalo.getMotivazione());
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

	                return idOfferta;
	            }
	            return -1;
	        }

	        if(rowsInserted > 0) return 0;
	        
	        return -1;

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
			stmtModificaAccettaOfferta.setString(3, studente.getMatricola());
			stmtModificaAccettaOfferta.setInt(4, annuncio.getIdAnnuncio());
			

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
			stmtModificaAccettaOfferta.setString(3, studente.getMatricola());
			stmtModificaAccettaOfferta.setInt(4, annuncio.getIdAnnuncio());
			

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
			stmtModificaAccettaOfferta.setString(2, offerta.getStudente().getMatricola());
			stmtModificaAccettaOfferta.setInt(3, offerta.getAnnuncio().getIdAnnuncio());
			
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
            if(rs.next()) {
                String deleteOfferte = "DELETE FROM OFFERTA WHERE idAnnuncio = ?";
                PreparedStatement deleteOfferteStmt = conn.prepareStatement(deleteOfferte);
                deleteOfferteStmt.setInt(1, idAnnuncio);
                deleteOfferteStmt.executeUpdate();
                deleteOfferteStmt.close();
            }
            rs.close();
            selectStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public ArrayList<Offerta> getOffertebyAnnuncio(Annuncio annuncio) {
        ArrayList<Offerta> offerte = new ArrayList<Offerta>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OFFERTA AS O INNER JOIN STUDENTE AS S ON O.matstudente = S.matricola WHERE idannuncio = ? ORDER BY statoofferta";
            PreparedStatement selectStmt = conn.prepareStatement(query);
            selectStmt.setInt(1, annuncio.getIdAnnuncio());
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String tipologia = rs.getString("tipologia");
                Offerta offerta = null;
                
                Studente studente = new Studente(
					rs.getString("matricola"),
					rs.getString("email"),
					rs.getString("nome"),
					rs.getString("cognome"),
					rs.getString("username")
        		);

                switch (tipologia) {
                    case "Vendita":
                        offerta = new OffertaVendita(
                            rs.getTimestamp("dataPubblicazione"),
                            studente,
                            annuncio,
                            rs.getDouble("prezzoofferta")
                        );
                        
                        offerta.setIdOfferta(rs.getInt("idofferta"));
                    break;

                    case "Regalo":
                        offerta = new OffertaRegalo(
                            rs.getTimestamp("dataPubblicazione"),
                            studente,
                            annuncio,
                            rs.getString("motivazione")
                        );
                        
                        offerta.setIdOfferta(rs.getInt("idofferta"));
                    break;

                    case "Scambio":
                        offerta = new OffertaScambio(
                            rs.getTimestamp("dataPubblicazione"),
                            studente,
                            annuncio
                        );
                        
                        offerta.setIdOfferta(rs.getInt("idofferta"));
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
			stmtAccettaOfferta.setString(1, offerta.getStudente().getMatricola());
			stmtAccettaOfferta.setInt(2, offerta.getAnnuncio().getIdAnnuncio());
			
			ResultSet rs = stmtAccettaOfferta.executeQuery();
			
			if (rs.next()) {
				int idOffertaModificata = rs.getInt("idofferta");
				String rifiutaOfferteRestanti = "UPDATE OFFERTA SET statoofferta = 'Rifiutata' WHERE idofferta <> ? AND idannuncio = ?";
				PreparedStatement stmtRifiutaOfferte = conn.prepareStatement(rifiutaOfferteRestanti);
				stmtRifiutaOfferte.setInt(1, idOffertaModificata);
				stmtRifiutaOfferte.setInt(2, offerta.getAnnuncio().getIdAnnuncio());
				stmtRifiutaOfferte.executeUpdate();
				
				String chiudiAnnuncio = "UPDATE ANNUNCIO SET statoannuncio = false WHERE idannuncio = ?";
				PreparedStatement stmtChiudiAnnuncio = conn.prepareStatement(chiudiAnnuncio);
				int idAnnuncio = offerta.getAnnuncio().getIdAnnuncio();
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
			stmtAccettaOfferta.setString(1, offerta.getStudente().getMatricola());
			stmtAccettaOfferta.setInt(2, offerta.getAnnuncio().getIdAnnuncio());
			
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
            stmt.setString(1, offerta.getStudente().getMatricola());
            stmt.setInt(2, offerta.getAnnuncio().getIdAnnuncio());
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
            String queryOggettiOfferti = "SELECT * FROM OGGETTIOFFERTI AS OO "
    		+ "NATURAL JOIN OGGETTO AS OG NATURAL JOIN OFFERTA AS OF INNER JOIN STUDENTE AS S ON O.matstudente = S.matricola "
    		+ "WHERE OO.idOfferta = ? AND OF.idannuncio = ?";
            
            PreparedStatement stmtOggettiOfferti = conn.prepareStatement(queryOggettiOfferti);
            stmtOggettiOfferti.setInt(1, offerta.getIdOfferta());
            stmtOggettiOfferti.setInt(2, offerta.getAnnuncio().getIdAnnuncio());

            ResultSet rsOggettiOfferti = stmtOggettiOfferti.executeQuery();
            while (rsOggettiOfferti.next()) {
                Studente studente = new Studente(
            		rsOggettiOfferti.getString("matricola"),
            		rsOggettiOfferti.getString("email"),
            		rsOggettiOfferti.getString("nome"),
            		rsOggettiOfferti.getString("cognome"),
            		rsOggettiOfferti.getString("username")
        		);
                
            	Oggetto oggetto = new Oggetto(
        			rsOggettiOfferti.getString("immagineoggetto"),
        			rsOggettiOfferti.getString("categoria"),
        			rsOggettiOfferti.getString("descrizione"),
					studente
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
        ArrayList<Offerta> offerte = new ArrayList<Offerta>();

        try {
            Connection conn = ConnessioneDB.getConnection();
            String query = "SELECT * FROM OFFERTA AS O INNER JOIN ANNUNCIO AS A ON O.idannuncio = A.idannuncio "
    		+ "INNER JOIN OGGETTO AS OG ON A.idoggetto = OG.idoggetto WHERE O.matstudente = ? ORDER BY O.dataPubblicazione";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studente.getMatricola());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipologia = rs.getString("tipologia");
                
                Sede sede = new Sede(
					rs.getString("ptop"),
					rs.getString("descrizione"),
					rs.getString("civico"),
					rs.getString("cap")
				);
                
            	sede.setIdSede(rs.getInt("idSede"));
            	
            	Oggetto oggetto = new Oggetto(
        			rs.getString("immagineoggetto"),
        			rs.getString("categoria"),
        			rs.getString("descrizione"),
					studente
				);
            	
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
                	
                Offerta offerta = null;
                
                Studente s = new Studente(
            		rs.getString("matricola"),
            		rs.getString("email"),
            		rs.getString("nome"),
            		rs.getString("cognome"),
            		rs.getString("username")
        		);

                switch (tipologia) {
                    case "Vendita":
                        offerta = new OffertaVendita(
                            rs.getTimestamp("dataPubblicazione"),
                            s,
                            annuncio,
                            rs.getDouble("prezzoofferta")
                        );
                    break;

                    case "Regalo":
                        offerta = new OffertaRegalo(
                            rs.getTimestamp("dataPubblicazione"),
                            s,
                            annuncio,
                            rs.getString("motivazione")
                        );
                    break;

                    case "Scambio":
                        offerta = new OffertaScambio(
                            rs.getTimestamp("dataPubblicazione"),
                            s,
                            annuncio
                        );
                    break;

                    default:
                        System.out.println("Tipologia non riconosciuta: " + tipologia);
                    break;
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
    
    public int eliminaOfferta(Offerta offerta) {
        try{
        	Connection conn = ConnessioneDB.getConnection();
            if (offerta instanceof OffertaScambio) {
                String eliminaOggettiOfferti = "DELETE FROM OGGETTIOFFERTI WHERE idOfferta = ?";
                try (PreparedStatement stmtEliminaOggetti = conn.prepareStatement(eliminaOggettiOfferti)) {
                    stmtEliminaOggetti.setInt(1, offerta.getIdOfferta());
                    stmtEliminaOggetti.executeUpdate();
                }
            }

            String eliminaOfferta = "DELETE FROM OFFERTA WHERE matstudente = ? AND idannuncio = ?";
            try (PreparedStatement stmtEliminaOfferta = conn.prepareStatement(eliminaOfferta)) {
                stmtEliminaOfferta.setString(1, offerta.getStudente().getMatricola());
                stmtEliminaOfferta.setInt(2, offerta.getAnnuncio().getIdAnnuncio());

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
			stmt.setString(1, studente.getMatricola());
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
			stmt.setString(1, studente.getMatricola());
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
			stmt.setString(3, studente.getMatricola());
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