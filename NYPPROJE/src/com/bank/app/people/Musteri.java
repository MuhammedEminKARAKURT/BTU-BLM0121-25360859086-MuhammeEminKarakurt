package com.bank.app.people;
import java.util.ArrayList;
import java.util.Random;

import com.bank.app.accounts.BankaHesabi;
import com.bank.app.accounts.VadesizHesap;
import com.bank.app.accounts.YatirimHesabi;
import com.bank.app.cards.KrediKarti;

public class Musteri extends Kisi {

	private String musteriNumarasi;
	private ArrayList<BankaHesabi> hesaplar=new ArrayList<>();
	
	ArrayList<KrediKarti> krediKartlari=new ArrayList<>();
	
	public Musteri(String ad, String soyad, String email, int telefonNumarasi){
		super(ad, soyad, email, telefonNumarasi);
		this.hesaplar=new ArrayList<>();
		this.krediKartlari=new ArrayList<>();
		
		Random r=new Random();
		this.musteriNumarasi = String.valueOf(new Random().nextInt(900000) + 100000);
	}
	
	public ArrayList<BankaHesabi> getHesaplar() {
		return hesaplar;
	}
	
	public ArrayList<KrediKarti> getKrediKartlari(){
		return krediKartlari;
	}
	
	public void setHesaplar(ArrayList<BankaHesabi> hesaplar) {
        this.hesaplar = hesaplar;
    }

    public void setKrediKartlari(ArrayList<KrediKarti> krediKartlari) {
        this.krediKartlari = krediKartlari;
    }
    
    
    public boolean hesapEkle(String hesapTuru) {
        BankaHesabi yeniHesap = null;

        if (hesapTuru.equals("Vadesiz")) {
            yeniHesap = new VadesizHesap(0.0);
        } else if (hesapTuru.equals("Yatirim")) {
            yeniHesap = new YatirimHesabi(0.0);
        }

        if (yeniHesap != null) {
            hesaplar.add(yeniHesap);
            return true; // Hesap başarıyla oluşturuldu
        }
        
        return false; // Geçersiz hesap türü girildi
    }
    
    public void krediKartiEkle(double limit) {
    	//yeni bir KrediKarti nesnesi oluşturuyoruz.
    		KrediKarti yeniKart=new KrediKarti(limit,0.0);
    		//müşterinin listesine ekliyoruz.
    		this.krediKartlari.add(yeniKart);
    		System.out.println(limit + " TL limitli kredi kartı tanımlandı.");
    }
    
    public void hesapSil(BankaHesabi hesap){
    	//Parametre boş mu değil mi kontrolü.
    	if(hesap==null) {
    		System.out.println("Hata!Hesap bulunamadı.");
    		return;
    	}
    	if(hesap.getBakiye()>0) {
    		System.out.println("Hata!Hesapta bakiye varken silinemez.Bakiyeyi transfer edin.");
    	}
    	else {
    		boolean silindi=this.hesaplar.remove(hesap);
    		
    		if(silindi) {
    			System.out.println("Hesap başarıyla silindi.");
    		}
    		else {
    			System.out.println("Hata!Hesap bu müşteriye ait değil.");
    		}
    	}
    }
    
    public void krediKartiSil(KrediKarti kart) {
    	if(kart==null) {
    		System.out.println("Hata!Kart bulunamadı.");
    		return;
    	}
    	 if(kart.getGuncelBorc()>0) {
    		 System.out.println("Hata!Kartın ödenmemiş borcu bulunmaktadir: "+kart.getGuncelBorc());
    	 }
    	 else {
    		 
    		 boolean silindi=this.krediKartlari.remove(kart);
    		 if(silindi) {
    			 System.out.println("Kart başarıyla silindi.");
    		 }
    		 else {
    			 System.out.println("Hata!Bu kart bu müşteriye ait değil.");
    		 }
    	 }
    }
    
    public String toString() {
    	String metin="Müşteri numarası: "+ musteriNumarasi + " "+ super.getAd()+ "\n";
    	for(int i=0 ; i<hesaplar.size() ; i++) {
    		metin+=hesaplar.get(i).toString() + "\n";
    	}
    	for(int i=0;i<krediKartlari.size();i++) {
    		metin+=krediKartlari.get(i).toString()+ "\n";
    	}
    	return metin;
    	
    }
    
    

	
	
	
	
	
	
	
	
	
}
