package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

	@Test
	public void kortinSaldoAlussaOikein() {
		assertEquals(10, kortti.saldo());
	}

	@Test
	public void rahanLataaminenKasvattaaSaldoa() {
		kortti.lataaRahaa(1);
		
		assertEquals(11, kortti.saldo());
	}

	@Test
	public void saldoVaheneeKunRahaaTarpeeksi() {
		kortti.otaRahaa(1);
		
		assertEquals(9, kortti.saldo());
	}

	@Test
	public void saldoEiMuutuKunRahaaEiRiittavasti() {
		kortti.otaRahaa(11);

		assertEquals(10, kortti.saldo());
	}

	@Test
	public void otaRahaaPalauttaaOikeanArvon() {
		assertTrue(kortti.otaRahaa(1));
		assertFalse(kortti.otaRahaa(10));
	}
}
