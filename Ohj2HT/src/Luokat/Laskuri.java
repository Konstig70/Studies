package Luokat;

import fi.jyu.mit.fxgui.StringAndObject;
import fi.jyu.mit.ohj2.Tiedosto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Luokka laskuri toimintoja varten, osaa lukea tiedostoja ja asettaa sieltä arvoja
 */
public class Laskuri {


    private HashMap<Integer,Integer> arvot = new HashMap<>();
    private Tulkki tulkki;
    private TiedostonHiplailija th;
    private String osoite;

    public Laskuri(String osoite, Tulkki tulkki) {
        this.tulkki = tulkki;
        this.th = new TiedostonHiplailija(osoite);
        this.osoite = osoite;
        List<String> tiedot  = th.lueRivit();
        th.sulje();
        for(String t: tiedot) {
            int id = Integer.parseInt(t.split("\\|")[0]);
            int maara = Integer.parseInt(t.split("\\|")[1]);
            if (maara != 0){
                arvot.put(id,maara);
            }

        }
    }

    public void tallenna() {
        th = new TiedostonHiplailija(osoite,false);
        for (String s : tallennusTiedot()){
            th.kirjoita(s);
        }
        th.sulje();
    }

    public Laskuri(String[] tiedot,Tulkki tulkki) {
        for(String t: tiedot) {
            int id = Integer.parseInt(t.split("\\|")[0]);
            int maara = Integer.parseInt(t.split("\\|")[1]);
            arvot.put(id,maara);
            this.tulkki = tulkki;
        }
    }

    public void setTulkki(Tulkki tulkki) {
        this.tulkki = tulkki;
    }

    /**
     * kasvattaa laskurin arvoja
     * @param id mitä arvoa kasvatetaan
     * @example
     * <pre name="test">
     *  Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat"));
     *  l.kasvata(1);
     *  l.getLkm(1) === 3;
     * </pre>
     */
    public void kasvata(int id){
        if(arvot.containsKey(id)){
            arvot.put(id,arvot.get(id)+1);
            return;
        }
        arvot.put(id,1);
    }


    /**
     * kasvattaa laskurin arvoja
     * @param id mitä arvoa kasvatetaan
     * @example
     * <pre name="test">
     *  Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat"));
     *  l.vahenna(1);
     *  l.getLkm(1) === 1;
     * </pre>
     */
    public void vahenna(int id){
        arvot.put(id, arvot.get(id)-1);
    }



    public List<StringAndObject<String>> getArvotAsList(){
        List<StringAndObject<String>> arvotList = new ArrayList<>();
        this.arvot.forEach((k,v)->arvotList.add(new StringAndObject(tulkki.getValueFromId(k)+" "+v + " kpl",null)));
        return arvotList;
    }

    public int getSumma(){
        int summa = 0;
        Integer[] lkm = arvot.values().toArray(new Integer[0]);
        for (Integer i : lkm) {
            summa += i;
        }
        return summa;
    }

    /**
     * @return suurimman arvon id
     * @example <pre name="test">
     * Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat"));
     * l.getSuurinId() === 4;
     * </pre>
     */
    public int getSuurinId(){
        int suurin = Integer.MIN_VALUE;
        Integer[] lkm = arvot.values().toArray(new Integer[0]);
        for (Integer i : lkm) {
            if (i > suurin) {
                suurin = i;
            }
        }
        return suurinId(suurin);
    }

    /**
     * apumetodi getSuurinId metodilla
     * @param suurin suurin arvo laskurissa
     * @return suurimman arvon Id:n
     */
    private int suurinId(int suurin) {
        List<Integer> idt = arvot.keySet().stream().toList();
        for(Integer i : idt){
            if(arvot.get(i).equals(suurin)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Palauttaa laskurin arvon tietyllä avaimella
     * @param id avain
     * @return arvon
     */
    public int getLkm(int id){
        return arvot.get(id);
    }

    public List<String> tallennusTiedot(){
        List<String> tiedot = new ArrayList<>();
        arvot.forEach((k,v) -> tiedot.add(k + "|" + v) );
        return tiedot;
    }

    public static void main(String[] args) {
    	   String[] tiedot = new String[] {"1|2", "2|4", "3|1", "4|5"};
        Tulkki lajit = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
           Laskuri l = new Laskuri(tiedot,lajit);
           

         
           System.out.println(l.arvot);
           l.kasvata(1);
           l.kasvata(3);
           l.kasvata(4);

           System.out.println(l.arvot);

    }
}
