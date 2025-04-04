package fxReissu;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author miikk
 * @version 29.1.2025
 *
 */
public class ReissuMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("ReissuGUIView.fxml"));
            final Pane root = ldr.load();
            //final ReissuGUIController reissuCtrl = (ReissuGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("reissu.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reissu");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}