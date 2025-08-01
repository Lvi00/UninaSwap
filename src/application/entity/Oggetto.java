package application.entity;

public class Oggetto {
    private String immagineOggetto;
    private String categoria;
    private String descrizione;
    private String nomeOggetto;

    public Oggetto(String immagineOggetto, String categoria, String descrizione, String nomeOggetto) {
        this.immagineOggetto = immagineOggetto;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.nomeOggetto = nomeOggetto;
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

    public String getNomeOggetto() {
        return nomeOggetto;
    }
}