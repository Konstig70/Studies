package Luokat.test;
// Generated by ComTest BEGIN
import java.io.IOException;
import fi.jyu.mit.ohj2.VertaaTiedosto;
import static org.junit.Assert.*;
import org.junit.*;
import Luokat.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2025.03.26 13:40:59 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class TiedostonHiplailijaTest {



  // Generated by ComTest BEGIN
  /** 
   * testKirjoita41 
   * @throws IOException when error
   */
  @Test
  public void testKirjoita41() throws IOException {    // TiedostonHiplailija: 41
    String tulos = "testataan kirjoitusta"; 
    VertaaTiedosto.tuhoaTiedosto("tulos.txt"); 
    TiedostonHiplailija t = new TiedostonHiplailija("tulos.txt"); 
    t.kirjoita(tulos); 
    assertEquals("From: TiedostonHiplailija line: 49", null, VertaaTiedosto.vertaaFileString("tulos.txt",tulos)); 
    VertaaTiedosto.tuhoaTiedosto("tulos.txt"); 
    VertaaTiedosto.tuhoaTiedosto("hiljaa.txt"); 
  } // Generated by ComTest END
}