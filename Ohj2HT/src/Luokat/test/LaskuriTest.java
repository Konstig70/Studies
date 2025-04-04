package Luokat.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Luokat.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2025.03.21 15:51:07 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class LaskuriTest {



  // Generated by ComTest BEGIN
  /** testKasvata48 */
  @Test
  public void testKasvata48() {    // Laskuri: 48
    Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat")); 
    l.kasvata(1); 
    assertEquals("From: Laskuri line: 51", 3, l.getLkm(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testVahenna67 */
  @Test
  public void testVahenna67() {    // Laskuri: 67
    Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat")); 
    l.vahenna(1); 
    assertEquals("From: Laskuri line: 70", 1, l.getLkm(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetSuurinId96 */
  @Test
  public void testGetSuurinId96() {    // Laskuri: 96
    Laskuri l = new Laskuri(new String[] {"1|2", "2|4", "3|1", "4|5"}, new Tulkki(System.getProperty("user.dir") + "/../lajit.dat")); 
    assertEquals("From: Laskuri line: 98", 4, l.getSuurinId()); 
  } // Generated by ComTest END
}