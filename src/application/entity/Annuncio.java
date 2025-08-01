package application.entity;

import java.time.LocalDateTime;

public class Annuncio {
    private String titoloAnnuncio;
    private boolean statoAnnuncio;
    private LocalDateTime fasciaOrariaInizio;
    private LocalDateTime fasciaOrariaFine;
    private double prezzo;
    private String tipologia;
    private String descrizioneAnnuncio;
    private Oggetto oggetto;

    public Annuncio(String titoloAnnuncio, boolean statoAnnuncio,
    LocalDateTime fasciaOrariaInizio, LocalDateTime fasciaOrariaFine,
    double prezzo, String tipologia, String descrizioneAnnuncio, Oggetto oggetto) {
        this.titoloAnnuncio = titoloAnnuncio;
        this.statoAnnuncio = statoAnnuncio;
        this.fasciaOrariaInizio = fasciaOrariaInizio;
        this.fasciaOrariaFine = fasciaOrariaFine;
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.descrizioneAnnuncio = descrizioneAnnuncio;
        this.oggetto = oggetto;
    }

    public String getTitoloAnnuncio() {
        return titoloAnnuncio;
    }

    public boolean isStatoAnnuncio() {
        return statoAnnuncio;
    }

    public LocalDateTime getFasciaOrariaInizio() {
        return fasciaOrariaInizio;
    }

    public LocalDateTime getFasciaOrariaFine() {
        return fasciaOrariaFine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getDescrizioneAnnuncio() {
        return descrizioneAnnuncio;
    }
    
    public Oggetto getOggetto() {
		return oggetto;
	}
}
