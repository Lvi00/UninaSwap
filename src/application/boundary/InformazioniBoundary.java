package application.boundary;

import java.io.File;

import application.control.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class InformazioniBoundary {	

    @FXML private HBox containerCatalogoProdotti;
    @FXML private Label usernameDashboard;
    @FXML private ImageView immagineNav;
    @FXML private Label labelOfferteInviateTotali;
    @FXML private Label labelOfferteAccettate;
    @FXML private BarChart<String, Number> chartOfferteInviate;
    @FXML private BarChart<String, Number> chartOfferteAccettate;
    @FXML private NumberAxis yAxisInviate;
    @FXML private NumberAxis yAxisAccettate;
    @FXML private CategoryAxis xAxisInviate;
    @FXML private CategoryAxis xAxisAccettate;
    
    private SceneManager sceneManager = SceneManager.sceneManager();
    private Controller controller = Controller.getController();

    public void costruisciPagina() {
        usernameDashboard.setText(controller.getUsername(controller.getStudente()));
        String immagineP = controller.getImmagineProfilo(controller.getStudente());
        try {
            // Gestione immagine profilo
            File file = new File(immagineP);
            Image image;
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                image = new Image(getClass().getResource(immagineP).toExternalForm());
            }
            
            immagineNav.setImage(image);
            Circle clip = new Circle(16.5, 16.5, 16.5);
            immagineNav.setClip(clip);
            immagineNav.setFitWidth(33);
            immagineNav.setFitHeight(33);  
            immagineNav.setPreserveRatio(false);

            // Popolamento grafico offerte inviate
            ObservableList<XYChart.Series<String, Number>> dataOfferteInviate = FXCollections.observableArrayList();
            dataOfferteInviate.add(creaSerieConTooltip("Vendita", controller.getNumeroOfferteInviate(controller.getStudente(), "Vendita")));
            dataOfferteInviate.add(creaSerieConTooltip("Scambio", controller.getNumeroOfferteInviate(controller.getStudente(), "Scambio")));
            dataOfferteInviate.add(creaSerieConTooltip("Regalo", controller.getNumeroOfferteInviate(controller.getStudente(), "Regalo")));
            chartOfferteInviate.setData(dataOfferteInviate);

            // Popolamento grafico offerte accettate
            ObservableList<XYChart.Series<String, Number>> dataOfferteAccettate = FXCollections.observableArrayList();
            dataOfferteAccettate.add(creaSerieConTooltip("Vendita", controller.getNumeroOfferteAccettate(controller.getStudente(), "Vendita")));
            dataOfferteAccettate.add(creaSerieConTooltip("Scambio", controller.getNumeroOfferteAccettate(controller.getStudente(), "Scambio")));
            dataOfferteAccettate.add(creaSerieConTooltip("Regalo", controller.getNumeroOfferteAccettate(controller.getStudente(), "Regalo")));
            chartOfferteAccettate.setData(dataOfferteAccettate);

            // Aggiorna etichette
            labelOfferteInviateTotali.setText("Numero offerte inviate: " + controller.getNumeroOfferteInviate(controller.getStudente(), ""));
            labelOfferteAccettate.setText("Numero offerte accettate: " + controller.getNumeroOfferteAccettate(controller.getStudente(), ""));
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore caricando immagine: " + immagineP);
        }
    }
    
    private XYChart.Series<String, Number> creaSerieConTooltip(String nome, int valore) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName(nome);

        XYChart.Data<String, Number> data = new XYChart.Data<>(nome, valore);
        serie.getData().add(data);

        // Tooltip installato quando il nodo è pronto
        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                Tooltip.install(newNode, new Tooltip(nome + ": " + valore));
            }
        });

        return serie;
    }

    @FXML
    public void SelezionaPagina(MouseEvent e) {
        Object source = e.getSource();
        Label label = (Label) source;
        String nomePagina = label.getText();
        sceneManager.SelezionaPagina(nomePagina, e);
    }
}
