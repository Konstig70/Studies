package fxHeatMapTesti;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author konst
 * @version 14.2.2025
 */
public class HeatMapTestiGUIController implements Initializable {
    @FXML
    private ImageView kuva;

    @FXML
    private Button nappi;

    @FXML
    private void handleLisaa(ActionEvent event) {
        lisaaKoordinaatti(event);
    }

    @FXML private void handleNollaa(){
        nollaaKartta();
    }

    private void nollaaKartta() {
        File kopio = new File(System.getProperty("user.dir")+"/src/media/Kartta_backup.png");
        File vanha = new File(System.getProperty("user.dir")+"/src/media/UusiKarttakopio.png");
        try {
            Files.copy(kopio.toPath(), vanha.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Dialogs.showMessageDialog("Kartan nollaus onnistui!");

        } catch (IOException e) {
            Dialogs.showMessageDialog("Virhe nollauksessa: " + e.getMessage());
        }
    }

    private void lisaaKoordinaatti(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Vastaa");
        dialog.setContentText("Anna paikan nimi mahdollisimman tarkasti:");
        Optional<String> answer = dialog.showAndWait();
        System.out.println(answer.orElse("Ei ollut vastausta"));
        haeKoordinaatti(answer.orElse("ei"));
    }

    private void haeKoordinaatti(String answer) {
        try{
            if (answer.equals("ei")) {
                return;
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
                //haetaan koordinaatit viestistä "," merkki jakoi jonon parhaiten
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
            if(piirraKuvaan(x,y)){
                Dialogs.showMessageDialog("Kuva päivitettiin käynnistä sovellus uudestaan, jotta näet päivitykset");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }



    private boolean piirraKuvaan(int x, int y) {
        try{
            kuva.setImage(null);
            //Poistetaan kuva käytöst hetkeksi, jotta voimme piirtää siihen
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
                Image i = new Image("/media/UusiKarttakopio.png");
                kuva.setImage(i);
                return true;
            }
            //tallennetaan uusi versio kuvasta vanhan päälle ja asetetaan kuva takaisin (toisaalta javafx ei taida lukea kuin kerran kuvan joten ei ehkä tarvitsisi tehdä)
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image kuva = new Image("/media/UusiKarttakopio.png");
        this.kuva.setImage(kuva);
        //Asetetaan kuva
    }
}