package application.InterfacePostgresDAO;

import java.util.ArrayList;

import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import application.entity.Studente;

public interface InterfaceOffertaDAO {
	int SaveOffertaVendita(OffertaVendita offertaVendita);
	int SaveOffertaRegalo(OffertaRegalo offertaRegalo);
	int SaveOffertaScambio(OffertaScambio offertaScambio);
	int UpdateOffertaVendita(double prezzo, Studente studente, Annuncio annuncio);
	int UpdateOffertaRegalo(String motivazione, Studente studente, Annuncio annuncio);
	int UpdateStatoOfferta(Offerta offerta);
	int rimuoviOfferteByIdAnnuncio(int idAnnuncio);
	ArrayList<Offerta> getOffertebyAnnuncio(Annuncio annuncio);
	int accettaOfferta(Offerta offerta);
	int rifiutaOfferta(Offerta offerta);
	int getIdByOfferta(Offerta offerta);
	ArrayList<Oggetto> getOggettiOffertiByOfferta(Offerta offerta);
	ArrayList<Offerta> getOffertebyMatricola(Studente studente);
	int eliminaOfferta(Offerta offerta);
	int getNumeroOfferteInviate(Studente studente, String tipologia);
	int getNumeroOfferteAccettate(Studente studente, String tipologia);
	double getPrezzoMinimoOfferte(Studente studente, String tipoFunzione);
}