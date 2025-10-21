package application.InterfacePostgresDAO;

import java.util.ArrayList;

import application.entity.Annuncio;
import application.entity.Oggetto;
import application.entity.Sede;
import application.entity.Studente;

public interface InterfaceAnnuncioDAO {
	int SaveAnnuncio(Annuncio annuncio, Oggetto oggetto, Sede sede);
	int rimuoviAnnuncio(Annuncio annuncio);
	ArrayList<Annuncio> getAnnunci(Studente studente);
	ArrayList<Annuncio> getAnnunciStudente(Studente studente);
	ArrayList<Annuncio> getAnnunciByFiltri(Studente studente, String keyword, String categoria, String tipologia);
	void cambiaStatoAnnuncio(Annuncio annuncio);
	Annuncio getAnnuncioById(Studente studente, int idAnnuncio);
}