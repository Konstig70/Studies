package Luokat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class kartanPiirtaja {

    public static Integer[] haeKoordinaatti(String answer) {
        try{
            if (answer.equals("ei")) {
                return new Integer[0];
            }
            System.out.println(answer);
            String paikka = answer.toLowerCase().replace(' ', '+').replace('ä','a').replace('ö','o');
            System.out.println(paikka);
            //Valmistellaan paikka poistamalla skandit ja laittamalla se pieniin kirjaimiin
            String urlJono = "https://nominatim.openstreetmap.org/search?q=" +paikka  +"+Suomi" +"&format=json";
            //Lisätään vielä suomi jokaiseen api kutsuun, jotta vältytään tilanteelta, missä saadaan kaksi eri maasta olevaa paikkaa
            URI uri = new URI(urlJono);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            //Luetaan palautettu viesti
            System.out.println(content);
            String[] split = content.toString().split(",");
            String[] coords = new String[2];
            for(String s : split) {
                if(s.contains("lat")){
                    System.out.println(s);
                    coords[0] = s;
                }
                if(s.contains("lon")){
                    System.out.println(s);
                    coords[1] = s;

                }
                //haetaan koordinaatit viestistä, "," merkki jakoi jonon parhaiten
            }
            String[] lat = coords[0].split(":");
            String[] lon = coords[1].split(":");
            //Koordinaatissa oli vielä lon:62 joten täytyy saada vain numero
            double lng = Double.parseDouble(lon[1].replace("\"",""));
            double la = Double.parseDouble(lat[1].replace("\"",""));
            double tunlat = 61;
            double tunlon = 25.7;
            double liikex = 32.15;
            double liikey = 71.45;
            //Yllä tunnetut koordinaatit sekä yhden lat/lon yksikön liike kartalla
            int x = (int) (300 + (lng - tunlon) * liikex);
            int y = (int) (670 - (la - tunlat) * liikey);
            //300 ja 670 olivat tunnetun koordinaattien pikseli kartalla
            return new Integer[] {x,y};
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new Integer[0];
    }



    public static boolean piirraKuvaan(int x, int y) {
        try{
            File file = new File(System.getProperty("user.dir") +"/src/media/UusiKarttakopio.png");
            BufferedImage kartta = ImageIO.read(file);
            Graphics2D sivellin = kartta.createGraphics();
            sivellin.setColor(Color.BLACK);
            sivellin.fillOval(x - 10, y - 10, 20, 20);
            sivellin.dispose();
            //Piirretään kuvaan koordinaatteihin piste, -10, jotta säde saadaan oikein
            File uusi = new File(System.getProperty("user.dir")+"/src/media/UusiKarttakopio.png");
            if(uusi.exists()){
                System.out.println("tiedosto luotu");
                ImageIO.write(kartta, "png", uusi);
                return true;
            }
            //tallennetaan uusi versio kuvasta vanhan päälle ja asetetaan kuva takaisin (toisaalta javafx ei taida lukea kuin kerran kuvan joten ei ehkä tarvitsisi tehdä)
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean piirraKaikki(List<Integer[]> koordinaatit) {
        try{
            File file = new File(System.getProperty("user.dir") +"/src/media/UusiKarttakopio.png");
            BufferedImage kartta = ImageIO.read(file);
            Graphics2D sivellin = kartta.createGraphics();
            sivellin.setColor(Color.BLACK);
            for (Integer[] pisteet : koordinaatit) {
                sivellin.fillOval(pisteet[0] - 10, pisteet[1] - 10, 20, 20);
            }
            sivellin.dispose();
            //Piirretään kuvaan koordinaatteihin piste, -10, jotta säde saadaan oikein
            File uusi = new File(System.getProperty("user.dir")+"/src/media/UusiKarttakopio.png");
            if(uusi.exists()){
                System.out.println("tiedosto luotu");
                ImageIO.write(kartta, "png", uusi);
                return true;
            }
            //tallennetaan uusi versio kuvasta vanhan päälle ja asetetaan kuva takaisin (toisaalta javafx ei taida lukea kuin kerran kuvan joten ei ehkä tarvitsisi tehdä)
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
