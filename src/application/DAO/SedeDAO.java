
package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.control.Controller;
import application.entity.Sede;
import application.resources.ConnessioneDB;

public class SedeDAO {
	
	private Controller controller = Controller.getController();
	
	public int SaveSade(Sede sede) {
		int idSedeInserita = -1;
		
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String queryCheck = "SELECT * FROM SEDE WHERE ptop = ? AND descrizione = ? AND civico = ? AND cap = ?";
		    PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
		    checkStatement.setString(1, controller.getParticellaToponomasticaSede(sede));
		    checkStatement.setString(2, controller.getDescrizioneIndirizzo(sede));
		    checkStatement.setString(3, controller.getCivicoSede(sede));
		    checkStatement.setString(4, controller.getCapSede(sede));
		    
		    ResultSet resultSet = checkStatement.executeQuery();
		    
		    if (resultSet.next()) {
		    	System.out.println("Sede già esistente.");
		        idSedeInserita = resultSet.getInt("idSede");
		        resultSet.close();
		        checkStatement.close();
		    }
		    
		    else {
			    String query = "INSERT INTO SEDE(ptop,descrizione,civico,cap) VALUES (?,?,?,?) RETURNING idSede";
			    PreparedStatement insertStatement = conn.prepareStatement(query);
	            insertStatement.setString(1, controller.getParticellaToponomasticaSede(sede));
	            insertStatement.setString(2, controller.getDescrizioneIndirizzo(sede));
	            insertStatement.setString(3, controller.getCivicoSede(sede));
	            insertStatement.setString(4, controller.getCapSede(sede));

	            ResultSet rsInsert = insertStatement.executeQuery();
	            
	            if (rsInsert.next()) {
	                idSedeInserita = rsInsert.getInt("idSede");
	                System.out.println("Nuova sede inserita con ID: " + idSedeInserita);
	            }
	            else {
	                System.out.println("Errore: nessun ID restituito.");
	            }
		    }
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return idSedeInserita;
	}
	
	public int getIdBySede(Sede sede) {
		int idSede = -1;
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String query = "SELECT idSede FROM SEDE WHERE ptop = ? AND descrizione = ? AND civico = ? AND cap = ?";
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.setString(1, controller.getParticellaToponomasticaSede(sede));
		    statement.setString(2, controller.getDescrizioneIndirizzo(sede));
		    statement.setString(3, controller.getCivicoSede(sede));
		    statement.setString(4, controller.getCapSede(sede));
		    
		    ResultSet resultSet = statement.executeQuery();
		    
		    if (resultSet.next()) {
		    	idSede = resultSet.getInt("idSede");
		    	sede.setIdSede(idSede);
		    } else {
		        System.out.println("Nessuna sede trovata con i criteri specificati.");
		    }
		    
		    resultSet.close();
		    statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idSede;
	}
	
	public int rimuoviSede(int idSede) {		
		try {
			Connection conn = ConnessioneDB.getConnection();
	        String queryCount = "SELECT COUNT(*) AS totale FROM SEDE AS S INNER JOIN ANNUNCIO AS A ON S.idSede = A.idSede WHERE S.idsede = ?";
	        PreparedStatement countStatement = conn.prepareStatement(queryCount);
	        countStatement.setInt(1, idSede);
	        ResultSet rs = countStatement.executeQuery();

	        int totaleAnnunci = 0;
	        
	        if (rs.next()) {
	            totaleAnnunci = rs.getInt("totale");
	        }
	        
	        if(totaleAnnunci > 0) {
	        	return 1;
	        }
	        
	        String queryDelete = "DELETE FROM SEDE WHERE idSede = ?";
	        PreparedStatement deleteStatement = conn.prepareStatement(queryDelete);
	        deleteStatement.setInt(1, idSede);

	        int righeEliminate = deleteStatement.executeUpdate();
	        
	        deleteStatement.close();

	        if (righeEliminate > 0) {
	            System.out.println("Sede con ID " + idSede + " eliminata con successo.");
	            return 0;
	        } else {
	            System.out.println("Nessuna sede trovata con ID " + idSede + ".");
	            return 1;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}
}
