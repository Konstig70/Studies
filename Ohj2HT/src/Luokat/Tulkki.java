package Luokat;


import java.util.HashMap;
import java.util.List;


/**
 * Tulkki luokka, tarvitaan perus tietojen, kuten lajien, kokojen ja vieheiden tyyppien muuttamiseen id:stä
 */
public class Tulkki {

	/**
	 * poikkeusluokka jos tulkki ei löydä arvoa
	 */
	public static class EiLoydyException extends RuntimeException {
		public EiLoydyException(String message) {
			super(message);
		}
	}

	private int id = 1;
	 private final HashMap<Integer, String> arvot = new HashMap<>();

	/**
	 * Muodostin suoraan tiedoston riveistä
 	 * @param osoite tiedostosta luetut rivit
	 */
	 public Tulkki(String osoite) {
		TiedostonHiplailija th = new TiedostonHiplailija(osoite);
		List<String> rivit = th.lueRivit();
		th.sulje();
		 for (String rivi : rivit) {
	    	 String[] a = rivi.split("\\|");
	    	 arvot.put(id, a[1]);
	    	 id++;
	    	}

	    }

	/**
	 * hakee id:n perusteella arvon
	 * @param id mistä etsitään
	 * @return arvon, mikä saadaan id:llä tai null, jos id ei löydy mitään
	 */
	public String getValueFromId(int id) throws EiLoydyException {
	    String s = arvot.get(id);
		if (s == null) throw new EiLoydyException("Ei löydetty arvoa avaimella " + id);
		return s;
	    }

	/**
	 * hakee arvon perusteella id:n
 	 * @param value arvo
	 * @return id:n
	 */
	public int getIdFromValue(String value){
		List<Integer> idt = arvot.keySet().stream().toList();
		for(Integer i : idt){
			if(arvot.get(i).equals(value)){
				return i;
			}
		}
		throw new EiLoydyException("Ei Id:tä arvolla " + value);
	}


	    
	    
	    
	    public static void main(String[] args) {
	    	Tulkki test = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
	    	test.getValueFromId(2);
	    	Tulkki test2 = new Tulkki(System.getProperty("user.dir") + "/../koot.dat");
	    	test2.getValueFromId(3);
	    	System.out.println(test.getIdFromValue("lohi"));
	    	System.out.println(test.getIdFromValue("iso"));
	    }
	    
	}
