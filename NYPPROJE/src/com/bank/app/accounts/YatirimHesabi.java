package com.bank.app.accounts;

public class YatirimHesabi extends BankaHesabi {
	
	private String hesapTuru;
	
	public YatirimHesabi(double bakiye) {
		super(bakiye);
		this.hesapTuru="Yatirim";
	}
	
	public void paraEkle(double miktar) {
		
		double yeniBakiye=super.getBakiye() + miktar;
		super.setBakiye(yeniBakiye);
	}
	
	public void paraCek(double miktar) {
		if(miktar>0 &&miktar<=super.getBakiye()) {
			super.setBakiye(super.getBakiye()-miktar);
			System.out.println(miktar + "TL çekildi.");
		}
		else {
			System.out.println("Yetersiz bakiye veya geçersiz miktar girildi");
		}
	}
	
	public String toString() {
		return "Hesap Türü: "+hesapTuru+",Güncel bakiye: "+super.getBakiye()+"TL"; 
	}
	
	
	

}
