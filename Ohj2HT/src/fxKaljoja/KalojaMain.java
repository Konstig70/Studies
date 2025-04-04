package fxKaljoja;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author miikk
 * @version 29.1.2025
 *
 */
public class KalojaMain extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KalojaGUIView.fxml"));
            final Pane root = ldr.load();
            //final KalojaGUIController kalojaCtrl = (KalojaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kaloja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kaloja");



            primaryStage.show();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}