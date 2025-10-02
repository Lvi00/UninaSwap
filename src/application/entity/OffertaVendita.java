package application.entity;

import java.sql.Timestamp;

public class OffertaVendita extends Offerta {
    private double prezzoOfferta;
    
	public OffertaVendita(Timestamp dataPubblicazione, Studente studente, Annuncio annuncio, double prezzoOfferta) {
		super(dataPubblicazione, studente, annuncio);
		this.prezzoOfferta = prezzoOfferta;
	}
	
    public double getPrezzoOfferta() {
        return prezzoOfferta;
    }
    
    public void setPrezzoOfferta(double Prezzo)
    {
    	this.prezzoOfferta = Prezzo;
    }
}
