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

	private Controller controller = new Controller();

	public int SaveOfferta(Annuncio annuncio, Offerta offerta, String matricola) {
	    try {
	        String matStudente = matricola;
	        int idannuncio = new AnnuncioDAO().getIdByAnnuncio(annuncio);

	        Connection conn = ConnessioneDB.getConnection();
	        String queryCheck = "SELECT * FROM OFFERTA WHERE statoofferta = ? AND prezzoofferta = ? "
	                + "AND tipologia = ? AND matstudente = ? AND idannuncio = ?";
	        PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
	        checkStatement.setString(1, offerta.getStatoOfferta());
	        checkStatement.setDouble(2, offerta.getPrezzoOfferta());
	        checkStatement.setString(3, offerta.getTipologia());
	        checkStatement.setString(4, matStudente);
	        checkStatement.setInt(5, idannuncio);

	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Offerta già esistente.");
	            resultSet.close();
	            checkStatement.close();
	            return 1;
	        } else {
	            String insert = "INSERT INTO OFFERTA(statoofferta, prezzoofferta, tipologia, matstudente, idannuncio) "
	                    + "VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(insert);
	            statement.setString(1, offerta.getStatoOfferta());
	            statement.setDouble(2, offerta.getPrezzoOfferta());
	            statement.setString(3, offerta.getTipologia());
	            statement.setString(4, matStudente);
	            statement.setInt(5, idannuncio);

	            int rowsInserted = statement.executeUpdate();
	            statement.close();

	            if (rowsInserted == 0) {
	                System.out.println("Errore: inserimento fallito.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 1;
	    }
	    
        return 0;
	}

}
