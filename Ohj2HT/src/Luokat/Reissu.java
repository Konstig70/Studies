package Luokat;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka yhden reissun esittämistä varten osaa muodostaa reissun joko suoraan tiedoston 
 * tiedoista tai attribuuteista, sekä osaa myös muuttaa reissun tiedot tallennus muotoon.
 */
public class Reissu {
    private LocalDate Pvm;
    private static int SeuraavanReissunTag = 1;
    private int reissunTag;
    private String sijainti;
    private String keli;
    private List<Saalis> saaliit;
    private boolean poistetaanko;

/**
 * Konstruktori reissun luomiseen annetuilla tiedoilla.
 */
	public Reissu(LocalDate Pvm, String sijainti, String keli, List<Saalis> saaliit, boolean poistetaanko) {
		this.Pvm = Pvm;
		this.reissunTag = SeuraavanReissunTag;
        SeuraavanReissunTag++;
		this.sijainti = sijainti;
		this.keli = keli;
		this.saaliit = saaliit;
		this.poistetaanko = false;
	}
    public void asetaKentat(LocalDate Pvm, String sijainti, String keli) {
        this.Pvm = Pvm;
        this.sijainti = sijainti;
        this.keli = keli;
    }

	 /**
     * get metodit palauttavat halutun asian halutussa muodossa
     * 
     * testit gettereille
    /**@example
    * <pre name="test">
    *  #import Luokat.*;
    *  #import java.time.LocalDate;
	*	#import java.util.ArrayList;
	* 	#import java.util.List;

	* 	#import org.junit.*;

		#import Luokat.Reissu;
		#import Luokat.Saalis;
    
    * LocalDate pvm = LocalDate.of(2024, 4, 21);
    * List<Saalis> saaliit = new ArrayList<>();
    * Reissu r = new Reissu(pvm, "Helsinki", "aurinkoinen", saaliit, false);
    * 
    * r.getPaivamaara() === pvm;
    * r.getSijainti() === "Helsinki";
    * r.getpoistetaanko() === false;
    * r.getSaaliit() === saaliit;
    * </pre>
    */
    
    public int getTag() {
        return reissunTag;
    }

    public LocalDate getPaivamaara() {
        return this.Pvm;
    }
    public String getPvmString() {
    	 DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	    return Pvm.format(date);
    }
    public String getSijainti() {
        return sijainti;
    }
    public List<Saalis> getSaaliit() {
        return saaliit;
    }
    public String getKeli() {
        return keli;
    }
    public boolean getpoistetaanko() {
        return poistetaanko;
    }
    
    
    /**
     * asettaa reissun poistetaanko arvoksi true, jolloin sitä ei enään tarvita.
     */
    public void poistetaan() {
    	poistetaanko = true;
    	
    }

    
    /**
     * Muuntaa päivämäärän merkkijonosta LocalDate-muotoon. 
     * Tukee useita välimerkkejä ja yksinumeroisia päiviä/kuukausia.
     * @param pvm: päivämäärä, joka halutaan muuttaa.
     * @return LocalDate tai null, riippuen siitä oliko päivämäärä validi.
     * 
     * testi
     /**@example
     * <pre name="test">
     * sToDate("12.43 Å() 2004") === null;
     * sToDate("12.4.2004") === LocalDate.of(2004,4,12);
     * sToDate(" 12    3,2004") === null;
     * sToDate("12/4 2004") === LocalDate.of(2004,4,12);
     * sToDate("12 4,2004") === LocalDate.of(2004,4,12);
     * </pre>
  
     */
    
    public static LocalDate sToDate(String pvm) {
    	DateTimeFormatter paivamaara = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	
    	pvm = pvm.replace('.', '-').replace('/', '-').replace(',', '-').replace(' ', '-');
    	pvm = pvm.trim().replaceAll("\\s+", "");
    	
    	String[] osat = pvm.split("-");
    	if (osat.length == 3) {
    	    if (osat[0].length() == 1) osat[0] = "0" + osat[0];
    	    if (osat[1].length() == 1) osat[1] = "0" + osat[1];
    	    pvm = osat[0] + "-" + osat[1] + "-" + osat[2];
    	}
    	
    	if (onkoValid(pvm, paivamaara )) {
    		  System.out.println("Päivämäärä kelpaa: " + LocalDate.parse(pvm, paivamaara));
            return LocalDate.parse(pvm, paivamaara);
          
        } else {
            System.out.println("Virheellinen päivämäärämuoto!");
            return null;
        }
    }

    
    /**
     * Tarkistaa, onko annettu päivämäärä merkkijonona oikeassa muodossa.
     * @param dateStr Tarkistettava päivämäärä
     * @param formatter Käytettävä formatteri
     * @return true jos validi, muuten false
     */
    
   
    public static boolean onkoValid(String dateStr, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Konstruktori reissun luomiseen tiedostorivistä.
     * @param tiedosto Yksi reissurivi tiedostosta
     * 
     * testit
     /**@example
     * <pre name="test">
     * Reissu r = new Reissu("5|02.03.2024|Rovaniemi|kylmä");
     * r.getTag() === 5;
     * r.getPaivamaara() ===LocalDate.of(2024, 3, 2);
     * r.getSijainti() === "Rovaniemi";
     * r.getKeli() === "kylmä";
     * r.getpoistetaanko() === false;
     * </pre>
     */
    
    public Reissu (String tiedosto) {
    	String[] t = tiedosto.split("\\|"); 
    	this.Pvm = sToDate(t[1]);
    	this.sijainti = t[2];
    	this.keli = t[3];
        int tagi = Integer.parseInt(t[0]);
    	this.reissunTag = tagi;
        if (reissunTag > SeuraavanReissunTag) {
            SeuraavanReissunTag = this.reissunTag + 1;
        }
        else {
            SeuraavanReissunTag++;
        }
        this.poistetaanko = false;
    }
    
    
    /**
     * Asettaa reissun saalislistan.
     * @param saaliit Lista saaliista
     */
    
    public void setSaaliit(List<Saalis> saaliit) {
    	this.saaliit = saaliit;
    }
    
    
    /**
     * Muuntaa reissun tiedot tallennusmuotoon (merkkijonoksi).
     * @return tallennusmuotoinen merkkijono
     /**@example
     * <pre name="test">
     * LocalDate pvm = LocalDate.of(2023, 5, 10);
     * Reissu r = new Reissu(pvm, "Oulu", "selkeä", new ArrayList<>(), false);
     * String tallennus = r.toString();
     * tallennus.matches("\\d+\\|10\\.05\\.2023\\|Oulu\\|selkeä") === true;
     *  
     * </pre>
     */
    
    
    public String toString() {
    	String tag = Integer.toString(reissunTag);
        String pvm = Pvm.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace('-', '.');
    	 return  tag + "|" + pvm + "|" + sijainti + "|" + keli;
    }
    

    
    //nopea testi mainilla
    public static void main(String[] args) {
    	
    	Reissu testi = new Reissu( sToDate("01.12.2025"), "Jyväskylä", "lumisade", new ArrayList<>(), false);
        System.out.println(testi);
    	LocalDate date = LocalDate.parse("01-12-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate nyt = LocalDate.now();
        if (nyt.isBefore(date)) {
            System.out.println(nyt + " on myöhemmin kuin " + date);
        }


    }
}

