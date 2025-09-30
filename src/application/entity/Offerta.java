package application.entity;

import java.sql.Timestamp;

public class Offerta {
	private String statoOfferta;
    private Studente studente;
    private Timestamp dataPubblicazione;
    private Annuncio annuncio;
    
    public Offerta(Timestamp dataPubblicazione, Studente studente, Annuncio annuncio) { 
        this.statoOfferta = "Attesa";
        this.studente = studente;
        this.dataPubblicazione = dataPubblicazione;
        this.annuncio = annuncio;
    }

    public String getStatoOfferta() {
        return statoOfferta;
    }
    
    public void setStatoOfferta(String statoOfferta) {
    	this.statoOfferta = statoOfferta;
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
