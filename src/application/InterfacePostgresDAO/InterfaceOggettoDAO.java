package application.InterfacePostgresDAO;

import application.entity.Oggetto;

public interface InterfaceOggettoDAO {
	int SaveOggetto(Oggetto oggetto);
	Oggetto getOggettoById(int idOggetto);
	int rimuoviOggettoByIdOggetto(int idOggetto);
	int UpdateOggetto(Oggetto oggetto, String pathImmagine, String categoria, String descrizione);
	int UpdateImmagineOggetto(Oggetto oggetto, String path);
}
