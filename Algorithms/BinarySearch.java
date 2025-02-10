package Demot;

import java.util.Arrays;
import java.util.Random;

public class BinarySearch {
    ///Binäärihaku toteutus, palauttaa kuinka monta vertailua tehtiin
    /// jos lukua ei löydy palauttaa -1
    public static int search(int[] nums, int key) {
        int low = 0;
        int high = nums.length - 1;
        int comparisons = 0;
        while (low <= high){
            int middle = (low + high) / 2;
            comparisons++;
            if (nums[middle] == key){

                return comparisons;

            }
            if (nums[middle] < key){
                low = middle + 1;
                comparisons++;
            }
            if (nums[middle] > key){
                high = middle - 1;
                comparisons++;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] luvut = new int[10];
        Random random = new Random();
        TaulukonTaytto(luvut, random);
        int [] luvut2 = new int[10];
        TaulukonTaytto(luvut2, random);
        int [] luvut3 = new int[100];
        TaulukonTaytto(luvut3, random);

    }

    public static void TaulukonTaytto(int[] luvut, Random random) {
        int randomInt = random.nextInt(0,50);
        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = randomInt;
            randomInt = random.nextInt(0,50);
        }
        Arrays.sort(luvut);
        Tulostuts(luvut, random);
    }

    ///Tulostus aliohjelmaksi, jotta ei tarvitse toistaa
    public static void Tulostuts(int [] luvut, Random random) {
        if (luvut.length <= 20){
            System.out.println("Luotiin seuraava taulukko");
            System.out.print(" " + Arrays.toString(luvut));
            System.out.print(" ");
        }
        else {
            System.out.println("Luotiin " + luvut.length + ". alkion kokoinen taulukko");
        }
        int etistty = luvut[random.nextInt(0,luvut.length-1)];
        System.out.println("josta etsitään lukua " + etistty);
        int vertailu = search(luvut, etistty);
        if (vertailu == -1){
            System.out.println("Lukua ei löydetty ");
            return;
        }
        System.out.println("luku löydettiin " + vertailu + ". vertailu operaation jälkeen");
    }
}

