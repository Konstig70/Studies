package Luokat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Saaliit {
    private Saalis[] saaliit;
    private int koko;
    private int index = 0;
    private List<Integer> poistettavatId = new ArrayList<>();

    /**
     * muodostaja tyjhään taulukkoon
     * @param koko kuinka suuri
     */
    public Saaliit(int koko) {
        this.koko = koko;
        this.saaliit = new Saalis[koko];
    }

    public int getKoko() {
        return index;
    }

    /**
     * lisää saaliin olioon
     * @param saalis olio joka lisätään
     *@example <pre name="test">
     *  Saaliit saaliit = new Saaliit(1);
     * Saalis s = new Saalis("iso", "ahven", "lippa", "sininen");
     * saaliit.lisaa(s);
     * Saalis s2 = new Saalis("iso", "ahven", "lippa", "sininen");
     * saaliit.lisaa(s2);
     * saaliit.getKoko() === 2;
     * </pre>
     */
    public void lisaa(Saalis saalis) {
        if (index == koko){
            kasvataTaulukkoa();
            saaliit[index] = saalis;
            index++;
        }
        else {
            saaliit[index] = saalis;
            index++;
        }
    }

    /**
     * Kasvattaa taulukon eli siis luo uuden
     */
    private void kasvataTaulukkoa() {

        Saalis[] taulukko = new Saalis[koko + 10];
        for (int i = 0; i < koko; i++){
            taulukko[i] = saaliit[i];
        }
        koko += 10;
        saaliit = taulukko;

    }

    public Saalis getSaalis(int index) {
        return saaliit[index];
    }

    /**
     * suorittaa tallennukse ei tallenna null-viitteitä eikä poistettavia olioita
     */
    public void tallenna() {
        Tulkki lajit = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
        Tulkki vieheTulkki = new Tulkki(System.getProperty("user.dir") + "/../vieheTyypit.dat");
        Tulkki kokoTulkki = new Tulkki(System.getProperty("user.dir") + "/../koot.dat");
        TiedostonHiplailija saalisData = new TiedostonHiplailija(System.getProperty("user.dir")+ "/../saaliit.dat",false);
        for (Saalis s : saaliit){
            if (s != null) {
                if (!s.getPoista()){
                    saalisData.kirjoita(s.tiedotTallenusMuodossa(lajit,vieheTulkki,kokoTulkki));
                }
            }
        }
        saalisData.sulje();
    }

    public List<Saalis> getSaaliitList() {
        List<Saalis> saaliitList = new ArrayList<>();
        for (Saalis saalis : saaliit){
            if (saalis != null){
                saaliitList.add(saalis);
            }
        }
        return saaliitList;
    }

    public static void main(String[] args){
        Saaliit saaliit = new Saaliit(2);
        Saalis s = new Saalis("iso", "ahven", "lippa", "sininen");
        saaliit.lisaa(s);
        Saalis s2 = new Saalis("iso", "hauki", "vaappu", "sininen");
        saaliit.lisaa(s2);
        Saalis s3 = new Saalis("iso", "kuha", "lippa", "hopea");
        saaliit.lisaa(s3);
    }

    /**
     * merkkaa saaliin poistettavaksi
     * @param saaliinId mikä poistetaan
     */
    public void merkkaaPoistettavaksi(int saaliinId) {
        int poistettava = binHaku(saaliinId);
        if (poistettava == -1) {
            return;
        }
        saaliit[poistettava].poista();
    }

    /**
     * hakee tietokannasta
     * @param key saaliin id
     * @return haetun saaliin paikan taulukosta
     */
    public int binHaku(int key) {
        int alin = 0;
        int ylin = index - 1;
        while (alin <= ylin){
            int keski = (alin + ylin) / 2;
            if (saaliit[keski].getSaaliinId() == key){
                return keski;
            }
            if (saaliit[keski].getSaaliinId() < key){
                alin = keski + 1;
            }
            if (saaliit[keski].getSaaliinId() > key){
                ylin = keski - 1;
            }
        }
        return -1;
    }

    public boolean loytyyko(Saalis saalis) {
        int indeksi = binHaku(saalis.getSaaliinId());
        if (indeksi == -1) {
            return false;
        }
        return true;
    }
}
