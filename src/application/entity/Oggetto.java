package application.entity;

public class Oggetto {
    private Integer idOggetto;
    private String immagineOggetto;
    private String categoria;
    private String descrizione;
    private String matStudente;
    private String nomeOggetto;

    public Oggetto(Integer idOggetto, String immagineOggetto, String categoria, String descrizione, String matStudente, String nomeOggetto) {
        this.idOggetto = idOggetto;
        this.immagineOggetto = immagineOggetto;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.matStudente = matStudente;
        this.nomeOggetto = nomeOggetto;
    }

    // Getters e Setters
    public Integer getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(Integer idOggetto) {
        this.idOggetto = idOggetto;
    }

    public String getImmagineOggetto() {
        return immagineOggetto;
    }

    public void setImmagineOggetto(String immagineOggetto) {
        this.immagineOggetto = immagineOggetto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMatStudente() {
        return matStudente;
    }

    public void setMatStudente(String matStudente) {
        this.matStudente = matStudente;
    }

    public String getNomeOggetto() {
        return nomeOggetto;
    }

    public void setNomeOggetto(String nomeOggetto) {
        this.nomeOggetto = nomeOggetto;
    }
}