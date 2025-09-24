package application.boundary;

import application.control.Controller;
import application.entity.Offerta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PopupEditOffertaBoundary {
	@FXML private AnchorPane paneOffertaVendita;
	@FXML private AnchorPane paneOffertaScambio;
	@FXML private AnchorPane paneOffertaRegalo;
	@FXML private TextArea messaggioMotivazionale;
	@FXML private TextField campoPrezzoIntero;
	@FXML private TextField campoPrezzoDecimale;
	@FXML private Button inviaDatiOfferta;
	private Controller controller;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void CostruisciPopupEdit(Offerta o) {
		switch(o.getTipologia()) {
			case "Vendita":
				paneOffertaVendita.setVisible(true);
				paneOffertaScambio.setVisible(false);
				paneOffertaRegalo.setVisible(false);
				double prezzo = o.getPrezzoOfferta();
				String prezzoString = String.format("%.2f", prezzo);
				String[] parti = prezzoString.split("\\,");
				campoPrezzoIntero.setText(parti[0]);
				campoPrezzoDecimale.setText(parti[1]);
			break;
			
			case "Scambio":
				paneOffertaScambio.setVisible(true);
				paneOffertaVendita.setVisible(false);
				paneOffertaRegalo.setVisible(false);
			break;
			
			case "Regalo":
				paneOffertaRegalo.setVisible(true);
				paneOffertaVendita.setVisible(false);
				paneOffertaScambio.setVisible(false);
				if(o.getMotivazione().equals("Assente")) messaggioMotivazionale.setText("");
				else messaggioMotivazionale.setText(o.getMotivazione());
			break;
		}
		
		inviaDatiOfferta.setOnMouseClicked(event -> prelevaDatiOfferta(o));
	}
	
	private void prelevaDatiOfferta(Offerta o) {
		switch(o.getTipologia()) {
			case "Vendita":
				String prezzoIntero = campoPrezzoIntero.getText().trim();
				String prezzoDecimale = campoPrezzoDecimale.getText().trim();
				controller.editOffertaVendita(o, prezzoIntero, prezzoDecimale);
			break;
			
			case "Scambio":
				
			break;
			
			case "Regalo":
				String motivazione = messaggioMotivazionale.getText().trim();
				controller.editOffertaRegalo(o, motivazione);
			break;
		}
	}
}