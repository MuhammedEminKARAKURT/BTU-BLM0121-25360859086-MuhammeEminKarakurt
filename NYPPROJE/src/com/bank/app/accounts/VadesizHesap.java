package com.bank.app.accounts;

import com.bank.app.cards.KrediKarti;

public class VadesizHesap extends BankaHesabi{
	
	private String hesapTuru;
	
	public VadesizHesap(double bakiye){
		//Üst sınıf olan BankaHesabi'nin kurucusuna bakiye bilgisini gönderiyoruz.
		super(bakiye);
		this.setHesapTuru("Vadesiz");
	}
	
	
	public void paraTransferi(BankaHesabi aliciHesap,BankaHesabi gonderenHesap,double miktar) {
		if(miktar>gonderenHesap.getBakiye()) {
			System.out.println("Hata!Yetersiz bakiye");
		}
		else {
			gonderenHesap.setBakiye(gonderenHesap.getBakiye() - miktar);
			aliciHesap.setBakiye(aliciHesap.getBakiye() + miktar);
		}
	}
	//Borc işlemlerini yapalım.
	public void krediKartiBorcOdeme(KrediKarti kart,double miktar) {
		if(this.getBakiye()>=miktar) {
			double yeniBakiye=this.getBakiye() - miktar;
			this.setBakiye(yeniBakiye);
			
			double yeniBorc=kart.getGuncelBorc() - miktar;
			kart.setGuncelBorc(yeniBorc);
			double yeniLimit=kart.getKullanilabilirLimit() + miktar;
			kart.setKullanilabilirLimit(yeniLimit);
		}
	}


	public String getHesapTuru() {
		return hesapTuru;
	}


	public void setHesapTuru(String hesapTuru) {
		this.hesapTuru = hesapTuru;
	}

}
