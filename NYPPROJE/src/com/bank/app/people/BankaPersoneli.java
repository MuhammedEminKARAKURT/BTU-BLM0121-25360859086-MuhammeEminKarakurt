package com.bank.app.people;

import java.util.ArrayList;
import java.util.Random;

import com.bank.app.accounts.BankaHesabi;

public class BankaPersoneli extends Kisi {

	
	private String personelID;
	// Müşteri nesnelerini saklayacak ArrayList oluşturalım.
	private ArrayList<Musteri> musteriler=new ArrayList<>();
	
	
	public BankaPersoneli(String ad, String soyad, String email, int telefonNumarasi) {
		super(ad,soyad,email,telefonNumarasi);
		this.musteriler=new ArrayList<>();
		this.personelID = "P" + (new Random().nextInt(9000) + 1000);
	}
	
	public String toString() {
		return "Banka Personeli: " + getAd() + " " + getSoyad() + 
	           "\nPersonel ID: " + personelID;
	}

	public ArrayList<Musteri> getMusteriler() { 
        return musteriler;
    }
	
	
	
	
}
