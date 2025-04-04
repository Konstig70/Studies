package Luokat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import Luokat.Reissu;

/**
 * Pitää sisällään kaikki reissut
 */
public class Reissut {

	    private List<Reissu> reissut;

	/**
	 * muodostaja
	 */
	public Reissut(Saaliit saaliit) {
		this.reissut = new ArrayList<>();
		luoReissut(saaliit);
	}

	
	private void luoReissut(Saaliit saaliit) {
		TiedostonHiplailija t = new TiedostonHiplailija(System.getProperty("user.dir") + "/../reissut.dat");
		List<Saalis> saaliitList = saaliit.getSaaliitList(); //Kaikki saaliit
		for (String s : t.lueRivit()) {
			Reissu a = new Reissu(s);
			List<Saalis> apuri = new ArrayList<>(); // Tässä luodaan tietyn reissun saaliiden lista
			for (Saalis saalis : saaliitList) {
				if (saalis.getReissunTagi() == a.getTag()) {
					apuri.add(saalis);
				}
			}
			a.setSaaliit(apuri);
			this.reissut.add(a);
		}
	}

	/**
	 * lisää reissun listaan
	 * @param reissu mikä reissu lisätään
	 */
	public void lisaaListaan(Reissu reissu) {
	   	reissut.add(reissu);
	}


	/**
	 * palauttaa reissu listan
	 * @return reissu lista
	 */
	public List<Reissu> getReissut() {
		return reissut;
	}
	
	
	public void tallenna(){
		TiedostonHiplailija reissu = new TiedostonHiplailija(System.getProperty("user.dir") + "/../reissut.dat",false);
		for (Reissu r : reissut) {
			System.out.println(r);
			reissu.kirjoita(r.toString());
		}
		reissu.sulje();
	}

	/**
	 * etsii reissun päivämäärän perusteella, jokerina toimii * merkki
	 * @param paivamaara mikä päivä
	 * @return listan reissuista joilla on haluttu päivämäärä
	 */
	
	public static List<Reissu> etsi(String paivamaara, Reissut reissut) {
        List<Reissu> re = new ArrayList<>();
          
        paivamaara = paivamaara.replace('.', '-').replace('/', '-').replace(',', '-').replace(' ', '-').trim();
        String[] osat = paivamaara.split("-");
        
        String paiva = "*", kuukausi = "*", vuosi = "*";

        if (osat.length > 0) paiva = osat[0];
        if (osat.length > 1) kuukausi = osat[1];
        if (osat.length > 2) vuosi = osat[2];

        if (!paiva.equals("*") && paiva.length() == 1) paiva = "0" + paiva;
        if (!kuukausi.equals("*") && kuukausi.length() == 1) kuukausi = "0" + kuukausi;

        
        for (Reissu t : reissut.getReissut()) {
            LocalDate date = t.getPaivamaara(); 

            String reissuPaiva = String.format("%02d", date.getDayOfMonth());
            String reissuKuukausi = String.format("%02d", date.getMonthValue());
            String reissuVuosi = String.valueOf(date.getYear());

            
            if (!paiva.equals("*") && !paiva.equals(reissuPaiva)) continue;
            if (!kuukausi.equals("*") && !kuukausi.equals(reissuKuukausi)) continue;
            if (!vuosi.equals("*") && !vuosi.equals(reissuVuosi)) continue;

            re.add(t); 
        }

        return re;
    }
	
	
	public void sort() {
		reissut.sort(Comparator.comparing(Reissu::getPaivamaara).reversed());
	}
	
	
	/**
	 * käy reissut läpi ja katsoo onko missään yksittäisessä reissussa poistetaanko 
	 * arvo = true. Tällöin reissu poistetaan
	 */
	
	
	public void removeReissu() {
		   Iterator<Reissu> iterator = reissut.iterator();
		    while (iterator.hasNext()) {
		        Reissu reissu = iterator.next();
		        if (reissu.getpoistetaanko()) {
		            iterator.remove();  
		            break;
		        }
		    }
	}
	
	/* testit
	/**@example
	* <pre name="test">
	* 	#import java.time.LocalDate;
		#import java.util.ArrayList;
		#import java.util.List;

		#import org.junit.Before;
		#import org.junit.Test;

		#import Luokat.Reissu;
		#import Luokat.Reissut;
		#import Luokat.Tulkki;
		#import Luokat.Saaliit;
		
	* 
	* 	Saaliit saaliita = new Saaliit(8);	
	*    Reissut test = new Reissut(saaliita); 
	*    test.getReissut().clear();
	*    
	*    Reissu t0 = new Reissu(Reissu.sToDate("1.12.2323"), "ka", "pilvi", new ArrayList<>(), false); 
	*    Reissu t1 = new Reissu(Reissu.sToDate("12.12.2023"), "jk", "sade", new ArrayList<>(), false); 
	*    Reissu t2 = new Reissu(Reissu.sToDate("12.12.1223"), "sk", "aurinko", new ArrayList<>(), false); 
	*    Reissu t3 = new Reissu(Reissu.sToDate("15.11.2024"), "ka", "pilvi", new ArrayList<>(), false); 
	*    test.lisaaListaan(t1); 
	*    test.lisaaListaan(t2); 
	*    test.lisaaListaan(t3); 
	*    test.lisaaListaan(t0);
	*    test.getReissut().size() === 4;
	*    t1.poistetaan();
	*    
	*    test.removeReissu();
	*     test.getReissut().size() === 3;
	*     
	* </pre>
	*/

	public static void main(String[] args) {
		 	Saaliit saaliita = new Saaliit(8);	
		    Reissut test = new Reissut(saaliita); 
		    test.getReissut().clear();
		    
		    Reissu t0 = new Reissu(Reissu.sToDate("1.12.2323"), "ka", "pilvi", new ArrayList<>(), false); 
		    Reissu t1 = new Reissu(Reissu.sToDate("12.12.2023"), "jk", "sade", new ArrayList<>(), false); 
		    Reissu t2 = new Reissu(Reissu.sToDate("12.12.1223"), "sk", "aurinko", new ArrayList<>(), false); 
		    Reissu t3 = new Reissu(Reissu.sToDate("15.11.2024"), "ka", "pilvi", new ArrayList<>(), false); 
		    test.lisaaListaan(t1); 
		    test.lisaaListaan(t2); 
		    test.lisaaListaan(t3); 
		    test.lisaaListaan(t0);
		    test.getReissut().size();
		    t1.poistetaan();
		   
		    test.removeReissu();
		     test.getReissut().size();
		    
				List<Reissu> a = new ArrayList<>();
				a = etsi("*,*,1223", test);
				a.size();
				System.out.println(a);
	}
	
	//todo:
	//tallenna()

}


