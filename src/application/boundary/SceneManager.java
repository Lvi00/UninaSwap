package application.boundary;

import application.control.Controller;
import application.entity.Annuncio;
import application.entity.Offerta;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneManager {
	private Controller controller = Controller.getController();
	private static SceneManager sceneManager = null;
	
	public static SceneManager sceneManager() {
        if (sceneManager == null) {
        	sceneManager = new SceneManager();
        }
        return sceneManager;
    }
	
	public void SelezionaPagina(String nomePagina, MouseEvent e) {            
        System.out.println("Navigazione a: " + nomePagina);

        switch (nomePagina) {
            case "Prodotti":
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Prodotti.fxml"));
                    Parent root = loader.load();
                    ProdottiBoundary prodottiCtrl = loader.getController();
                    prodottiCtrl.costruisciPagina();
                    prodottiCtrl.CostruisciCatalogoProdotti();
                    prodottiCtrl.setFiltri();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("UninaSwap - Prodotti");
                    stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            break;
                
            case "Offerte":
	            try {
	    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offerte.fxml"));
	    	        Parent root = loader.load();
	                OfferteBoundary offerteCtrl = loader.getController();
	                offerteCtrl.costruisciPagina();
	                offerteCtrl.CostruisciOfferteUtente();
	                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    	        Scene scene = new Scene(root);
	    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
	    	        stage.setScene(scene);
	    	        stage.setTitle("UninaSwap - Offerte");
	    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
	    	        stage.setResizable(false);
	    	        stage.show();
	            }
	            catch (Exception ex) {
	                ex.printStackTrace();
	            }
            break;
            
            case "Informazioni":
            	try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Informazioni.fxml"));
                    Parent root = loader.load();
                    InformazioniBoundary infoCtrl = loader.getController();
                    infoCtrl.costruisciPagina();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("UninaSwap - Informazioni");
                    stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            break;

            case "Crea annuncio":
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreaAnnuncio.fxml"));
                    Parent root = loader.load();
                    CreaAnnuncioBoundary creaCtrl = loader.getController();
                    creaCtrl.costruisciPagina();
                    creaCtrl.setCampiForm();
	                creaCtrl.MostraPaneVendita(e);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("UninaSwap - Crea annuncio");
                    stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            break;
                
            case "I tuoi annunci":
	            try {
	    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnnunciStudente.fxml"));
	    	        Parent root = loader.load();
	                AnnunciStudenteBoundary annunciCtrl = loader.getController();
	                annunciCtrl.costruisciPagina();
	                annunciCtrl.CostruisciProdottiUtente();
	                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    	        Scene scene = new Scene(root);
	    	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
	    	        stage.setScene(scene);
	    	        stage.setTitle("UninaSwap - I tuoi annunci");
	    	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
	    	        stage.setResizable(false);
	    	        stage.show();
	            }
	            catch (Exception ex) {
	                ex.printStackTrace();
	            }
            break;
            
            case "Registrazione":
            	try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrazione.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("UninaSwap - Registrazione");
                    stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                    stage.setResizable(false);
                    stage.show();
        		}
        		catch(Exception ex) {
        			ex.printStackTrace();
        		}
            break;
            
            case "Login":
        	    try {
        	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        	        Parent root = loader.load();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        	        Scene scene = new Scene(root);
        	        scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
        	        stage.setScene(scene);
        	        stage.setTitle("UninaSwap - Login");
        	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
        	        stage.setResizable(false);
        	        stage.show();
        	    }
        	    catch(Exception ex) {
        	        ex.printStackTrace();
        	    }
            break;

            default:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Profilo.fxml"));
                    Parent root = loader.load();
                    ProfiloBoundary ProfiloCtrl = loader.getController();
                    ProfiloCtrl.costruisciPagina();
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setTitle("UninaSwap - Profilo");
                    stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            break;
        }
    }
	
	public void showPopupError(Node container, String title, String message) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupError.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) container.getScene().getWindow();
	        Stage stage = new Stage();
	        stage.initOwner(mainStage);
	        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("UninaSwap - " + title);
	        stage.setResizable(false);
	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));
	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	        mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
	        stage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));
	        stage.show();
	        stage.setX(mainStage.getX() + (mainStage.getWidth() - stage.getWidth()) / 2);
	        stage.setY(mainStage.getY() + (mainStage.getHeight() - stage.getHeight()) / 2 - 50);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void showPopupAlert(Node container, String title, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupAlert.fxml"));
	        Parent root = loader.load();
	        Stage mainStage = (Stage) container.getScene().getWindow();
	        Stage stage = new Stage();
	        stage.initOwner(mainStage);
	        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("UninaSwap - " + title);
	        stage.setResizable(false);
	        stage.getIcons().add(new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm()));

	        PopupErrorBoundary popupController = loader.getController();
	        popupController.setLabels(title, message);
	       
	        mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
	        stage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

	        stage.show();
	        
	        stage.setX(mainStage.getX() + (mainStage.getWidth() - stage.getWidth()) / 2);
	        stage.setY(mainStage.getY() + (mainStage.getHeight() - stage.getHeight()) / 2 - 50);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void showInfoOfferta(MouseEvent e, Offerta offerta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOfferteAnnuncio.fxml"));
            Parent root = loader.load();
            PopupOfferteAnnuncioBoundary popupInfoController = loader.getController();
            popupInfoController.setPopupInfoOfferta(offerta);
            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("UninaSwap - Informazioni di " + controller.getTipologiaOfferta(offerta).toLowerCase());
            popupStage.getIcons().add(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
            popupStage.setResizable(false);
            mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
            popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));

            popupStage.show();

            popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void mostraOggettiDesiderati(MouseEvent e) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOggettiDesiderati.fxml"));
            Parent root = loader.load();
            PopupOggettiDesideratiBoundary popupOggettiDesideratiCtrl = loader.getController();              
            popupOggettiDesideratiCtrl.costruisciTabella(controller.getListaOggettiDesiderati());
            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("UninaSwap - Oggetti desiderati");
            popupStage.getIcons().add(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
            popupStage.setResizable(false);
            mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
            popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));
            popupStage.show();
            popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void mostraEditOfferta(MouseEvent e, Offerta offerta) {
		try {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupEditOfferta.fxml"));
		   Parent root = loader.load();
		   PopupEditOffertaBoundary popupEditController = loader.getController();
		   Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		   Stage popupStage = new Stage();
		   popupStage.initOwner(mainStage);
		   popupStage.initModality(Modality.WINDOW_MODAL);
		   Scene scene = new Scene(root);
		   scene.getStylesheets().add(
		       getClass().getResource("../resources/application.css").toExternalForm()
		   );
		   popupStage.setScene(scene);
		   popupStage.setTitle("UninaSwap - Modifica offerta");
		   popupStage.getIcons().add(
		       new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
		   );
		   popupStage.setResizable(false);
		   mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
		   popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));    	       
		   popupEditController.CostruisciPopupEdit(offerta, popupStage);
		   popupStage.show();
		   popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
		   popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);
		}
		catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	public void showPopupOfferte(MouseEvent e, Annuncio annuncio) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOfferte.fxml"));
            Parent root = loader.load();
            PopupOfferteBoundary popupCtrl = loader.getController();
            popupCtrl.setProdottiBoundary();
            controller.setAnnuncioSelezionato(annuncio);
            popupCtrl.costruisciPopup();
            Stage mainStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage popupStage = new Stage();
            popupStage.initOwner(mainStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            popupStage.setScene(scene);
            popupStage.setTitle("UninaSwap - " + annuncio.getTipologia());
            popupStage.getIcons().add(
                new Image(getClass().getResource("../IMG/immaginiProgramma/logoApp.png").toExternalForm())
            );
            popupStage.setResizable(false);
            mainStage.getScene().getRoot().setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.5, 0));
            popupStage.setOnHidden(event -> mainStage.getScene().getRoot().setEffect(null));
            popupStage.show();
            popupStage.setX(mainStage.getX() + (mainStage.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(mainStage.getY() + (mainStage.getHeight() - popupStage.getHeight()) / 2 - 40);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
