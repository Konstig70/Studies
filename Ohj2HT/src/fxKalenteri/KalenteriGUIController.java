package fxKalenteri;

import fi.jyu.mit.fxgui.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Luokat.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author konst
 * @version 16.1.2025
 */
public class KalenteriGUIController implements Initializable {

    private Kalenteri kalenteri;
    @FXML private ImageView Kartta;
    @FXML private ListChooser<String> Saaliisi;
    @FXML private Label reissuLkm;
    @FXML private ListChooser<Reissu> reissutLista;
    @FXML private Label saalisLkm;
    @FXML private Label lempiViehe;
    @FXML private TextField PvmHaku;
    private SimpleIntegerProperty reissumaara = new SimpleIntegerProperty();
    private SimpleIntegerProperty saalismaara = new SimpleIntegerProperty();
    private SimpleStringProperty parasViehe = new SimpleStringProperty();
    private int aluksi;
    private boolean onkoPoistettu = false;
    private ObservableList<StringAndObject<String>> lista;

    @FXML private void handleTallenna() {
        tallenna();
    }

    @FXML private void handleApua() { APUA();}

    @FXML private void handleReissu() {
        naytaReissu();
    }

    @FXML private void handleLisaaReissu(){
        LisaaReissu();
    }


    @FXML
    private void handleEtsi(ActionEvent event) {
    	haku();
    }


    /*
      * <------------------------------ TÄSTÄ ETEENPÄIN EI ENÄÄN HANDLEREITA ------------------------------>
     */
    
    /**
     * hakee ja kirjoittaa reissuja 
     * todo: paikan mukaan kanssa.
     */
    
    public void haku() {
    	String haluttuPvm = PvmHaku.getText();
    	if (haluttuPvm.isEmpty()) {
    		kirjoitaReissut();
    		return;
    	}
    	
    	List<Reissu> halutut = Reissut.etsi(haluttuPvm, kalenteri.getReissut());
    	
    	 if (halutut != null) {
             reissutLista.getItems().clear();
             for (Reissu reissu : halutut) {
                 reissutLista.add(reissu.getPvmString() + " " + reissu.getSijainti(),reissu);
             }
         }
    	
    }
    
    

    /**
     * Nollaa kartan
     */
    private void nollaaKartta(boolean vastaus) {
       if (vastaus) {
           File kopio = new File(System.getProperty("user.dir")+"/src/media/Kartta_backup.png");
           File vanha = new File(System.getProperty("user.dir")+"/src/media/UusiKarttakopio.png");
           try {
               Files.copy(kopio.toPath(), vanha.toPath(), StandardCopyOption.REPLACE_EXISTING);
               Platform.runLater(() -> Kartta.setImage(new Image("/media/UusiKarttakopio.png")));
               Dialogs.showMessageDialog("Kartan nollaus onnistui!");
           } catch (IOException e) {
               Dialogs.showMessageDialog("Virhe nollauksessa: " + e.getMessage());
           }
       }
    }

    /**
     * avaa uuden ikkunan "LisaaReissu", lisää reissun käyttöliittymään ja kasvattaa laskureita
     */
    private void LisaaReissu(){
        Kartta.setImage(null);
        ModalController.showModal(KalenteriMain.class.getResource("/fxLisaaReissu/LisaaReissuGUIView.fxml"),
        "Lisää reissu", null,kalenteri);
        kalenteri.paivitaTulkit();
        Platform.runLater(() -> Kartta.setImage(new Image("/media/UusiKarttakopio.png")));
        kirjoitaReissut();
        tekstienPaivitys();

    }


    /**
     * kirjoittaa reissut näkyville.
     */
    public void kirjoitaReissut() {
    	Reissut s = kalenteri.getReissut();
        s.sort();
    	 if (s != null) {
             reissutLista.getItems().clear();
             for (Reissu reissu : s.getReissut()) {
                 reissutLista.add(reissu.getPvmString() + " " + reissu.getSijainti(),reissu);
             }
         }
    }

    /**
     * Päivitetään laskureiden tekstejä vastaamaan lukumääriä
     */
    private void tekstienPaivitys() {
        Platform.runLater(()-> Saaliisi.setItems(FXCollections.observableArrayList(kalenteri.getKalaLaskuri().getArvotAsList())));
        reissumaara.set(kalenteri.getReissut().getReissut().size());
        saalismaara.set(kalenteri.getKalaLaskuri().getSumma());
        int paras = kalenteri.getVieheLaskuri().getSuurinId();
        if (paras != -1){
            parasViehe.set(kalenteri.getVieheTulkki().getValueFromId(paras));
        }

    }

    /**
     * Tallentaa tiedot ja piirtää kartan uudestaan.
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan odota hetki...");
        kirjoitaReissut();
        kalenteri.getReissut().sort();
        if (onkoPoistettu){
            if (kalenteri.tallennaTietyt()){
                kartanPiirto();
                Dialogs.showMessageDialog("Tallennus onnistui!");
                aluksi = reissumaara.get();
                return;
            }
        }
        if (kalenteri.tallennaTietyt()) {
            Dialogs.showMessageDialog("Tallennus onnistui!");
            aluksi = reissumaara.get();
        }
    }


    /**
     * Avaa ohjelman suunnitelman selaimessa.
     */
    private void APUA(){
    	  Desktop desktop = Desktop.getDesktop();
          try {
              URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/v/2025/kevat/ht/niemimm");
              desktop.browse(uri);
          } catch (URISyntaxException e) {
              return;
          } catch (IOException e) {
              return;
          }
    }

    
    /**
     * Näyttää tietyn reissun tiedot
     * Poistaa reissun haluttaessa
     */
    
    private void naytaReissu(){
    	// reissun näyttö
        String reissunNimi = reissutLista.getSelectedText();
        if (reissunNimi == null || reissunNimi.trim().isEmpty()) return;
       
        Reissu valittuReissu = this.reissutLista.getSelectedObject();
        int saaliitLkm = valittuReissu.getSaaliit().size();
        ModalController.showModal(
            KalenteriMain.class.getResource("/fxReissu/ReissuGUIView.fxml"),
            "Reissu " + this.reissutLista.getSelectedText(),null, new SaaliitJaReissu(kalenteri.getSaaliit(),valittuReissu));
        // Poistaminen
        if (valittuReissu.getpoistetaanko()) {
            onkoPoistettu = true;
        	System.out.println("Ennen poistoa " + kalenteri.getReissut().getReissut().size());
             System.out.println(valittuReissu.getpoistetaanko());
            for (Saalis s: valittuReissu.getSaaliit()){
                kalenteri.getKalaLaskuri().vahenna(kalenteri.getLajiTulkki().getIdFromValue(s.getLaji()));
                kalenteri.getVieheLaskuri().vahenna(kalenteri.getVieheTulkki().getIdFromValue(s.getViehe().tyyppi()));
                kalenteri.getSaaliit().merkkaaPoistettavaksi(s.getSaaliinId());
            }
            kalenteri.getReissut().removeReissu();
            System.out.println("Jälkeen " + kalenteri.getReissut().getReissut().size());
            List<Reissu> r = kalenteri.getReissut().getReissut();
            kalenteri.getReissut().sort();
            if (r != null) {
                reissutLista.getItems().clear();
                this.Saaliisi.getItems().clear();
                kirjoitaReissut();
                tekstienPaivitys();
            }
            return;
        }
        if (saaliitLkm < valittuReissu.getSaaliit().size()) {
            for (int i = saaliitLkm; i < valittuReissu.getSaaliit().size(); i++) {
                kalenteri.getKalaLaskuri().kasvata(kalenteri.getLajiTulkki().getIdFromValue(valittuReissu.getSaaliit().get(i).getLaji()));
            }
            kirjoitaReissut();
            tekstienPaivitys();
        }
        
        
        
    }

    /**
     * nollataan kartta ja piirretään uusiks
     */
    private void kartanPiirto() {
        nollaaKartta(true);
        List<Integer[]> koordinaatit = new ArrayList<>();
        for (Reissu r : kalenteri.getReissut().getReissut()) {
            koordinaatit.add(kartanPiirtaja.haeKoordinaatti(r.getSijainti()));
        }
        if (kartanPiirtaja.piirraKaikki(koordinaatit)) {
            Dialogs.showMessageDialog("Kuva päivitettiin käynnistä sovellus uudestaan, jotta näet päivitykset");
        }
    }


    /**
     * Alustus
     * @param url pakko laittaa
     * @param resourceBundle sama täs
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.kalenteri = new Kalenteri();
        //luodaan uusi kalenteri olio

        List<Reissu> r = kalenteri.getReissut().getReissut();
        aluksi = r.size();
        //taas otetaan selville monta reissua oli ohjelman käynnistyessä
        reissutLista.getItems().clear();
        kirjoitaReissut();
        //kirjoitetaan reissut näkyville
        this.reissuLkm.textProperty().bind(reissumaara.asString());
        this.saalisLkm.textProperty().bind(saalismaara.asString());
        this.lempiViehe.textProperty().bind(parasViehe);
        Saaliisi.setItems(FXCollections.observableArrayList(kalenteri.getKalaLaskuri().getArvotAsList()));
        tekstienPaivitys();
        //Alustetaan kaikki laskurit ja niiden tekstit
    }
    
    
    
    
    
    
}
