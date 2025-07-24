package application.entity;

public class Studente {
	private String matricola;
	private String email;
	private String nome;
	private String cognome;
	private String passkey;
	private String username;
	
	public Studente(String matricola, String email, String nome, String cognome, String passkey, String username) {
		this.matricola = matricola;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.passkey = passkey;
		this.username = username;
		System.out.println("Studente Creato");
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

    public String getPasskey() {
        return passkey;
    }

    public String getUsername() {
        return username;
    }
	
}
