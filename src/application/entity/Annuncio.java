package application.entity;

public class Annuncio {	
    private String titoloAnnuncio;
    private boolean statoAnnuncio;
    private String fasciaOrariaInizio;
    private String fasciaOrariaFine;
    private double prezzo;
    private String tipologia;
    private String descrizioneAnnuncio;
    private Oggetto oggetto;
    private Sede sede;
    private String giorni;

    public Annuncio(String titoloAnnuncio,
                    boolean statoAnnuncio,
                    String fasciaOrariaInizio,
                    String fasciaOrariaFine,
                    double prezzo,
                    String tipologia,
                    String descrizioneAnnuncio,
                    Oggetto oggetto,
                    Sede sede,
                    String giorni) { 
        this.titoloAnnuncio = titoloAnnuncio;
        this.statoAnnuncio = statoAnnuncio;
        this.fasciaOrariaInizio = fasciaOrariaInizio;
        this.fasciaOrariaFine = fasciaOrariaFine;
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.descrizioneAnnuncio = descrizioneAnnuncio;
        this.oggetto = oggetto;
        this.sede = sede;
        this.giorni = giorni;
    }

    public String getTitoloAnnuncio() {
        return titoloAnnuncio;
    }

    public boolean isStatoAnnuncio() {
        return statoAnnuncio;
    }

    public String getFasciaOrariaInizio() {
        return fasciaOrariaInizio;
    }

    public String getFasciaOrariaFine() {
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

    public Sede getSede() {
        return sede;
    }
    
    public String getGiorni() {
        return giorni;
    }
}
