package application.entity;

public class Oggetto {
	private int idOggetto;
    private String immagineOggetto;
    private String categoria;
    private String descrizione;
    private Studente studente;
    
    public Oggetto(String immagineOggetto, String categoria, String descrizione, Studente studente) {
        this.immagineOggetto = immagineOggetto;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.studente = studente;
    }
    
    public int getIdOggetto() {
		return idOggetto;
	}
    
    public void setIdOggetto(int idOggetto) {
    	this.idOggetto = idOggetto;
    }
    
    public String getImmagineOggetto() {
        return immagineOggetto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }
    
    public Studente getStudente() {
        return studente;
    }
}