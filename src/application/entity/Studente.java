package application.entity;

public class Studente {
	private String matricola;
	private String email;
	private String nome;
	private String cognome;
	private String username;
	public String immagineProfilo;
	
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
}