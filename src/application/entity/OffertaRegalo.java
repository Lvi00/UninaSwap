package application.entity;

import java.sql.Timestamp;

public class OffertaRegalo extends Offerta {
    private String motivazione;
    
	public OffertaRegalo(Timestamp dataPubblicazione, Studente studente, Annuncio annuncio, String motivazione) {
		super(dataPubblicazione, studente, annuncio);
		this.motivazione = motivazione;
	}
	
    public String getMotivazione() {
        return motivazione;
    }
}
