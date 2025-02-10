package Demot;

public class Kuplalaji {
    public static int[] Lajittele(int[] t,int yla, int ala){
        int h = 0;
        boolean muutokset;
        while (true){
            muutokset = false;
            for(int i = ala; i <= yla; i++){
                if(i+1 < yla){
                    if(t[i] > t[i+1]){
                        h = t[i];
                        t[i] = t[i+1];
                        t[i+1] = h;
                        muutokset = true;
                    }
                }

            }
            if(!muutokset){break;}
        }
        return t;
    }
}
