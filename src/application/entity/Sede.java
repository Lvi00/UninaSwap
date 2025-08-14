package application.entity;

public class Sede {
	private String particellaToponomastica;
	private String descrizioneIndirizzo;
	private String civico;
	private String cap;
	
	public Sede(String particellaToponomastica, String descrizioneIndirizzo, String civico, String cap) {
		this.particellaToponomastica = particellaToponomastica;
		this.descrizioneIndirizzo = descrizioneIndirizzo;
		this.civico = civico;
		this.cap = cap;
	}
	
	public String getParticellaToponomastica() {
		return particellaToponomastica;
	}
	
	public String getDescrizioneIndirizzo() {
		return descrizioneIndirizzo;
	}
	
	public String getCivico() {
		return civico;
	}
	
	public String getCap() {
		return cap;
	}
}
