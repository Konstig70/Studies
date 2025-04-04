package fxLisaaReissu;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxKalenteri.KalenteriMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import Luokat.*;

/**
 * @author miikk
 * @version 29.1.2025
 * Controller reissun lisäämis ikkunaa varten
 */
public class LisaaReissuGUIController implements Initializable, ModalControllerInterface<Kalenteri> {

	@FXML
	private Label tiedot;

	@FXML
    private TextField Pvm;

	 @FXML
	 private TextArea kaloja;

	 @FXML
	private Pane pane;

	@FXML
    private TextField keli;

    @FXML
    private TextField sijainti;
    
    private List<Saalis> saaliit = new ArrayList<>();
	private Kalenteri kale;
	
	
	@FXML private void handleTallenna() {
    	 Tallenna();

    }
	 
	 @FXML private void handlePoista(){
		 Poista();
	 }
	 
	 @FXML private void handleKaloja() {
		 	Kaloja();
	 }
	 
	 //-------------------------//
	 
	 private void Kaloja() {
		 LocalDate date = Reissu.sToDate(Pvm.getText());
		 if (date == null) {
		     Dialogs.showMessageDialog("Virheellinen päivämäärämuoto: " + Pvm.getText() + "\nSyötä muodossa pp-kk-vvvv.");
		     return;
		 }
		 
		ModalController.showModal(KalenteriMain.class.getResource("/fxKaljoja/KalojaGUIView.fxml"),
				 "Lisää saalis",null,saaliit);

		 liitaKaloja();
	 }
	 
	 public void liitaKaloja() {
		kaloja.clear();
		for(Saalis s : saaliit){
			 Platform.runLater(()->{kaloja.appendText(s.getLaji() + " " +s.getKoko()+ " saatu vieheellä "  + s.getViehe().vari()+ " "  + s.getViehe().tyyppi());kaloja.appendText("\n");});
		 }
	 }
	 
	 private void Poista() {
		 
	 }

	/**
	 * Lisää reissun reissut-olion listaan ts tallentaa sen
	 */
	public void Tallenna() {
		 LocalDate date = Reissu.sToDate(Pvm.getText());
		 if (date == null) {
		     Dialogs.showMessageDialog("Virheellinen päivämäärämuoto: " + Pvm.getText() + "\nSyötä muodossa pp-kk-vvvv.");
		     return;
		 }
		 String pvm = Pvm.getText();
		 Reissu a = new Reissu(Reissu.sToDate(pvm), sijainti.getText(),keli.getText(),saaliit, false);
		 kale.lisaaReissu(a, saaliit);
		Integer [] koordinaatit = kartanPiirtaja.haeKoordinaatti(sijainti.getText());
		if(kartanPiirtaja.piirraKuvaan(koordinaatit[0],koordinaatit[1])){
			Dialogs.showMessageDialog("Kuva päivitettiin käynnistä sovellus uudestaan, jotta näet päivitykset");
		}
		ModalController.closeStage(pane);

	 }
	

	
	
	 
	@Override
	public Kalenteri getResult() {
		return kale;
	}

	@Override
	public void setDefault(Kalenteri k) {
		this.kale = k;
	}

	@Override
	public void handleShown() {

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		kaloja.setEditable(false);
	}

}
