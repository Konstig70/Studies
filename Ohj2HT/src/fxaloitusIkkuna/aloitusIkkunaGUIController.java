package fxaloitusIkkuna;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author konst
 * @version 22.1.2025
 * kontrolleri luokka aloitus ikkunnalle toteuttaa modalController rajapinnan
 */
public class aloitusIkkunaGUIController implements ModalControllerInterface<BooleanProperty> {

    @FXML private MediaView pyorivaKala; //id mediaview oliolle

    private BooleanProperty suljetaanko;


    @Override
    public BooleanProperty getResult() {
        return null;
    }

    @Override
    public void setDefault(BooleanProperty booleanProperty) {
        this.suljetaanko = booleanProperty;
    }


    @Override
    public void handleShown() {

    }


    /**
     * initialize eli käynnistys metodi, asetetaan video ja laitetaan se autoplaylle
     */

    public void initialize(){
        String sijainti = getClass().getResource("/media/spinning_fish.mp4").toExternalForm();
        //haetaan src hakemiston alta media hakemistosta tiedosta ja muutetaan sen sijainti Stringiksi
        System.out.println(sijainti);
        Media kala = new Media(sijainti);
        //luodaan media olento sijainnilla
        MediaPlayer mediaPlayer = new MediaPlayer(kala);
        //luodaan mediaplayer
        mediaPlayer.seek(Duration.ZERO);
        pyorivaKala.setMediaPlayer(mediaPlayer);
        //asetetaan mediaview oliolle soitin
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //konfigguraatiot

        mediaPlayer.play();

        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("MediaPlayer status: " + newValue);
            if (newValue == MediaPlayer.Status.READY) {
                mediaPlayer.play();
            }
        });
        mediaPlayer.setOnError(() -> System.err.println("Media error: " + mediaPlayer.getError().getMessage()));
        //loggausta
    }

    /**
     * jatka napin käsittelly, eli sulkee vain ikkunan
     */
    @FXML private void handleJatka(){
        ModalController.closeStage(pyorivaKala);
    }

    /**
     * Ohje napin käsittely
     */

    @FXML private void handleOhje(){
        APUA();
    }

    /**
     * Avaa ohjelman suunnitelman selaimessa.
     */
    private void APUA(){
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/v/2025/kevat/ht/niemimm");
            desktop.browse(uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleTakaisin() {
        suljetaanko.set(true);
        //tämä boolean property valvoo suljetaanko koko sovellus aloitusnäytöstä
        ModalController.closeStage(pyorivaKala);
    }
}
