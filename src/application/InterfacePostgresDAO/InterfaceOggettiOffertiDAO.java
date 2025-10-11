package application.InterfacePostgresDAO;

import java.util.ArrayList;

public interface InterfaceOggettiOffertiDAO {
	void SaveOggettoOfferto(int idOfferta, int idOggetto);
	ArrayList<Integer> rimuoviOggettiOffertiByIdOfferta(int idOfferta);
	void rimuoviOggettoOffertoById(int idOggetto, int idOfferta);
}
