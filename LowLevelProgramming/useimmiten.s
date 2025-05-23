	.text
	.global useimmiten

silmukka:
	movq -8(%rbp), %rdi # siirreään osoitin takaisin rdi rekisteriin
	call fgetc # rdissä on jo stdin, sillä c funktion kutsu laittoi sen ensimmäiseksi parametriksi
	cmpl %eax, -16(%rbp) # tekee saman kun sub %eax, %rbp, ilman että tallennetaan. Jos tulos oli 0 RFLAGS nollabitti kääntyy 1
	jz suurin # jz (jump if zero) tutkii onko RFLAGS nollabitti 1, jos on niin palaamme. 
	movzbl %al, %ecx # lasketaan indeksi. Eli otetaan merkki eax-rekisteristä (mihin fgetc palautti sen ja lisätään siihen nollia niin kauan että siitä saadaan 32 bittinen, jotta siitä tulee validi indeksi (voisi olla 64 bittinen mutta ei tarvitse)) nollien lisäys tehdään, jotta on vain merkin verran ykkösiä. Ts muut RAX muunnokset eivät vaikuta (toisaalta en tiiä onko sillein, että jos jokin palauttaa asiaa vain esim just eax-rekisteriin, niin se ei nollaa loppuja RAX-rekisterin 32-bittiä. Toimi myös jos sijoitti pelkästään RAX, mutta tämä oli varmempi tapa ainakin omasta mielestä.
	addq $1, -2064(%rbp, %rcx, 8) # kasvatetaan "indeksin" eli muistin kohdalla rdx + rcx olevaa arvoa yhdellä
	jmp silmukka # hypätään takaisin silmukkaan

suurin:
	movq $0, %r8 # Käytetään "taulukon" indeksinä r8-rekisteriä
	movl $0, %r9d # laitetaan r9 rekisteriin eli ns "paluuarvoon" 0, mikä kuvaa tilannetta, että 0 tavua ensiintynyt tällä hetkellä eli siis jokin pieni arvo, jotta minkään tavun esiintymä olisi suurempi.
	movq $0, %rcx # rcx on suurimman merkin esiintymisen määrä
	silmu2:
		movq -2064(%rbp,%r8,8), %rax # sijoitetaan rax-rekisteriin arvo rbp - 2046 + r8 * 8 ts, otetaan r8:ksas alkio
		cmpq %rcx, %rax # verrataan onko alkio suurempi kuin suurin
		jg  vaihda_suurin # jos on niin mennään vaihda_suurin
		addq $1, %r8 # kasvatetaan r8
		cmpq $256, %r8 # jos r8 pienempi kuin 256 
		jl silmu2 # toistetaan silmukka
	retq #palataan
	
vaihda_suurin: 
	movq %rax, %rcx # vaihdetaan rax rekisterissä oleva arvo rcx. Ts rax rekisterin arvosta tulee suurin
	movl %r8d, %r9d # tallennetaan tieto tavusta eli merkistä mikä esiintyi eniten 
	jmp silmu2 # hypätään takaisin silmukkaan

nollaus:
	movq $0, %r8 #taulukon indeksinä r8
	silmu1:
		movq $0, -2064(%rbp,%r8,8) #laitetaan nolla "taulukon" indeksiin r8 eli muisti paikkaan %rbp - 2064 + r8 
		addq $1, %r8 # kasvatetaan indeksiä
		cmpq $256, %r8 # onko indeksi pienempi kuin 256
		jl silmu1 # jos on niin toistetaan silmukka
		retq # palataan

useimmiten:
	#Pinokehyksen alustaminen
	pushq %rbp #Tallentaa edellisen pinon pohjan osoitteen
	movq %rsp, %rbp #Päällinen uudeksi pohjaksi
	subq $2064, %rsp #Varataan tarvittava tila
	movq %rdi, -8(%rbp) # ensimmäinen parametri talteen, jotta arvo pysyy tallessa, vaikka rdi muuttuisi
	movq %rsi, -16(%rbp) # toinen parametri talteen samat perusteet tässä
	call nollaus #nollataan "taulukko"
	call silmukka #kutsutaan silmukkaa eli funktiota joka hoitaa itse merkkien laskemisen
	movl %r9d, %eax #laitetaan eax-rekisteriin paluuarvo
	leaveq #purkaa kehyksen
	ret


