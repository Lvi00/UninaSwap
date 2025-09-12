package application.entity;

public class Offerta {
	private String statoOfferta;
    private double prezzoOfferta = 0;
    private String tipologia;

	    public Offerta(String tipologia) { 
	        this.statoOfferta = "Attesa";
	        this.tipologia = tipologia;
	    }

	    public void setPrezzoOfferta(double prezzoOfferta)
	    {
	        this.prezzoOfferta = prezzoOfferta;
	    }

	    public String getStatoOfferta() {
	        return statoOfferta;
	    }

	    public double getPrezzoOfferta() {
	        return prezzoOfferta;
	    }

	    public String getTipologia() {
	        return tipologia;
	    }

}
