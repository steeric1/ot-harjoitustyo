package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

	Kassapaate paate;

	@Before
	public void setUp() {
    	paate = new Kassapaate();
	}

	@Test
	public void alussaRahanJaLounaidenMaaraOikein() {
		assertEquals(100000, paate.kassassaRahaa());
		assertEquals(0, paate.maukkaitaLounaitaMyyty());
		assertEquals(0, paate.edullisiaLounaitaMyyty());
	}

	@Test
	public void kateisostoToimiiEdullisellaLounaallaKunMaksuRiittava() {
		int takaisin = paate.syoEdullisesti(250);
		
		assertEquals(100240, paate.kassassaRahaa());
		assertEquals(10, takaisin);
		assertEquals(1, paate.edullisiaLounaitaMyyty());
	}

	@Test
	public void kateisostoToimiiEdullisellaLounaallaKunMaksuEiRiittava() {
		int takaisin = paate.syoEdullisesti(230);
		
		assertEquals(100000, paate.kassassaRahaa());
		assertEquals(230, takaisin);
		assertEquals(0, paate.edullisiaLounaitaMyyty());
	}

	@Test
	public void kateisostoToimiiMaukkaallaLounaallaKunMaksuRiittava() {
		int takaisin = paate.syoMaukkaasti(410);
		
		assertEquals(100400, paate.kassassaRahaa());
		assertEquals(10, takaisin);
		assertEquals(1, paate.maukkaitaLounaitaMyyty());
	}

	@Test
	public void kateisostoToimiiMaukkaallaLounaallaKunMaksuEiRiittava() {
		int takaisin = paate.syoMaukkaasti(390);
		
		assertEquals(100000, paate.kassassaRahaa());
		assertEquals(390, takaisin);
		assertEquals(0, paate.maukkaitaLounaitaMyyty());
	}

	@Test
	public void korttiostoToimiiEdullisellaLounaallaKunKortillaRiittavastiRahaa() {
		Maksukortti k = new Maksukortti(250);

		assertTrue(paate.syoEdullisesti(k));
		assertEquals(10, k.saldo());
		assertEquals(1, paate.edullisiaLounaitaMyyty());
		assertEquals(100000, paate.kassassaRahaa());
	}

	@Test
	public void korttiostoToimiiEdullisellaLounaallaKunKortillaEiRiittavastiRahaa() {
		Maksukortti k = new Maksukortti(230);
		
		assertFalse(paate.syoEdullisesti(k));
		assertEquals(230, k.saldo());
		assertEquals(0, paate.edullisiaLounaitaMyyty());	
	}

	@Test
    public void korttiostoToimiiMaukkaallaLounaallaKunKortillaRiittavastiRahaa() {
        Maksukortti k = new Maksukortti(410);

        assertTrue(paate.syoMaukkaasti(k));
        assertEquals(10, k.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
		assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void korttiostoToimiiMaukkaallaLounaallaKunKortillaEiRiittavastiRahaa() {
        Maksukortti k = new Maksukortti(390);

        assertFalse(paate.syoMaukkaasti(k));
        assertEquals(390, k.saldo());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

	@Test
	public void kortilleRahaaLadattaessaKortinSaldoMuuttuu() {
		Maksukortti k = new Maksukortti(0);
		paate.lataaRahaaKortille(k, 10);

		assertEquals(10, k.saldo());
	}

	@Test
	public void kortilleRahaaLadattaessaKassanRahamaaraMuuttuuOikein() {
		Maksukortti k = new Maksukortti(0);
		paate.lataaRahaaKortille(k, 10);
	
		assertEquals(100010, paate.kassassaRahaa());
	}

	@Test
	public void ladattaessaNegatiivisenSummanKortilleMitaanEiTapahdu() {
		Maksukortti k = new Maksukortti(10);
		paate.lataaRahaaKortille(k, -10);
		
		assertEquals(10, k.saldo());
		assertEquals(100000, paate.kassassaRahaa());
	}

}
