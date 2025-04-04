package Luokat;

/**
 * Luokka yhtä saalista esittämistä varten
 */
public class Saalis {
    private static int seuraavanSaaliinId = 1;
    private final int saaliinId;
    private final String koko;
    private final String laji;
    private int reissunTagi;
    private final Viehe viehe;
    private boolean positetaanko = false;
    /**
     * muodostin merkkijono taulukosta, tarvitaan saaliin muodostamiseen tiedostosta
     * @param rivi mitä tietoja saaliin muodostamiseen tarvitaan
     */
    public Saalis(String rivi, Tulkki l, Tulkki v, Tulkki k) {
        String[] tiedot = rivi.split("\\|");
        int[] tiedotLuku = new int[tiedot.length];
        for (int i = 0; i < tiedotLuku.length-1; i++) {
            tiedotLuku[i] = Integer.parseInt(tiedot[i]);
        }
        this.saaliinId = tiedotLuku[0];
        if (this.saaliinId > seuraavanSaaliinId) {
            seuraavanSaaliinId = this.saaliinId + 1;
        }
        else {
            seuraavanSaaliinId++;
        }
        laji = l.getValueFromId(tiedotLuku[1]);
        koko = k.getValueFromId(tiedotLuku[2]);
        reissunTagi = tiedotLuku[3];
        this.viehe = new Viehe(v.getValueFromId(tiedotLuku[4]),tiedot[  5]);
    }

    /**
     * muodostin suoraan arvoista
     * @param koko saaliin koko
     * @param laji saaliin laji
     */
    public Saalis(String koko, String laji, String tyyppi, String vari) {
        this.koko = koko;
        this.laji = laji;
        this.viehe = new Viehe(tyyppi, vari);
        this.saaliinId = seuraavanSaaliinId;
        seuraavanSaaliinId++;

    }

    /**
     * asettaa reissun tagin
     * @param reissunTagi tagi
     * @example <pre name="test">
     * String rivi = "1|1|3|1|2|sininen";
     * Tulkki lajit = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
     * Tulkki vieheTulkki = new Tulkki(System.getProperty("user.dir") + "/../vieheTyypit.dat");
     * Tulkki kokoTulkki = new Tulkki(System.getProperty("user.dir") + "/../koot.dat");
     * Saalis s = new Saalis(rivi, lajit, vieheTulkki, kokoTulkki);
     * s.setReissunTagi(2);
     * s.getReissunTagi() === 2;
     * </pre>
     */
    public void setReissunTagi(int reissunTagi) {
        this.reissunTagi = reissunTagi;
    }

    /**
     * Tarvittavat getterit jokanen tekee sen mitä nimi sanoo
     * @return jonkin attribuuttinsa
     */
    public String getKoko() {
        return koko;
    }

    public String getLaji() {
        return laji;
    }
    public int getReissunTagi() {
        return reissunTagi;
    }

    public int getSaaliinId() {
        return saaliinId;
    }

    public void poista() {
        this.positetaanko = true;
    }

    public boolean getPoista() {
        return positetaanko;
    }

    /**
     * @return palauttaa saaliin tiedot tallennusmuodossa
     */
    public String tiedotTallenusMuodossa(Tulkki l, Tulkki v, Tulkki k){
        int lajiId =  l.getIdFromValue(laji);
        int kokoId = k.getIdFromValue(koko);
        int vieheId = v.getIdFromValue(this.viehe.tyyppi());
        return saaliinId + "|" + lajiId + "|" +kokoId + "|" + reissunTagi + "|" + vieheId+ "|" + viehe.vari();
    }

    public Viehe getViehe() {
        return viehe;
    }

    /*
    pieni testi ohjelma
     */
    public static void main(String[] args) {
        String rivi = "1|1|3|1";
        Tulkki lajit = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
        Tulkki vieheTulkki = new Tulkki(System.getProperty("user.dir") + "/../vieheTyypit.dat");
        Tulkki kokoTulkki = new Tulkki(System.getProperty("user.dir") + "/../koot.dat");
        Saalis s = new Saalis(rivi, lajit, vieheTulkki, kokoTulkki);
        System.out.println("------------Saaliin testi------------");
        System.out.println("tiedot annettu muodossa " + rivi);
        System.out.println(s.getKoko());
        System.out.println(s.getLaji());
        System.out.println(s.getReissunTagi());
        System.out.println(s.getSaaliinId());
        System.out.println(s.tiedotTallenusMuodossa(lajit, vieheTulkki, kokoTulkki));
    }
    public record Viehe(String tyyppi, String vari) {
    }
}
