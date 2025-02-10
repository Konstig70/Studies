package Demot;

import java.util.ArrayList;
import java.util.List;

public class Solmu {
    private int key;
    private Solmu left;
    private Solmu right;

    public Solmu(int k, Solmu l, Solmu r) {
        key = k;
        left = l;
        right = r;
    }

    public Solmu(int k) {
        key = k;
    }

    public Solmu getLeft() {
        return left;
    }

    public Solmu getRight() {
        return right;
    }

    void setLeft(Solmu l) {
        left = l;
    }


    void setRight(Solmu r) {
        right = r;
    }

    public int getKey() {
        return key;
    }


    public static void main(String[] args) {
        List<Solmu> binaaripuu = new ArrayList<Solmu>();
        Solmu juuri = new Solmu(25);
        binaaripuu.add(juuri);
        ///Tämän jälkeen oltaisiin voitu lisätä aina uusi solmu lisäämällä juuri.setLeft(new Solmu(10)) tms,
        ///mutta näin käsittely tulee vaikeaksi, kun sitten seuraavan lisääminen olisi juuris.getLeft.setLeft(new Solmu(9))
        ///sama jatkuisi ja lopuksi lisäykset olisivat jotain juuri.getLeft.getLeft.getRight.setLeft(new Solmu 5)
        ///joka olisi mielestäni liian monimutkaisesti tehty
        Solmu s1 = new Solmu(15);
        juuri.setLeft(s1);
        binaaripuu.add(s1);
        Solmu s2 = new Solmu(27);
        juuri.setRight(s2);
        binaaripuu.add(s2);
        Solmu s3 = new Solmu(20);
        s1.setRight(s3);
        binaaripuu.add(s3);
        Solmu s4 = new Solmu(12);
        s1.setLeft(s4);
        binaaripuu.add(s4);
        Solmu s5 = new Solmu(13);
        s4.setRight(s5);
        binaaripuu.add(s5);
        Solmu s6 = new Solmu(18);
        s3.setLeft(s6);
        binaaripuu.add(s6);
        Solmu s7 = new Solmu(21);
        s3.setRight(s7);
        binaaripuu.add(s7);
        Solmu s8 = new Solmu(30);
        s2.setRight(s8);
        binaaripuu.add(s8);
        Solmu s10 = new Solmu(28);
        s8.setLeft(s10);
        binaaripuu.add(s10);
        System.out.println("luotiin seuraava binääripuu :");
        for(Solmu s: binaaripuu) {
            System.out.print(s.getKey() + " ");
        }
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Binaaripuu esijärjestyksessä :");
        Esijarjestys(juuri);
        System.out.println(" ");
        System.out.println("Binaaripuu sisäjärjestyksessä :");
        Sisajarjestys(juuri);
        System.out.println(" ");
        System.out.println("Binääripuu Jälkijärjestyksessä");
        JalkiJarjestys(juuri);
    }

    public static void Esijarjestys(Solmu solmu) {
        System.out.print(solmu.getKey() + " ");
        if(solmu.getLeft() != null) {
            Esijarjestys(solmu.getLeft());
        }
        if(solmu.getRight() != null) {
            Esijarjestys(solmu.getRight());
        }
    }

    public static void Sisajarjestys(Solmu solmu) {
        if(solmu.getLeft() != null) {
            Sisajarjestys(solmu.getLeft());
        }
        System.out.print(solmu.getKey() + " ");
        if(solmu.getRight() != null) {
            Sisajarjestys(solmu.getRight());
        }

    }

    public static void JalkiJarjestys(Solmu solmu) {
        if(solmu.getLeft() != null) {
            JalkiJarjestys(solmu.getLeft());
        }
        if(solmu.getRight() != null) {
            JalkiJarjestys(solmu.getRight());
        }
        System.out.print(solmu.getKey() + " ");
        return;
    }
}
