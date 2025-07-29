package application.DAO;

import java.sql.SQLException;
import application.entity.Studente;

public interface PostreSQLDAO<T, K> {
	public int Save(T value) throws SQLException;
	public int Delete(T value) throws SQLException;
	public int Find(K value) throws SQLException;
}