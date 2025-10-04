package application.entity;

import java.util.ArrayList;

public class Studente {
	private String matricola;
	private String email;
	private String nome;
	private String cognome;
	private String username;
	private String immagineProfilo;
	private ArrayList<Annuncio> annunciPubblicati = new ArrayList<Annuncio>();
	private ArrayList<Annuncio> annunciVisibili = new ArrayList<Annuncio>();
	private ArrayList<Offerta> offerteInviate = new ArrayList<Offerta>();

	public Studente(String matricola, String email, String nome, String cognome, String username) {
		this.matricola = matricola;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
	}
	
    public String getMatricola() {
        return matricola;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }
    
    public String getImmagineProfilo() {
        return immagineProfilo;
    }
    
    public void setImmagine(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }
    
    public ArrayList<Annuncio> getAnnunciPubblicati() {
		return annunciPubblicati;
	}
	
	public void setAnnunciPubblicati(ArrayList<Annuncio> annunciPubblicati) {
		this.annunciPubblicati = annunciPubblicati;
	}
	
    public ArrayList<Annuncio> getAnnunciVisibili() {
		return annunciVisibili;
	}
	
	public void setAnnunciVisibili(ArrayList<Annuncio> annunciVisibili) {
		this.annunciVisibili = annunciVisibili;
	}
	
    public ArrayList<Offerta> getOfferteInviate() {
		return offerteInviate;
	}
	
	public void setOfferteInviate(ArrayList<Offerta> offerteInviate) {
		this.offerteInviate = offerteInviate;
	}
}