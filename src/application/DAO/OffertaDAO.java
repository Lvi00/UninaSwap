package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.resources.ConnessioneDB;

public class OffertaDAO {

    public int SaveOfferta(Annuncio annuncio, Offerta offerta, String matricola, String motivazione) {
        int returnValue = 0;
    	try {
            String matStudente = matricola;
            int idannuncio = new AnnuncioDAO().getIdByAnnuncio(annuncio);

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
                return 1;
            }
            dupResult.close();
            dupStatement.close();

            // Imposta motivazione
            if (!"Regalo".equalsIgnoreCase(offerta.getTipologia())) {
                motivazione = "Non Necessaria";
            }

            // Inserimento offerta
            String insert = "INSERT INTO OFFERTA(statoofferta, prezzoofferta, tipologia, matstudente, idannuncio, motivazione) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
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
                return 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        
        if ("Scambio".equalsIgnoreCase(offerta.getTipologia())) {
            return returnValue;
        }
        else {
            return 0;
        }
    }
}