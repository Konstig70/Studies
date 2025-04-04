package fxaloitusIkkuna;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author konst
 * @version 22.1.2025
 * aloitusikkunan pääohjelma
 */
public class aloitusIkkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("aloitusIkkunaGUIView.fxml"));
            final Pane root = ldr.load();
            //final aloitusIkkunaGUIController aloitusikkunaCtrl = (aloitusIkkunaGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("aloitusikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("aloitusIkkuna");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}