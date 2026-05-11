package com.bank.app.accounts;

import java.util.Random;

public class BankaHesabi {
	
	private String iban;
	private double bakiye;
	
	public BankaHesabi(double bakiye){
		this.bakiye=bakiye;
		
		this.iban=ibanUret();
	}
	//Rastgele iban üretmek için metod kuralım.
	private String ibanUret() {
		String yeniIban="TR";
		Random r=new Random();
		for(int i=0;i<24;i++) {
			yeniIban +=r.nextInt(10);
		}
		return yeniIban;
	}
	
	public String toString() {
		return iban+"İbanlı müşterinin bakiyesi: "+bakiye;
	}
	
	public double getBakiye() {
		return bakiye;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setBakiye(double bakiye) {
		this.bakiye=bakiye;
	}
	
	

}
