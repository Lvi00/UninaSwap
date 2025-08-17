
package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.entity.Sede;
import application.resources.ConnessioneDB;

public class SedeDAO {
	
	public void SaveSade(Sede sede) {
		try {
		    Connection conn = ConnessioneDB.getConnection();
		    String queryCheck = "SELECT * FROM SEDE WHERE ptop = ? AND descrizione = ? AND civico = ? AND cap = ?";
		    PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
		    checkStatement.setString(1, sede.getParticellaToponomastica());
		    checkStatement.setString(2, sede.getDescrizioneIndirizzo());
		    checkStatement.setString(3, sede.getCivico());
		    checkStatement.setString(4, sede.getCap());
		    
		    ResultSet resultSet = checkStatement.executeQuery();
		    
		    if (resultSet.next()) {
		        System.out.println("Sede già esistente.");
		        resultSet.close();
		        checkStatement.close();
		    }
		    
		    else {
			    String query = "INSERT INTO SEDE(ptop,descrizione,civico,cap) VALUES (?,?,?,?)";
			    PreparedStatement statement = conn.prepareStatement(query);
			    statement.setString(1, sede.getParticellaToponomastica());
			    statement.setString(2, sede.getDescrizioneIndirizzo());
			    statement.setString(3, sede.getCivico());
			    statement.setString(4, sede.getCap());
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
	
	 public Sede getSedeById(int idSede) {
	    	Sede sede = null;
	    	
	        try {        	
	            Connection conn = ConnessioneDB.getConnection();
	            String query = "SELECT * FROM SEDE WHERE idsede = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
			    statement.setInt(1, idSede);
	            ResultSet rs = statement.executeQuery();
	            
	            if(rs.next()) {
	            	sede = new Sede(
						rs.getString("ptop"),
						rs.getString("descrizione"),
						rs.getString("civico"),
						rs.getString("cap")
					);
	            }

	            rs.close();
	            statement.close();
	            
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }

	        return sede;
	 }
	 
	 public int getIdBySede(Sede sede) {
		    int idSede = 0;

		    String query = "SELECT idsede FROM SEDE WHERE ptop = ? AND descrizione = ? AND civico = ? AND cap = ?";

		    try {
		    	Connection conn = ConnessioneDB.getConnection();
		        PreparedStatement statement = conn.prepareStatement(query);

		        statement.setString(1, sede.getParticellaToponomastica());
		        statement.setString(2, sede.getDescrizioneIndirizzo());
		        statement.setString(3, sede.getCivico());
		        statement.setString(4, sede.getCap());

		       ResultSet rs = statement.executeQuery();
	            if (rs.next()) {
	                idSede = rs.getInt("idsede");
	            }
		        

		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }

		    return idSede;
		}

}
