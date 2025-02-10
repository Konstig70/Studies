package Demot;
import java.util.Random;

public class Pikalaji {
    public static void Lajitele(int[] t, int ala, int yla){
        if(yla - ala < 4){
           Kuplalaji.Lajittele(t,yla+1,ala);
        }
        else{
            int k = jakotietue(t, yla, ala);
            if(k==-1){System.out.println("error");}
            int j = ositus(t, k, yla , ala);
            Lajitele(t, ala, j-1);
            Lajitele(t,j+1, yla);
        }
    }

    private static int ositus(int[] t, int k, int yla, int ala){
        int h = t[ala];
        t[ala] = t[k];
        t[k] = h;
        int i = ala;
        int j =  yla + 1;
        while(true){
            do {
                i++;
            }
            while(i < yla+1 && t[i] < t[ala]);
            do {
                j--;
            }
            while(t[j] > t[ala]);
            if(j < i){break;}
            h = t[i];
            t[i] = t[j];
            t[j] = h;
        }
        h = t[j];
        t[j] = t[ala];
        t[ala] = h;
        return j;
    }

    private static int jakotietue(int[] t, int yla, int ala){

        int eka = t[ala];
        int keskimmainen = t[(yla + ala)/2];
        int vika = t[yla];
         if(eka > keskimmainen || eka > vika){
            if(eka < keskimmainen || eka < vika){
                return ala;
            }
        }
        if(keskimmainen > eka || keskimmainen > vika){
            if(keskimmainen < vika || keskimmainen < eka){
                return (yla + ala)/2;
            }
        }
        if (vika >= keskimmainen || vika > eka){
            if(vika <= keskimmainen || vika < eka){
                return yla;
            }
        }
        return (yla + ala)/2;
    }
}
