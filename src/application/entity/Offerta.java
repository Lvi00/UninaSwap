package application.entity;

public class Offerta {
	private String statoofferta;
    private double prezzoofferta = 0;
    private String tipologia;

	    public Offerta(String tipologia) { 
	        this.statoofferta = "Attesa";
	        this.tipologia = tipologia;
	    }

	    public void setPrezzoOfferta(double prezzoofferta)
	    {
	        this.prezzoofferta = prezzoofferta;
	    }

	    public String getStatoOfferta() {
	        return statoofferta;
	    }

	    public double getPrezzoOfferta() {
	        return prezzoofferta;
	    }

	    public String getTipologia() {
	        return tipologia;
	    }

}
