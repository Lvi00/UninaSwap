package application.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OffertaScambio extends Offerta {
	private ArrayList<Oggetto> oggettiOfferti;
	
	public OffertaScambio(Timestamp dataPubblicazione, Studente studente, Annuncio annuncio) {
		super(dataPubblicazione, studente, annuncio);
		oggettiOfferti = new ArrayList<Oggetto>();
	}
	
	public ArrayList<Oggetto> getOggettiOfferti() {
		return oggettiOfferti;
	}
	
	public void setOggettiOfferti(ArrayList<Oggetto> oggettiOfferti) {
		this.oggettiOfferti = oggettiOfferti;
	}
}
