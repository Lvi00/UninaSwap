package application.entity;

import java.sql.Timestamp;

public class Offerta {
	private String statoOfferta;
    private double prezzoOfferta = 0;
    private String tipologia;
    private String motivazione;
    private Studente studente;
    private Timestamp dataPubblicazione;
    private Annuncio annuncio;
    
    public Offerta(String tipologia, Timestamp dataPubblicazione, Annuncio annuncio) { 
        this.statoOfferta = "Attesa";
        this.tipologia = tipologia;
        this.dataPubblicazione = dataPubblicazione;
        this.annuncio = annuncio;
    }

    public void setPrezzoOfferta(double prezzoOfferta)
    {
        this.prezzoOfferta = prezzoOfferta;
    }

    public String getStatoOfferta() {
        return statoOfferta;
    }
    
    public void setStatoOfferta(String statoOfferta) {
    	this.statoOfferta = statoOfferta;
    }
    
    public double getPrezzoOfferta() {
        return prezzoOfferta;
    }

    public String getTipologia() {
        return tipologia;
    }
    
    public String getMotivazione() {
        return motivazione;
    }
    
    public void setMotivazione(String motivazione) {
    	this.motivazione = motivazione;
    }
   
    public Studente getStudente() {
        return studente;
    }
    
    public void setStudente (Studente studente) {
    	this.studente = studente;
    }
    
    public Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }
    
    public Annuncio getAnnuncio() {
		return annuncio;
	}
}
