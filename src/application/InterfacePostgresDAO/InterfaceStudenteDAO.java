package application.InterfacePostgresDAO;

import application.entity.Studente;

public interface InterfaceStudenteDAO {
	int Save(Studente studente, String password);
	Studente LoginStudente(String username, String password);
	int CheckStudenteEsistente(String matricola, String email, String username);
	Studente getStudenteByMatricola(String matricola);
	void cambiaFoto(String matricola, String percorsoImmagine);
}
