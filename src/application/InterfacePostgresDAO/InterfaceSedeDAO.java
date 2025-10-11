package application.InterfacePostgresDAO;

import application.entity.Sede;

public interface InterfaceSedeDAO {
	int SaveSade(Sede sede);
	int getIdBySede(Sede sede);
	int rimuoviSede(int idSede);
}
