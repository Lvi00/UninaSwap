package application.InterfacePostgresDAO;

import java.util.ArrayList;

public interface InterfaceOggettiOffertiDAO {
	void SaveOggettoOfferto(int idOfferta, int idOggetto);
	void rimuoviOggettoOffertoById(int idOggetto, int idOfferta);
	ArrayList<Integer> rimuoviOggettiOffertiByIdOfferta(int idOfferta);
}
