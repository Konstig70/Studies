package fxHeatMapTesti;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author konst
 * @version 14.2.2025
 */
public class HeatMapTestiMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("HeatMapTestiGUIView.fxml"));
            final Pane root = ldr.load();
            //final HeatMapTestiGUIController heatmaptestiCtrl = (HeatMapTestiGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("heatmaptesti.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("HeatMapTesti");
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