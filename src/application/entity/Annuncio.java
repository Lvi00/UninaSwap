package application.entity;

import java.time.LocalTime;

public class Annuncio {
    private String titoloAnnuncio;
    private boolean statoAnnuncio;
    private LocalTime fasciaOrariaInizio;
    private LocalTime fasciaOrariaFine;
    private double prezzo;
    private String tipologia;
    private String descrizioneAnnuncio;
    private Oggetto oggetto;
    private String giorni; 

    public Annuncio(String titoloAnnuncio,
                    boolean statoAnnuncio,
                    LocalTime fasciaOrariaInizio,
                    LocalTime fasciaOrariaFine,
                    double prezzo,
                    String tipologia,
                    String descrizioneAnnuncio,
                    Oggetto oggetto,
                    String giorni) { 
        this.titoloAnnuncio = titoloAnnuncio;
        this.statoAnnuncio = statoAnnuncio;
        this.fasciaOrariaInizio = fasciaOrariaInizio;
        this.fasciaOrariaFine = fasciaOrariaFine;
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.descrizioneAnnuncio = descrizioneAnnuncio;
        this.oggetto = oggetto;
        this.giorni = giorni;
    }

    public String getTitoloAnnuncio() {
        return titoloAnnuncio;
    }

    public boolean isStatoAnnuncio() {
        return statoAnnuncio;
    }

    public LocalTime getFasciaOrariaInizio() {
        return fasciaOrariaInizio;
    }

    public LocalTime getFasciaOrariaFine() {
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

    public String getGiorni() {
        return giorni;
    }
}
