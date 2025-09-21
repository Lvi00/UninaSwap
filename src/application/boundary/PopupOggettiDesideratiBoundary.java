package application.boundary;

import java.util.ArrayList;

import application.control.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class PopupOggettiDesideratiBoundary {

    private Controller controller;
    @FXML private AnchorPane paneOggettiOfferti;
    @FXML private TableView<String> tabellaOggettiDesiderati;
    @FXML private TableColumn<String, String> colNome;
    @FXML private TableColumn<String, String> colAzioni;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void costruisciTabella(ArrayList<String> listaOggetti) {
        ObservableList<String> items = FXCollections.observableArrayList(listaOggetti);
        tabellaOggettiDesiderati.setItems(items);
        
        tabellaOggettiDesiderati.setPlaceholder(new Label(""));

        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        addTooltipToColumn(colNome);

        colAzioni.setCellFactory(col -> new TableCell<>() {
            private final Button removeButton = new Button();

            {
                ImageView imageView = new ImageView(
                    new Image(getClass().getResourceAsStream("/application/IMG/immaginiProgramma/Delete.png"))
                );
                imageView.setFitWidth(24);
                imageView.setFitHeight(24);
                removeButton.setGraphic(imageView);
                removeButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-cursor: hand;");

                removeButton.setOnAction(e -> {
                    String oggetto = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(oggetto);
                    listaOggetti.remove(oggetto);
                    controller.rimuoviOggettoDesideratoDaLista(listaOggetti, oggetto);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new StackPane(removeButton));
            }
        });

        tabellaOggettiDesiderati.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaOggettiDesiderati.setFixedCellSize(30);

        tabellaOggettiDesiderati.getItems().addListener(
            (ListChangeListener<String>) change -> aggiornaAltezzaTabella()
        );

        tabellaOggettiDesiderati.setSelectionModel(null);

        aggiornaAltezzaTabella();
    }

    private void aggiornaAltezzaTabella() {
        int numeroRighe = tabellaOggettiDesiderati.getItems().size();
        double altezzaHeader = 28;
        double altezzaRighe = numeroRighe * tabellaOggettiDesiderati.getFixedCellSize();
        double altezzaTotale = numeroRighe == 0
                ? paneOggettiOfferti.getHeight() - tabellaOggettiDesiderati.getLayoutY()
                : altezzaHeader + altezzaRighe;

        tabellaOggettiDesiderati.setPrefHeight(altezzaTotale);
        tabellaOggettiDesiderati.setMinHeight(altezzaTotale);
        tabellaOggettiDesiderati.setMaxHeight(altezzaTotale);
    }

    private void addTooltipToColumn(TableColumn<String, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item);
                    Tooltip tooltip = new Tooltip(item);
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(200);
                    setTooltip(tooltip);
                }
            }
        });
    }
}