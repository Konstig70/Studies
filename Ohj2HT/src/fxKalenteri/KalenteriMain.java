package fxKalenteri;

import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * @author konst
 * @version 16.1.2025
 */
public class KalenteriMain extends Application {
    private BooleanProperty suljetaanko = new SimpleBooleanProperty(false);

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KalenteriGUIView.fxml"));
            final Pane root = ldr.load();
            //final KalenteriGUIController kalenteriCtrl = (KalenteriGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kalenteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kalenteri");
            primaryStage.show();
            ModalController.showModal(KalenteriMain.class.getResource("/fxaloitusIkkuna/aloitusIkkunaGUIView.fxml"),
                    "aloitusikkuna",
                    null,
                    suljetaanko);
            if(suljetaanko.get()) {
                primaryStage.close();
            }
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