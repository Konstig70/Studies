using Jypeli;
using Jypeli.Assets;
using Jypeli.Widgets;
using PhysicsObject = Jypeli.PhysicsObject;
using Vector = Jypeli.Vector;
namespace Flappy_bird;

/// @author Konsta Lahtinen
/// @version 23.10.2023
/// <summary>
/// "Flappy Bird" tyylinen peli, missä liikutetaan pelaajaa esteen läpi
/// </summary>
public class FlappyBirdKlooni : PhysicsGame
{
    private PhysicsObject _pelaaja;
    private PhysicsObject _oikeaReuna;
    private PhysicsObject _alareuna;
    private IntMeter _pistelaskuri;
    private int _i;
    private int _aukko = 300;
    private EasyHighScore _parhaatLista = new EasyHighScore();
    private bool _havio;
    private bool _mista;
    private Image[] _taustakuvat = {LoadImage("taustaAurinko.png"), LoadImage("taustaKaupunki.png"), LoadImage("taustaYo.png")};
    
    
    /// <summary>
    /// Suoritetaan pelin aloittaessa
    /// </summary>
    public override void Begin()
    {
        LuoTaso();
        LuoEsteet();
        LuoLaskurit();
        AddCustomHandler(Arvo, VaikeammatEsteet);
        LisaaPelaaja();
        AlkuValikko();
        PelaajaAnimaatio();
        Timer.CreateAndStart(1, PelaajaAnimaatio);
        PhoneBackButton.Listen(ConfirmExit, "Lopeta peli");
        Keyboard.Listen(Key.Escape, ButtonState.Pressed, ConfirmExit, "Lopeta peli");
    }


    /// <summary>
    /// Pelaajan animaatio ennen kuin peli alkaa
    /// </summary>
    private void PelaajaAnimaatio()
    {
        Vector loppu = _pelaaja.Mass * new Vector(100, 400);
        _pelaaja.Velocity = new Vector(_pelaaja.Velocity.X, loppu.Y);
    }
    
    
    /// <summary>
    /// Luo pelin tason
    /// </summary>
    private void LuoTaso()
    {
        Level.Background.Image = _taustakuvat[0];
        if (_i != 0)
        {
            Level.Background.Image = _taustakuvat[RandomGen.NextInt(0, 3)];
        }
        Level.Background.FitToLevel();
        Level.CreateTopBorder();
        _alareuna = Level.CreateBottomBorder();
        Level.CreateLeftBorder();
        _oikeaReuna = Level.CreateRightBorder();
    }
    
    
    /// <summary>
    /// lisaa pelaajan peliin
    /// </summary>
    private void LisaaPelaaja()
    {
        int oikealle = 100;
        int ylos = 500;
        _pelaaja = new PhysicsObject(2 * 20, 2 * 20, Shape.Circle);
        _pelaaja.X = Level.Left + oikealle;
        _pelaaja.Y = Level.Bottom + ylos;
        _pelaaja.Restitution = 0;
        _pelaaja.Image = LoadImage("pelaaja.png");
        Add(_pelaaja);
        Gravity = new Vector(0.0, -981);
        _pelaaja.Tag = _pelaaja;
        Keyboard.Listen(Key.W, ButtonState.Pressed, Lyopelaajaa, "Liikuta pelaajaa ylös", new Vector(100, 400));
        Keyboard.Listen(Key.Up, ButtonState.Pressed, Lyopelaajaa, "Liikuta pelaajaa ylös", new Vector(100, 400));
        AddCollisionHandler(_pelaaja, "este", LopetaPeli);
        AddCollisionHandler(_pelaaja,PelaajaOsui);
    }
    
    
    /// <summary>
    /// katsoo, että onko pistelaskurin arvo 5
    /// </summary>
    /// <returns></returns>
    private bool Arvo()
    {
        return _i == 5;
    }
    
    
    /// <summary>
    /// Tekee esteistä pienempiä
    /// </summary>
    private void VaikeammatEsteet()
    {
        _aukko = 250;
    }
    
    
    /// <summary>
    /// liikuttaa pelaajaa ylös
    /// </summary>
    /// <param name="suunta"></param>
    private void Lyopelaajaa(Vector suunta)
    {
        Vector loppu = _pelaaja.Mass * suunta;
        _pelaaja.Velocity = new Vector(_pelaaja.Velocity.X, loppu.Y);
    }
    
    
    /// <summary>
    /// Ohjelma, joka suoritetaan kun pelaaja törmää
    /// </summary>
    /// <param name="pelaaja"></param>
    /// <param name="kohde"></param>
    private void PelaajaOsui(PhysicsObject pelaaja, PhysicsObject kohde)
    {
        if (kohde == _oikeaReuna)
        {
            _i++;
            UusiKentta();
            LisaaPelaaja();
            _pelaaja.Hit(new Vector(200, 0));
        }
        if (kohde == _alareuna) LopetaPeli(pelaaja, kohde);
    }
    
    
    /// <summary>
    /// Luo pistelaskurin peliin
    /// </summary>
    private void LuoLaskurit()
    {
        int oikealle = 100;
        int alas = 50;
        _pistelaskuri = new IntMeter(_i);
        _pistelaskuri.MaxValue = int.MaxValue;
        _pistelaskuri.UpperLimit += LoppuSaavutettu;
        Label pistenaytto = new Label(); 
        pistenaytto.X = Screen.Left + oikealle;
        pistenaytto.Y = Screen.Top - alas;
        pistenaytto.TextColor = Color.Black;
        pistenaytto.Color = Color.White;
        pistenaytto.Title = "pisteet: ";
        pistenaytto.BindTo(_pistelaskuri);
        Add(pistenaytto);
    }
    
    
    /// <summary>
    /// Luo peliin esteet
    /// </summary>
    private void LuoEsteet()
    {
        int esteidenpituus = 1000;
        int etaisyys = 300;
        for (int i = -200; i < esteidenpituus; i += 200)
        {
            int mis = RandomGen.NextInt(200, 500);
            PiirraNelio(i, mis + etaisyys, LoadImage("Olut2.jpg"));
            PiirraNelio(i, mis - etaisyys*2 - _aukko, LoadImage("Olut.jpg"));
        }
    }


    /// <summary>
    /// Piirtää esteet
    /// </summary>
    /// <param name="i">Neliön x-koordinaatti</param>
    /// <param name="c">Neliön y-koordinaatti</param>
    /// <param name="kuva">Esteen kuva</param>
    private void PiirraNelio(int i, double c, Image kuva)
    {
        PhysicsObject este = new PhysicsObject(40, 1000, Shape.Rectangle);
        este.Y = c;
        este.X = i;
        este.MakeStatic();
        este.Image = kuva;
        este.Tag = "este";
        Add(este);
    }


    /// <summary>
    /// Luo peliin alkuvalikon
    /// </summary>
    private void AlkuValikko()
    {
        MultiSelectWindow alkuvalikko = new MultiSelectWindow("Flappy Bird", "Aloita", "Parhaat pisteet", "Lopeta");
        alkuvalikko.SetButtonColor(Color.Black);
        Add(alkuvalikko);
        _mista = false;
        alkuvalikko.AddItemHandler(0,Aloitapeli);
        alkuvalikko.AddItemHandler(1,ParhaatPisteet);
        alkuvalikko.AddItemHandler(2,ConfirmExit);
    }
    
    
    /// <summary>
    /// Lopettaa pelin jos pisteitä on saatu tarpeeksi
    /// </summary>
    private void LoppuSaavutettu()
    {
        Remove(_pelaaja);
        LoppuValikko("Voitit pelin!");
    }
    
    
    /// <summary>
    /// Aloittaa pelin
    /// </summary>
    private void Aloitapeli()
    {
        ClearTimers();
        _i = 0;
        if (_havio)
        {
            UusiKentta();
            LisaaPelaaja();
        }
        _pelaaja.Hit(new Vector(200, 0));
    }

    
    /// <summary>
    /// Lopettaa pelin
    /// </summary>
    /// <param name="pelaaja">Mikä osuu</param>
    /// <param name="este">Mihin osuu</param>
    private void LopetaPeli(PhysicsObject pelaaja, PhysicsObject este)
    {
        Explosion rajahdys = new Explosion(50);
        rajahdys.Position = pelaaja.Position;
        Add(rajahdys);
        Remove(pelaaja);
        _havio = true;
        _mista = true;
        _parhaatLista.EnterAndShow(_pistelaskuri.Value);
        _parhaatLista.HighScoreWindow.Closed += Takaisin;
        
    }


    /// <summary>
    /// Pistelistalta palaaminen valikkoon
    /// </summary>
    /// <param name="sender"></param>
    private void Takaisin(Window sender)
    {
        if (_mista)
        {
            LoppuValikko("Hävisit!");
        }
        else AlkuValikko();
    }
    
    
    /// <summary>
    /// Luo peliin loppuvalikon halutulla tekstillä
    /// </summary>
    private void LoppuValikko(string teksti)
    {
        MultiSelectWindow loppuvalikko = new MultiSelectWindow(teksti, "Aloita", "Parhaat pisteet", "Lopeta");
        Add(loppuvalikko);
        loppuvalikko.AddItemHandler(0,AloitaAlusta);
        loppuvalikko.AddItemHandler(1,ParhaatPisteet);
        loppuvalikko.AddItemHandler(2,ConfirmExit);
    }
    
    
    /// <summary>
    /// Pistelistan näyttäminen
    /// </summary>
    private void ParhaatPisteet()
    {
        _parhaatLista.Show();
        _parhaatLista.HighScoreWindow.Closed += Takaisin;
    }
    
    
    /// <summary>
    /// Aloittaa pelin alusta
    /// </summary>
    private void AloitaAlusta()
    {
        ClearGameObjects();
        _aukko = 300;
        Aloitapeli();
    }
    
    
    /// <summary>
    /// Luo uuden kentän
    /// </summary>
    private void UusiKentta()
    {
        ClearGameObjects();
        LuoTaso();
        LuoEsteet();
        LuoLaskurit();
    }
}