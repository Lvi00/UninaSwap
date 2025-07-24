package application;

import application.boundary.LoginBoundary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
    @Override
    public void start(Stage stage) {
        try {
        	//Ottengo le dimensioni dello schermo
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth();
            double height = screenBounds.getHeight();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/Login.fxml"));
            Parent root = loader.load();
            LoginBoundary controller = loader.getController();

            stage.setWidth(width);
            stage.setHeight(height);
            stage.setX(0);
            stage.setY(0);

            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("UninaSwap - Login");
            stage.getIcons().add(new Image(getClass().getResource("IMG/logoApp.png").toExternalForm()));
            stage.setResizable(false);
            
            // Imposta i nodi alla dimensione corretta
            controller.setContainerSize(width, height);
            controller.setAnchorPaneSize(width, height);

            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}