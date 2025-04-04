package fxLisaaReissu;

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
public class LisaaReissuMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaReissuGUIView.fxml"));
            final Pane root = ldr.load();
            //final LisaaReissuGUIController lisaareissuCtrl = (LisaaReissuGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lisaareissu.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LisaaReissu");
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