package Luokat;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class TiedostonHiplailija {

	private PrintStream kirjoittaja;
	private Scanner lukija;
	private String tiedosto;
	
	public TiedostonHiplailija(String tiedosto) {
		 try{
			 this.tiedosto = tiedosto;
			 this.kirjoittaja = new PrintStream(new FileOutputStream(tiedosto, true));
			 this.lukija = new Scanner(new FileInputStream(new File(tiedosto)));
			 
	        } catch (FileNotFoundException ex) {
	            System.err.println("Tiedosto ei aukea: " + ex.getMessage());
	        }
	}

	public TiedostonHiplailija(String tiedosto, boolean appendataanko){
		try{
			this.tiedosto = tiedosto;
			this.kirjoittaja = new PrintStream(new FileOutputStream(tiedosto, appendataanko));
			this.lukija = new Scanner(new FileInputStream(new File(tiedosto)));

		} catch (FileNotFoundException ex) {
			System.err.println("Tiedosto ei aukea: " + ex.getMessage());
		}
	}

	/**
	 * kirjoittaa rivin tiedostoon
	 * @param sisalto kirjoitettava rivi
	 *@example
	 * <pre name="test">
	 * #THROWS IOException
	 * #import java.io.IOException;
	 * #import fi.jyu.mit.ohj2.VertaaTiedosto;
	 *  String tulos = "testataan kirjoitusta";
	 *  VertaaTiedosto.tuhoaTiedosto("tulos.txt");
	 *  TiedostonHiplailija t = new TiedostonHiplailija("tulos.txt");
	 *  t.kirjoita(tulos);
	 *  VertaaTiedosto.vertaaFileString("tulos.txt",tulos) === null;
	 *  VertaaTiedosto.tuhoaTiedosto("tulos.txt");
	 * </pre>

	 */
	public void kirjoita(String sisalto) {
		kirjoittaja.println(sisalto);
	}
	
	public void suljeKirjoittaja() {
		kirjoittaja.close();
	}

	public void sulje(){
		kirjoittaja.close();
		lukija.close();
	}

	public void suljeLukija(){
		this.lukija.close();
	}

	public List<String> lueRivit() {
	List<String> t = new ArrayList<>(); 
	 while (lukija.hasNextLine()) {
		 String s = lukija.nextLine();
		 if (!s.startsWith(";") && !s.isEmpty()) t.add(s);
	 }
	 return t;
	}


	public static void main(String[] args) {
		TiedostonHiplailija t = new TiedostonHiplailija(System.getProperty("user.dir") + "/../reissut.dat");
		for (String s : t.lueRivit()) {
			System.out.println(s);
		}
	}

}
	
