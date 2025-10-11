package application.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DAO.AnnuncioDAO;
import application.DAO.OffertaDAO;
import application.DAO.OggettiOffertiDAO;
import application.DAO.OggettoDAO;
import application.DAO.SedeDAO;
import application.DAO.StudenteDAO;
import application.entity.Annuncio;
import application.entity.Offerta;
import application.entity.OffertaRegalo;
import application.entity.OffertaScambio;
import application.entity.OffertaVendita;
import application.entity.Oggetto;
import application.entity.Sede;
import application.entity.Studente;

public class Controller {
	private static Controller controller = null;
	private Studente studente = null;
	private File fileOggettoAnnuncio = null;
	private File fileOggettoOfferto = null;
    private File fileOggettoAlternativo = null;
    private File immagineProfiloSelezionata = null;
	private Annuncio annuncioSelezionato = null;
    private ArrayList<String> listaOggettiDesiderati = new ArrayList<String>();
	
	public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }
	
	public void logout() {
		this.studente = null;
		this.fileOggettoAnnuncio = null;
		this.fileOggettoOfferto = null;
	    this.fileOggettoAlternativo = null;
	    this.immagineProfiloSelezionata = null;
		this.annuncioSelezionato = null;
	    this.listaOggettiDesiderati.clear();
	}
	
	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	
	public File getFileOggettoAlternativo() {
		return fileOggettoAlternativo;
	}

	public void setFileOggettoAlternativo(File fileOggettoAlternativo) {
		this.fileOggettoAlternativo = fileOggettoAlternativo;
	}
	
	public File getFileOggettoOfferto() {
		return fileOggettoOfferto;
	}
	
	public File getFileOggettoAnnuncio() {
		return fileOggettoAnnuncio;
	}
	
	public File getImmagineProfiloSelezionata() {
		return immagineProfiloSelezionata;
	}
	
	public void setImmagineProfiloSelezionata(File immagineProfiloSelezionata) {
		this.immagineProfiloSelezionata = immagineProfiloSelezionata;
	}
	
	public void setFileOggettoAnnuncio(File fileOggettoAnnuncio) {
		this.fileOggettoAnnuncio = fileOggettoAnnuncio;
	}
	
	public void setFileOggettoOfferto(File fileOggettoOfferto) {
		this.fileOggettoOfferto = fileOggettoOfferto;
	}
	
	public ArrayList<String> getListaOggettiDesiderati() {
		return listaOggettiDesiderati;
	}
	
	public void setListaOggettiDesiderati(ArrayList<String> listaOggettiDesiderati) {
		this.listaOggettiDesiderati = listaOggettiDesiderati;
	}
	
	public void svuotaListaOggettiDesiderati() {
		this.listaOggettiDesiderati.clear();
	}
	
	public void aggiungiOggettoDesiderato(String oggetto) {
		this.listaOggettiDesiderati.add(oggetto);
	}
	
	public void setAnnuncioSelezionato(Annuncio annuncio) {
		this.annuncioSelezionato = annuncio;
	}
	
	public Annuncio getAnnuncioSelezionato() {
		return annuncioSelezionato;
	}
	
	public void setOggettiOfferti(OffertaScambio offertaScambio, ArrayList<Oggetto> listaOggettiOfferti) {
		offertaScambio.setOggettiOfferti(listaOggettiOfferti);
	}
	
	public int checkDatiRegistrazione(ArrayList<String> credenziali) {
		String nome = credenziali.get(0);
		String cognome = credenziali.get(1);
		String matricola = credenziali.get(2);
		String email = credenziali.get(3);
		String username = credenziali.get(4);
		String password = credenziali.get(5);
	
		if(nome == "" || cognome == "" ||  matricola == "" || email == "" || username ==  "" || password ==  "") {
			return 1;
		}
		
	    if ((nome.length() > 40 || nome.length() < 2) || isValidNameSurname(nome) == 1) {
	        return 2;
	    }

	    if ((cognome.length() > 40 || cognome.length() < 2) || isValidNameSurname(cognome) == 1) {
	        return 3;
	    }
	    
	    if (matricola.length() != 9 || matricola.contains(" ") || matricola.contains("\t")) {
	        return 4;
	    }
	    
	    if(isValidEmail(email) == 1) {
	    	 return 5;
	    } 
	    
	    if (username.length() > 10 || username.contains(" ") || username.contains("\t")) {
	        return 6;
	    }
	    
	    if (password.length() < 8 || password.length() > 20) {
	        return 7;
	    }
	    
	    if(new StudenteDAO().CheckStudenteEsistente(matricola, email, username) == 1) {
	    	return 8;
	    }
	    
	    return 0;
	}
	
	public void InserisciStudente(ArrayList<String> credenziali){
	    StudenteDAO studenteDAO = new StudenteDAO();
	    studenteDAO.Save(new Studente(credenziali.get(2), credenziali.get(3), credenziali.get(0), credenziali.get(1), credenziali.get(4)), credenziali.get(5));	
	    
	}
	
	private int isValidEmail(String email) {
	    if (email == null || email.contains(" ") || email.contains("\t")) {
	        return 1;
	    }

		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) return 0;
        
        return 1;
	}
	
	private int isValidNameSurname(String s) {
		
		String sRegex = "^[A-Za-zÀ-ÿ'\\s]+$";

        Pattern pattern = Pattern.compile(sRegex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) return 0;
        
        return 1;
	}
	
	public int CheckLoginStudente(String username, String password){
		Studente studente = new StudenteDAO().LoginStudente(username, password);
		if(studente != null) {
			this.studente = studente;
			return 0;
		}
		
		return 1;
	}
	
	public Oggetto getOggettoById(int idOggetto) {
		return new OggettoDAO().getOggettoById(idOggetto);
	}
	
	public int getIdByOggetto(Oggetto oggetto) {
		return new OggettoDAO().getIdByOggetto(oggetto);
	}
	
	public int getIdBySede(Sede sede) {
		return new SedeDAO().getIdBySede(sede);
	}
	
	public ArrayList<Annuncio> getInfoAnnunci(Studente studente) {
	    return new AnnuncioDAO().getAnnunci(studente);
	}
	
	public ArrayList<Annuncio> getAnnunciStudente(Studente studente) {
	    return new AnnuncioDAO().getAnnunciStudente(studente);
	}
	
	public Studente getStudente() {
	    return this.studente;
	}
	
	public int checkDatiAnnuncio(ArrayList<String> datiAnnuncio, File fileSelezionato, Studente studente, ArrayList<String> listaOggettiDesiderati) {
		
		String titolo = datiAnnuncio.get(0).trim();
		if(titolo == "" || titolo.length() > 50) return 1;
		
		String categoria = datiAnnuncio.get(1);
		if(categoria.equals("")) return 2;
		
		String inizioOrarioDisponibilità = datiAnnuncio.get(2).trim();
		String fineOrarioDisponibilità = datiAnnuncio.get(3).trim();
		
		if (!LocalTime.parse(inizioOrarioDisponibilità).isBefore(LocalTime.parse(fineOrarioDisponibilità))) {
		    return 3;
		}
		
		String giorniDisponibilità = datiAnnuncio.get(4).replaceAll("-$", "").trim();
		if(giorniDisponibilità == "") return 4;
		
		String descrizione = datiAnnuncio.get(5).trim();
		if(descrizione.equals("") || descrizione.length() > 100) return 5;
		
		String particellatoponomastica = datiAnnuncio.get(6).trim();
		String descrizioneIndirizzo = datiAnnuncio.get(7).trim();
		String civico = datiAnnuncio.get(8).trim();
		String cap = datiAnnuncio.get(9).trim();
		
		String capRegex = "^[0-9]{5}$";
		
		if(particellatoponomastica == "" || descrizioneIndirizzo == "" || descrizioneIndirizzo.length() > 100 || civico == "" ||
		civico.length() > 4 || cap == "" || cap.length() != 5 || !cap.matches(capRegex)) return 6;
		
		String tipologia = datiAnnuncio.get(10);
		
		if(tipologia == "") return 7;
		
		double prezzo = 0.0;
		
		if(tipologia.equals("Vendita")){
			String stringaPrezzo = datiAnnuncio.get(11);
			
			String prezzoRegex = "^\\d{1,3}(\\.\\d{1,2})?$";
		    if (!stringaPrezzo.matches(prezzoRegex)) return 8;
		    
			prezzo = Double.parseDouble(stringaPrezzo);
			
			if(prezzo <= 0 || prezzo >= 1000) return 8;
		}
		
		if(fileSelezionato == null || fileSelezionato.getName().isEmpty() || fileSelezionato.getName().startsWith("no_image")) return 9;
		
		String appoggioDescrizione = descrizione;
		
		if(tipologia.equals("Scambio")) {
			if(listaOggettiDesiderati.size() < 1 || listaOggettiDesiderati.size() > 5) return 10;
			
			descrizione = "Oggetti desiderati: ";
			
			for(int i = 0; i < listaOggettiDesiderati.size(); i++) {
				descrizione = descrizione + listaOggettiDesiderati.get(i);
				if(i != listaOggettiDesiderati.size() - 1) descrizione = descrizione + ", ";
			}
			
			descrizione += "\n" + appoggioDescrizione;
			descrizione = descrizione.trim();
		}
		Sede sede = new Sede(particellatoponomastica, descrizioneIndirizzo, civico, cap);
		int idSede = new SedeDAO().SaveSade(sede);
		sede.setIdSede(idSede);
		
		String percorso = fileSelezionato.getAbsolutePath();
		
		Oggetto oggetto = new Oggetto(percorso, categoria, appoggioDescrizione, studente);
		int idOggetto = new OggettoDAO().SaveOggetto(oggetto);
		oggetto.setIdOggetto(idOggetto);
		
		Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());
		
		Annuncio annuncio = new Annuncio(titolo, true, inizioOrarioDisponibilità, fineOrarioDisponibilità, prezzo, tipologia, descrizione, oggetto, sede, giorniDisponibilità, dataCorrente);
		
		this.studente.getAnnunciPubblicati().clear();
		
		if(new AnnuncioDAO().SaveAnnuncio(annuncio, oggetto, sede) == 1) return 11;
		
		return 0;
	}
	
	public void copiaFileCaricato(File fileSelezionato) {
    	try {
    		File destinationDir = new File(System.getProperty("user.dir"), "src/application/IMG/uploads");
    		if (!destinationDir.exists()) destinationDir.mkdirs();
    		File destinationFile = new File(destinationDir, fileSelezionato.getName());
    		Files.copy(fileSelezionato.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    	catch (IOException ex) {
            System.err.println("Errore durante la copia del file: " + ex.getMessage());
        }
	}
	
	public void copiaImmagineProfiloCaricata(File immagineSelezionata) {
    	try {
    		File destinationDir = new File(System.getProperty("user.dir"), "src/application/IMG/immaginiProfilo");
    		if (!destinationDir.exists()) destinationDir.mkdirs();
    		File destinationFile = new File(destinationDir, immagineSelezionata.getName());
    		Files.copy(immagineSelezionata.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    	catch (IOException ex) {
            System.err.println("Errore durante la copia del file: " + ex.getMessage());
        }
	}
	
	public void caricaFileImmagine(String file){
		StudenteDAO studenteDAO = new StudenteDAO();
		String uploadsDir = System.getProperty("user.dir") + "/src/application/IMG/immaginiProfilo/";
		String percorsoAssoluto = uploadsDir + file;
		this.studente.setImmagine(percorsoAssoluto);
		studenteDAO.cambiaFoto(studente.getMatricola(), percorsoAssoluto);
	}
	
	public ArrayList<Annuncio> getAnnunciByFiltri(String keyword, String categoria, String tipologia) {
	    return new AnnuncioDAO().getAnnunciByFiltri(this.studente, keyword, categoria, tipologia);
	}
	
	public void AcquistaOggetto(Annuncio a){
		new AnnuncioDAO().cambiaStatoAnnuncio(a);
	}
	
	public int inviaOffertaVendita(Annuncio annuncio) {
		Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());
		OffertaVendita offertaVendita = new OffertaVendita(dataCorrente, this.studente, annuncio, annuncio.getPrezzo());
		
		int result = new OffertaDAO().SaveOfferta(offertaVendita);
		
        if(result == 0) controller.SvuotaOfferteInviate();
        
		return result;
	}
	
	public int inviaOffertaRegalo(Annuncio annuncio, String motivazione){
	    if(motivazione == null || motivazione.isEmpty()) motivazione = "Assente";
	    if(motivazione.length()>255) return 1;
		Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());
		OffertaRegalo offertaRegalo = new OffertaRegalo(dataCorrente, this.studente, annuncio, motivazione);
		
		int result = new OffertaDAO().SaveOfferta(offertaRegalo);
		
		if(result == 0) SvuotaOfferteInviate();
		
		return result;
	}
	
	public int inviaOffertaScambio(Annuncio annuncio, ArrayList<Oggetto> listaOggettiOfferti){
        Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());
        
		OffertaScambio offertaScambio = new OffertaScambio(dataCorrente, this.studente, annuncio);
		
		int idOffertaInserita = new OffertaDAO().SaveOfferta(offertaScambio);
        
        if(idOffertaInserita == -1) {
        	return -1;
        }
        
        if(!listaOggettiOfferti.isEmpty()) {
        	
            OggettoDAO oggettoDao = new OggettoDAO();
            OggettiOffertiDAO oggettiOffertiDao = new OggettiOffertiDAO();
    		
    		Oggetto oggettoPrimario, oggettoSecondario;
        	
        	for(int i = 0; i < listaOggettiOfferti.size(); i++) {
        		oggettoPrimario = listaOggettiOfferti.get(i);
        		for(int j = i + 1; j < listaOggettiOfferti.size(); j++) {
            		oggettoSecondario = listaOggettiOfferti.get(j);
        			if(oggettoPrimario.getDescrizione().equals(oggettoSecondario.getDescrizione())
            		&& oggettoPrimario.getCategoria().equals(oggettoSecondario.getCategoria())
            		&& oggettoPrimario.getStudente().getMatricola().equals(oggettoSecondario.getStudente().getMatricola())
            		&& oggettoPrimario.getImmagineOggetto().equals(oggettoSecondario.getImmagineOggetto())){
        				System.out.println("Oggetti duplicati");
            			return -2;
            		}
        		}
        	}
        	
    		int idOggettoInserito = 0;
        	
	        for(Oggetto o: listaOggettiOfferti){
	        	idOggettoInserito = oggettoDao.SaveOggetto(o);
	        	oggettiOffertiDao.SaveOggettoOfferto(idOffertaInserita, idOggettoInserito);
	        }
	        
	        setOggettiOfferti(offertaScambio, listaOggettiOfferti);
        	SvuotaOfferteInviate();
	        
			return 1;
        }
        
    	SvuotaOfferteInviate();

        return 0;
	}
	
	public int checkControffertaVendita(Annuncio annuncio, String stringaPrezzo) {
	    String prezzoRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
	    
	    if (!stringaPrezzo.matches(prezzoRegex)) {
	        return 1;
	    }

	    double prezzo = Double.parseDouble(stringaPrezzo);

	    if (prezzo <= 0 || prezzo >= annuncio.getPrezzo()) {
	        return 1;
	    }

	    Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());

	    OffertaVendita offerta = new OffertaVendita(dataCorrente, this.studente, annuncio, prezzo);

	    int risultato = new OffertaDAO().SaveOfferta(offerta);
	    
	    if(risultato == 0) SvuotaOfferteInviate();

	    return risultato;
	}
	
	public int checkModificaControffertaVendita(OffertaVendita offerta, String stringaPrezzo) 
	{
	    String prezzoRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
	    if (!stringaPrezzo.matches(prezzoRegex)) {
	        return 1;
	    }
	    System.out.println(stringaPrezzo);
	    double prezzo = Double.parseDouble(stringaPrezzo);

	    if (prezzo <= 0 || prezzo == offerta.getPrezzoOfferta() || prezzo > offerta.getAnnuncio().getPrezzo()) {
	        return 1;
	    }

	    int risultato = new OffertaDAO().UpdateOffertaVendita(prezzo, offerta.getStudente(),  offerta.getAnnuncio());
	    
	    if(risultato == 0) {
	    	controller.setPrezzoOfferta(offerta, Double.parseDouble(stringaPrezzo));
	    	controller.SvuotaOfferteInviate();
	    }
	    
	    return risultato;
	}
	
	public int editOffertaRegaloMotivazione(OffertaRegalo offerta, String motivazione) {
	    if (motivazione == null) motivazione = "";
	    motivazione = motivazione.trim().replaceAll("\\s{2,}", " ");
	    String motivazioneRegex = "^[\\p{L}0-9,.!?;:'\"()\\s-]*$";
	    
	    if (!motivazione.matches(motivazioneRegex) || motivazione.length()>255) {
	        return 1;
	    }
	    
	    if (motivazione.isEmpty()) {
	        motivazione = "Assente";
	    }
	    
	    String vecchiaMotivazione = offerta.getMotivazione();
	    if (vecchiaMotivazione == null || vecchiaMotivazione.trim().isEmpty()) {
	        vecchiaMotivazione = "Assente";
	    }

	    if (vecchiaMotivazione.equals(motivazione)) {
	        return 1;
	    }

	    offerta.setMotivazione(motivazione);
	    
	    int risultato = new OffertaDAO().UpdateOffertaRegalo(motivazione, offerta.getStudente(), offerta.getAnnuncio());
	    
	    if(risultato == 0) {
            offerta.setMotivazione(motivazione);
	    	controller.SvuotaOfferteInviate();
	    }

	    return risultato;
	}
	
	public int editImmagineOggettoOfferto(Offerta offerta, Oggetto oggetto, String path) {
		if(offerta instanceof OffertaScambio) {
			OffertaScambio offertaScambio = (OffertaScambio) offerta;
			
			for (Oggetto o : offertaScambio.getOggettiOfferti()) {
			    if (o.getImmagineOggetto().equals(path) &&
			        o.getCategoria().equals(oggetto.getCategoria()) &&
			        o.getDescrizione().equals(oggetto.getDescrizione()) &&
			        o.getStudente().getMatricola().equals(oggetto.getStudente().getMatricola())) {
			        return 1;
			    }
			}
			
			int risultato = new OggettoDAO().UpdateImmagineOggetto(oggetto, path);
			
			if(risultato == 0) {
				new OffertaDAO().UpdateStatoOfferta(offerta);
				SvuotaOfferteInviate();
			}
			
			return risultato;
		}
		
		return 1;
	}
	
	public int controllaCampiOggettoScambio(String descrizione, String categoriaSelezionata, String percorsoImmagine) {
		
		if(categoriaSelezionata == "") return 1;
		
		if(descrizione == "" || descrizione.length() > 255) return 2;
		
		if(percorsoImmagine == null || percorsoImmagine.isEmpty() || percorsoImmagine.startsWith("no_image")) return 3;
		
		return 0;
	}
	
	public Studente getStudenteByMatricola(String matricola) {
		return new StudenteDAO().getStudenteByMatricola(matricola);
	}
	
	public int rimuoviAnnuncio(Annuncio annuncio) {
		int risultatoRimozioneAnnuncio = new AnnuncioDAO().rimuoviAnnuncio(annuncio);
		int risultatoRimozioneOfferte = rimuoviOfferte(annuncio.getIdAnnuncio());
		int risultatoRimozioneOggettiOfferti = rimuoviOggettiOfferti(annuncio.getOggetto().getIdOggetto());
        int risultatoRimozioneOggetto = (annuncio.getOggetto().getIdOggetto());
        int risultatoRimozioneSede = rimuoviSede(annuncio.getSede().getIdSede());
		
		if(risultatoRimozioneAnnuncio >= 0 && risultatoRimozioneOfferte >= 0 && risultatoRimozioneOggettiOfferti >= 0 && risultatoRimozioneOggetto >= 0 && risultatoRimozioneSede >= 0) {
			this.studente.getAnnunciPubblicati().clear();
			return 0;
		}
		
		return 1;
	}
	
	public int rimuoviOfferte(int idAnnuncio) {
		return new OffertaDAO().rimuoviOfferteByIdAnnuncio(idAnnuncio);
	}
	
	public int rimuoviOggettiOfferti(int idOfferta) {
		return new OggettiOffertiDAO().rimuoviOggettiOffertiByIdOfferta(idOfferta);
	}
	
	public int rimuoviSede(int idSede) {
		return new SedeDAO().rimuoviSede(idSede);
	}
	
	public int rimuoviOggetto(int idOggetto) {
		return new OggettoDAO().rimuoviOggettoByIdOggetto(idOggetto);
	}
	
	public ArrayList<Offerta> getOffertebyAnnuncio(Annuncio annuncio) {
	    ArrayList<Offerta> offerte = new OffertaDAO().getOffertebyAnnuncio(annuncio);

	    boolean verificaOfferteScambio = true;

	    for (Offerta offerta : offerte) {
	        if (!(offerta instanceof OffertaScambio)) {
	            verificaOfferteScambio = false;
	        }
	    }

	    if (verificaOfferteScambio) {
	        for (Offerta offerta : offerte) {
	            setOggettiOfferti((OffertaScambio) offerta, getOggettiOffertiByOfferta(offerta));
	        }
	    }

	    return offerte;
	}
	
	public int accettaOfferta(Offerta o) {
		int risultato = new OffertaDAO().accettaOfferta(o);
		
		if(risultato == 0) controller.SvuotaAnnunciPubblicati();
		
		return risultato;
	}
	
	public int rifiutaOfferta(Offerta o) {
		int risultato = new OffertaDAO().rifiutaOfferta(o);
		
		if(risultato == 0) this.studente.getAnnunciPubblicati().clear();
		
		return risultato;
	}
	
	public int getIdByOfferta(Offerta o) {
		return new OffertaDAO().getIdByOfferta(o);
	}
	
	public ArrayList<Oggetto> getOggettiOffertiByOfferta(Offerta offerta) {
		if(offerta instanceof OffertaScambio) {
			OffertaScambio offertaScambio = (OffertaScambio) offerta;
			//modificato
			return new OffertaDAO().getOggettiOffertiByOfferta(offertaScambio);
		}
		
		return null;
	}
	
	public ArrayList<Offerta> getOffertebyMatricola(Studente studente) {
	    ArrayList<Offerta> offerte = new OffertaDAO().getOffertebyMatricola(studente);

	    boolean verificaOfferteScambio = true;

	    for (Offerta offerta : offerte) {
	        if (!(offerta instanceof OffertaScambio)) {
	            verificaOfferteScambio = false;
	        }
	    }

	    if (verificaOfferteScambio) {
	        for (Offerta offerta : offerte) {
	            setOggettiOfferti((OffertaScambio) offerta, getOggettiOffertiByOfferta(offerta));
	        }
	    }

	    return offerte;
	}
    
    public int eliminaOfferta(Offerta offerta) {
		return new OffertaDAO().eliminaOfferta(offerta);
	}
    
    public void SvuotaAnnunciPubblicati() {
        this.studente.getAnnunciPubblicati().clear();
    }
    
    public void SvuotaOfferteInviate() {
    	this.studente.getOfferteInviate().clear();
    }
    
    public int controllaOggettoDesiderato(String nomeOggetto, ArrayList<String> listaOggetti) {
    	String nomeOggettoDesiderato = nomeOggetto.trim().toLowerCase();
    	
    	String regexOggettoDesiderato = "^(?!.* {2})[A-Za-zÀ-ÿ0-9\\s]+$";

		Pattern pattern = Pattern.compile(regexOggettoDesiderato);
		Matcher matcher = pattern.matcher(nomeOggettoDesiderato);
		
		if(listaOggetti.size()== 5) return 3;
		
		if(nomeOggettoDesiderato == "" || nomeOggettoDesiderato.length() < 2 || nomeOggettoDesiderato.length() > 20 || !matcher.matches()) return 1;
	
		for(String s: listaOggetti) {
			if(s.equals(nomeOggettoDesiderato)) return 2;
		}
		
		return 0;
	}
    
    public void setAnnunciVisibili(ArrayList<Annuncio> lista)
    {
    	this.studente.setAnnunciVisibili(lista);
    }
    
    public void setAnnunciPubblicati(ArrayList<Annuncio> lista)
    {
    	this.studente.setAnnunciPubblicati(lista);
    }
    
    public void setOfferteInviate(ArrayList<Offerta> lista)
	{
		this.studente.setOfferteInviate(lista);
	}
    
    public void rimuoviOggettoDesideratoDaLista(ArrayList<String> listaOggetti, String nomeOggetto) {
        listaOggetti.remove(nomeOggetto);
    }

    public int editOffertaScambio(Oggetto oggetto, Offerta offerta, String pathImmagine , String categoria, String descrizione) {   	
    	if(offerta instanceof OffertaScambio) {
    		OffertaScambio offertaScambio = (OffertaScambio) offerta;
    		
	    	if(getImmagineOggetto(oggetto).equals(pathImmagine) && getCategoriaOggetto(oggetto).equals(categoria) && getDescrizioneOggetto(oggetto).equals(descrizione) ) 
	    	{
	    		return 1;
	    	}
	    	
	    	ArrayList<Oggetto> listaOggettiOfferti = offertaScambio.getOggettiOfferti();
	    	
	    	for(Oggetto o : listaOggettiOfferti) {
	        	if(getImmagineOggetto(o).equals(pathImmagine) && getCategoriaOggetto(o).equals(categoria) && getDescrizioneOggetto(o).equals(descrizione) ) 
	        	{
	        		return 2;
	        	}
	    	}
	    	OggettoDAO oggettoDAO = new OggettoDAO();
	    	
	    	if(oggettoDAO.UpdateOggetto(oggetto,pathImmagine, categoria, descrizione) == 1)
	    	{
	    		return 3;
	    	}
	    	
	    	new OffertaDAO().UpdateStatoOfferta(offertaScambio);
	    	
	    	offertaScambio.setOggettiOfferti(getOggettiOffertiByOfferta(offertaScambio));
	    	
	    	SvuotaOfferteInviate();
	    	
	    	return 0;
    	}
    	
    	return -1;
    }
    
    public int rimuoviOggettoOfferto(Oggetto oggetto, Offerta offerta) {
    	if(offerta instanceof OffertaScambio) {
    		OffertaScambio offertaScambio = (OffertaScambio) offerta;
	    	int idOggetto = new OggettoDAO().getIdByOggetto(oggetto);
	    	int idOfferta = new OffertaDAO().getIdByOfferta(offertaScambio);
	    	new OggettiOffertiDAO().rimuoviOggettoOffertoById(idOggetto, idOfferta);
	    	new OggettoDAO().rimuoviOggettoByIdOggetto(idOggetto);
	    	offertaScambio.setOggettiOfferti(getOggettiOffertiByOfferta(offertaScambio));
	    	new OffertaDAO().UpdateStatoOfferta(offerta);
	    	SvuotaOfferteInviate();
	    	return 0;
    	}
    	
    	return 1;
    }
    
    public int getNumeroOfferteInviate(Studente studente, String tipologia) {
		return new OffertaDAO().getNumeroOfferteInviate(studente, tipologia);
	}
    
    public int getNumeroOfferteAccettate(Studente studente, String tipologia) {
		return new OffertaDAO().getNumeroOfferteAccettate(studente, tipologia);
	}
    
    public double getInformazioniOfferteVendita(Studente studente, String tipoFunzione) {
    	return new OffertaDAO().getPrezzoMinimoOfferte(studente, tipoFunzione);
    }
    
    public String getUsername(Studente studente) {
    	return studente.getUsername();
    }
    
    public String getNome(Studente studente) {
    	return studente.getNome();
    }
    
    public String getMatricola(Studente studente) {
    	return studente.getMatricola();
    }
    
    public String getEmail(Studente studente) {
    	return studente.getEmail();
    }
    
    public String getCognome(Studente studente) {
		return studente.getCognome();
	}
    
    public void svuotaListaOggettiOfferti(OffertaScambio offertaScambio) {
		offertaScambio.getOggettiOfferti().clear();
	}
    
    public String getImmagineProfilo(Studente studente) {
		return studente.getImmagineProfilo();
	}
    
    public Oggetto getOggetto(Annuncio annuncio) {
    	return annuncio.getOggetto();
    }
    
    public String getImmagineOggetto(Oggetto oggetto) {
    	return oggetto.getImmagineOggetto();
    }
    
    public String getTitoloAnnuncio(Annuncio annuncio) {
    	return annuncio.getTitoloAnnuncio();
    }
    
    public double getPrezzoOfferta(OffertaVendita offertaVendita) {
		return offertaVendita.getPrezzoOfferta();
	}
    
    public String getMotivazioneOfferta(OffertaRegalo offertaRegalo) {
    	return offertaRegalo.getMotivazione();
    }
    
    public void setMotivazioneOfferta(OffertaRegalo offertaRegalo, String motivazione) {
		offertaRegalo.setMotivazione(motivazione);
	}
    
    public void setPrezzoOfferta(OffertaVendita offertaVendita, double prezzo) {
    	offertaVendita.setPrezzoOfferta(prezzo);
    }
    
    public boolean getStatoAnnuncio(Annuncio annuncio) {
    	return annuncio.isStatoAnnuncio();
    }
    
    public double getPrezzoAnnuncio(Annuncio annuncio) {
    	return annuncio.getPrezzo();
    }
    
    public void setStatoOfferta(Offerta offerta, String statoOfferta) {
		offerta.setStatoOfferta(statoOfferta);
	}
    
    public String getTipologiaAnnuncio(Annuncio annuncio) {
    	return annuncio.getTipologia();
    }
    
    public Oggetto getOggettoAnnuncio(Annuncio annuncio) {
    	return annuncio.getOggetto();
    }
    
    public String getCategoriaOggetto(Oggetto oggetto) {
    	return oggetto.getCategoria();
    }
    
    public Timestamp getDataPubblicazioneAnnuncio(Annuncio annuncio) {
    	return annuncio.getDataPubblicazione();
    }
    
    public String getDescrizioneAnnuncio(Annuncio annuncio) {
		return annuncio.getDescrizioneAnnuncio();
	}
    
    public String getStatoOfferta(Offerta offerta) {
    	return offerta.getStatoOfferta();
    }
    
    public Timestamp getDataPubblicazioneOfferta(Offerta offerta) {
		return offerta.getDataPubblicazione();
	}
    
    public Studente getStudenteOfferta(Offerta offerta) {
    	return offerta.getStudente();
    }
    
    public Studente getStudenteOggetto(Oggetto oggetto) {
    	return oggetto.getStudente();
    }
    
    public String getDescrizioneOggetto(Oggetto oggetto) {
		return oggetto.getDescrizione();
	}
    
    public String giorniDisponibilitaAnnuncio(Annuncio annuncio) {
		return annuncio.getGiorni();
	}
    
    public String getFasciaInizioAnnuncio(Annuncio annuncio) {
    	return annuncio.getFasciaOrariaInizio();
    }
    
    public String getFasciaFineAnnuncio(Annuncio annuncio) {
		return annuncio.getFasciaOrariaFine();
	}
    
    public Sede getSedeAnnuncio(Annuncio annuncio) {
		return annuncio.getSede();
	}
    
    public String getGiorniAnnuncio(Annuncio annuncio) {
		return annuncio.getGiorni();
	}
    
    public String getParticellaToponomasticaSede(Sede sede) {
    	return sede.getParticellaToponomastica();
    }
    
    public String getDescrizioneIndirizzo(Sede sede) {
		return sede.getDescrizioneIndirizzo();
	}
    
    public String getCivicoSede(Sede sede) {
    	return sede.getCivico();
    }
    
    public String getCapSede(Sede sede) {
    	return sede.getCap();
    }
    
    public Annuncio getAnnuncioOfferta(Offerta offerta) {
		return offerta.getAnnuncio();
	}
    
    public String getcategoriaOggetto(Oggetto oggetto) {
    	return oggetto.getCategoria();
    }
    
    public ArrayList<Annuncio> getAnnunciVisibili(){
		return this.studente.getAnnunciVisibili();
	}
    
    public void setDataPubblicazioneOfferta(Offerta offerta, Timestamp data) {
    	offerta.setDataPubblicazione(data);
    }
    
	public ArrayList<Oggetto> getOggettiOfferti(OffertaScambio offertaScambio) {
		return offertaScambio.getOggettiOfferti();
	}
	
	public ArrayList<Offerta> getOfferteInviate(Studente studente){
		return this.studente.getOfferteInviate();
	}
}