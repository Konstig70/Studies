package fxReissu;

import java.time.LocalDate;
import java.util.List;

import Luokat.Reissu;
import Luokat.Saaliit;
import Luokat.SaaliitJaReissu;
import Luokat.Saalis;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxKalenteri.KalenteriMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author miikk
 * @version 29.1.2025
 * Controller tietyn reissun näyttämisikkunaa varten
 */
public class ReissuGUIController implements ModalControllerInterface<SaaliitJaReissu> {
	private String label;
	private Reissu r;
	private Saaliit s;
	
	@FXML private Label reissuLabel; 
    
    @FXML private Button jatkaNappi;
   
    @FXML
    private TextField ReissuKeli;

    @FXML
    private TextField ReissuPvm;

    @FXML
    private TextField ReissuSijainti;

    @FXML
    private TextArea ReissunSaalis;



    @FXML public void handleLisaa() {
        lisaaSaaliita();
    }

    @FXML
    public void handleJatka() {
        jatka();
        ModalController.closeStage(jatkaNappi);
    }

    private void jatka() {
        LocalDate date = Reissu.sToDate(ReissuPvm.getText());
        if (date == null) {
            Dialogs.showMessageDialog("Virheellinen päivämäärämuoto: " + ReissuPvm.getText() + "\nSyötä muodossa pp-kk-vvvv.");
            return;
        }
        String uusPvm = ReissuPvm.getText();
        r.asetaKentat(Reissu.sToDate(uusPvm), ReissuSijainti.getText(), ReissuKeli.getText());
        for (Saalis saalis : r.getSaaliit()) {
            if (!s.loytyyko(saalis)) {
                s.lisaa(saalis);
                saalis.setReissunTagi(r.getTag());
            }
        }

    }

    @FXML
    void handlePoista() {
        r.poistetaan();

        ModalController.closeStage(ReissuSijainti);
    }

    @Override
    public SaaliitJaReissu getResult() {
    	String pvm = ReissuPvm.getText();
    	 return new SaaliitJaReissu(s,r);
    }

    @Override
    public void setDefault(SaaliitJaReissu sr) {
    	 if (sr == null) return;

    	 this.r = sr.reissu();
    	 this.s = sr.saaliit();
    	ReissuPvm.appendText(r.getPvmString());
    	ReissuKeli.appendText(r.getKeli());
    	ReissuSijainti.appendText(r.getSijainti());

    	ReissunSaalis.clear();
		for(Saalis s : r.getSaaliit()){
			 Platform.runLater(()->{ReissunSaalis.appendText( "Sait " + s.getLaji() + " " + s.getKoko()+ ", vieheellä "  +
		s.getViehe().vari()+ " "  + s.getViehe().tyyppi());ReissunSaalis.appendText("\n");});
		 }
    }

    private void lisaaSaaliita() {
        LocalDate date = Reissu.sToDate(ReissuPvm.getText());
        if (date == null) {
            Dialogs.showMessageDialog("Virheellinen päivämäärämuoto: " + ReissuPvm.getText() + "\nSyötä muodossa pp-kk-vvvv.");
            return;
        }

        ModalController.showModal(KalenteriMain.class.getResource("/fxKaljoja/KalojaGUIView.fxml"),
                "Lisää saalis",null,r.getSaaliit());
        liitaKaloja();
    }

    public void liitaKaloja() {
        ReissunSaalis.clear();
        for (Saalis s : r.getSaaliit()) {
            Platform.runLater(() -> {
                ReissunSaalis.appendText(s.getLaji() + " " + s.getKoko() + " saatu vieheellä " + s.getViehe().vari() + " " + s.getViehe().tyyppi());
                ReissunSaalis.appendText("\n");
            });
        }
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void handleShown() {

    }

}