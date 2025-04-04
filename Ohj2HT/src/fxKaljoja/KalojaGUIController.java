package fxKaljoja;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.DynamicComboBox;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Luokat.*;


/**
 * @author miikk
 * @version 29.1.2025
 * Luokka joka hoitaa saaliin lisäyksen reissuun
 */
public class KalojaGUIController implements Initializable, ModalControllerInterface<List<Saalis>> {
	@FXML private ComboBox<String> koko;

	@FXML private DynamicComboBox lajit;

	@FXML private TextField vieheVarit;

	@FXML private DynamicComboBox vieheet;

	private List<Saalis> saalis ;
	private int vanhatLajit;
	private int vanhatTyypit;
	
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle){
		koko.setItems(FXCollections.observableArrayList("sintti", "keskikokoinen", "iso", "kalavalhe"));
		TiedostonHiplailija lajeja = new TiedostonHiplailija(System.getProperty("user.dir") + "/../lajit.dat");
	   TiedostonHiplailija tyypit = new TiedostonHiplailija(System.getProperty("user.dir") + "/../vieheTyypit.dat");
	   //Asetetaan laatikoiden sisältö suoraan tiedostosta

		System.out.println("Lukija luotu!");
		List<String> ap = lajeja.lueRivit();
		String[] apuri = new String[ap.size()];
		for ( int i = 0; i < apuri.length; i++ ) {
			apuri[i] = ap.get(i).split("\\|")[1];
		}
		lajit.asetaSisalto(apuri);
		List<String> tyyppilista = new ArrayList<>();
		for (String s : tyypit.lueRivit()) {
			tyyppilista.add(s.split("\\|")[1]);
		}
		//Tässä samalla kokeillaa DynamicComboBoxin sisällön asetusta listasta ja taulukosta
		vieheet.asetaSisalto(tyyppilista);
		lajeja.sulje();
		tyypit.sulje();
		//Suljetaan tiedoston käsittelijät, jotta vapautuu resurssit
		vanhatLajit = lajit.getItems().size();
		vanhatTyypit = vieheet.getItems().size();
		//otetaan talteen tiedot laatikoiden sisällön määristä (tarvitaan myöhemmin)
   }

	 @FXML private void handlePoista(){
		 ModalController.closeStage(koko);
	 }

	@FXML private void handleTallenna() {
		Tallenna();
	}



	 private void Tallenna() {
	   ObservableList<String> uudet = lajit.getSisalto();
	   int erotus = uudet.size() - vanhatLajit;
	   //Tässä katsotaan luotiinko uusia lajeja valinta laatikkoon, jos luotiin niin tallennetaan uudet valinnat
	   if (erotus > 0){
		   TiedostonHiplailija t = new TiedostonHiplailija(System.getProperty("user.dir") + "/../lajit.dat",true);
		   for (int i = vanhatLajit; i < uudet.size(); i++){
			   t.kirjoita(i+1 +"|"+uudet.get(i));
		   }
		   t.sulje();
	   }
	   String kokoString = koko.getSelectionModel().getSelectedItem();
	   String lajiString = lajit.getSelectionModel().getSelectedItem();
	   //Varmistutaan, että käyttäjä on kertonut ainakin saaliin lajin ja koon, jotta voidaan tallentaa
	   if(kokoString != null && (lajiString != null && !lajiString.isEmpty())){
		   Saalis s = new Saalis(kokoString, lajiString, vieheet.getSelectionModel().getSelectedItem(),vieheVarit.getText());
		   saalis.add(s);
		   System.out.println("Lisätty:");
		   System.out.println("saalis : koko " + s.getKoko() + " laji " + s.getLaji() + " id " + s.getSaaliinId() + " Reissulta " + s.getReissunTagi());
		   System.out.println("saatu vieheellä " + s.getViehe().tyyppi() + " " + s.getViehe().vari());
	   }
		 ModalController.closeStage(koko);
	 }
	 

	@Override
	public List<Saalis> getResult() {
		return new ArrayList<>();
	}

	@Override
	public void setDefault(List<Saalis> saaliit) {
	   this.saalis = saaliit;

	}

	@Override
	public void handleShown() {
	}
}

