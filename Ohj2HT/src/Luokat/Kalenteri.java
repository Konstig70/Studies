package Luokat;

import java.io.File;
import java.util.List;

/**
 * Pääluokka sisältää kaikki reissut ja samalla kaikki saaliit ja vieheet sekä laskurit
 */
public class Kalenteri {
    private Reissut reissut;
    private Saaliit saaliit;
    private Laskuri kalaLaskuri;
    private Laskuri vieheLaskuri;
    private Tulkki lajit;
    private Tulkki vieheTulkki;
    private Tulkki kokoTulkki;

    public Kalenteri() {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
        lajit = new Tulkki(dir + "/../lajit.dat");
        vieheTulkki = new Tulkki(dir + "/../vieheTyypit.dat");
        kokoTulkki = new Tulkki(dir + "/../koot.dat");

        this.saaliit = new Saaliit(10);
        TiedostonHiplailija t = new TiedostonHiplailija(System.getProperty("user.dir") + "/../saaliit.dat");
        for (String s : t.lueRivit()) {
            Saalis saaliis = new Saalis(s, lajit, vieheTulkki, kokoTulkki);
            saaliit.lisaa(saaliis);
        }
        this.reissut = new Reissut(saaliit);
        reissut.sort();
        vieheLaskuri = new Laskuri(dir +"/../vieheLaskuri.dat",vieheTulkki);
        kalaLaskuri = new Laskuri(dir +"/../kalaLaskuri.dat",lajit);
    }

    public Laskuri getKalaLaskuri() {
        return kalaLaskuri;
    }

    public Laskuri getVieheLaskuri() {
        return vieheLaskuri;
    }

    public Reissut getReissut() {
        return reissut;
    }

    public Tulkki getLajiTulkki() { return lajit; }

    public Tulkki getVieheTulkki() { return vieheTulkki; }

    public Tulkki getKokoTulkki() { return kokoTulkki;  }

    public Saaliit getSaaliit() { return saaliit;}

    public boolean tallenna(int aluksi, int reissumaara) {
        TiedostonHiplailija reissu = new TiedostonHiplailija(System.getProperty("user.dir") + "/../reissut.dat");
        TiedostonHiplailija saalis = new TiedostonHiplailija(System.getProperty("user.dir")+ "/../saaliit.dat");
        TiedostonHiplailija saalisLaskuri = new TiedostonHiplailija(System.getProperty("user.dir")+ "/../kalaLaskuri.dat");
        TiedostonHiplailija vieheet = new TiedostonHiplailija(System.getProperty("user.dir")+ "/../vieheLaskuri.dat");
        System.out.println(System.getProperty("user.dir"));
        this.lajit = new Tulkki(System.getProperty("user.dir") + "/../lajit.dat");
        this.vieheTulkki = new Tulkki(System.getProperty("user.dir") + "/../vieheTyypit.dat");
        this.kokoTulkki = new Tulkki(System.getProperty("user.dir") + "/../koot.dat");
        for (int i = aluksi; i < reissumaara; i++){
            System.out.println(reissut.getReissut().get(i));
            reissu.kirjoita(reissut.getReissut().get(i).toString());

            for (Saalis s : reissut.getReissut().get(i).getSaaliit()) {
                saalis.kirjoita(s.tiedotTallenusMuodossa(lajit,vieheTulkki,kokoTulkki));
            }
        }
        for (String s : kalaLaskuri.tallennusTiedot()){
            saalisLaskuri.kirjoita(s);
        }
        for (String s : vieheLaskuri.tallennusTiedot()){
            vieheet.kirjoita(s);
        }
        reissu.sulje();
        saalis.sulje();
        saalisLaskuri.sulje();
        vieheet.sulje();
        return true;
    }

    public boolean tallennaTietyt() {
        saaliit.tallenna();
        reissut.tallenna();
        kalaLaskuri.tallenna();
        vieheLaskuri.tallenna();
        return true;
    }

    public void paivitaTulkit() {
        String dir = System.getProperty("user.dir");
        lajit = new Tulkki(dir + "/../lajit.dat");
        vieheTulkki = new Tulkki(dir + "/../vieheTyypit.dat");
        kokoTulkki = new Tulkki(dir + "/../koot.dat");
        kalaLaskuri.setTulkki(lajit);
        vieheLaskuri.setTulkki(vieheTulkki);

    }

    public void lisaaReissu(Reissu r, List<Saalis> saaliit) {
        for(Saalis saalis : saaliit) {
            saalis.setReissunTagi(r.getTag());
        }
        System.out.println(r);
        for (Saalis s : r.getSaaliit()){
            paivitaTulkit();
            getKalaLaskuri().kasvata(getLajiTulkki().getIdFromValue(s.getLaji()));
            getVieheLaskuri().kasvata(getVieheTulkki().getIdFromValue(s.getViehe().tyyppi()));
            this.saaliit.lisaa(s);
        }
        getReissut().lisaaListaan(r);

    }
}
