package com.bank.app.service;

import com.bank.app.people.Musteri;
import com.bank.app.accounts.BankaHesabi;
import com.bank.app.accounts.VadesizHesap;
import com.bank.app.cards.KrediKarti;


public class BankaService {
	
	// Müşteri için yeni hesap açma  mantığı 
    public void hesapAcmaServisi(Musteri musteri, String hesapTuru) {
        musteri.hesapEkle(hesapTuru);
    }

    // Vadesiz hesaptan transfer yapma mantığı 
    public void transferServisi(VadesizHesap gonderen, BankaHesabi alici, double miktar) {
        gonderen.paraTransferi(alici, gonderen, miktar);
    }

    // Kredi kartı borcu ödeme  mantığı 
    public void borcOdemeServisi(VadesizHesap hesap, KrediKarti kart, double miktar) {
        hesap.krediKartiBorcOdeme(kart, miktar);
    }

}
