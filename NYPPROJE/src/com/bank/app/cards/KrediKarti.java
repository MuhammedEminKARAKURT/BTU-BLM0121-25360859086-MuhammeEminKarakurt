package com.bank.app.cards;

import java.util.Random;

public class KrediKarti {

	private String kartNumarasi;
	private double limit;
	private double guncelBorc;
	private double kullanilabilirLimit;
	
	public KrediKarti(double limit,double guncelBorc){
		this.guncelBorc=guncelBorc;
		this.limit=limit;
		this.kullanilabilirLimit= limit - guncelBorc;
		
		Random r=new Random();
		String numara="";
		
		for(int i =0;i<15;i++) {
			numara+=r.nextInt(9)+1;
		}
		    this.kartNumarasi=numara;
	}
	
	
	public void rastgeleBorcAtama() {
	    Random random = new Random();
	   
	    double minBorc = this.limit * 0.1;
	    double maxBorc = this.limit * 0.9;
	    
	    double rastgeleDeger = minBorc + (maxBorc - minBorc) * random.nextDouble();
	    
	    // Sayıyı virgülden sonra 2 hane olacak şekilde formatlıyoruz
	    String formatliBorc = String.format("%.2f", rastgeleDeger);
	    
	    // Formatlanan String'i tekrar double'a çevirip güncel borca atıyoruz
	    this.guncelBorc = Double.parseDouble(formatliBorc.replace(",", "."));
	    
	    
	    this.kullanilabilirLimit = this.limit - this.guncelBorc;
	}
	
	
	public String getKartNumarasi() {
		return kartNumarasi;
	}

	public void setKartNumarasi(String kartNumarasi) {
		this.kartNumarasi = kartNumarasi;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getGuncelBorc() {
		return guncelBorc;
	}

	public void setGuncelBorc(double guncelBorc) {
		this.guncelBorc = guncelBorc;
	}

	public double getKullanilabilirLimit() {
		return kullanilabilirLimit;
	}

	public void setKullanilabilirLimit(double kullanilabilirLimit) {
		this.kullanilabilirLimit = kullanilabilirLimit;
	}

	public String toString() {
        return "Kart No: " + kartNumarasi + " | Limit: " + limit + " | Borç: " + guncelBorc;
    }
	public void borcOde(double miktar) {
	    // Borcu düş
	    this.guncelBorc -= miktar;

	    // Hassas hesaplama hatasını (0.0000001 gibi) engellemek ve tek hane yapmak için
	    this.guncelBorc = Math.round(this.guncelBorc * 10.0) / 10.0;

	    // Borç 0'ın altına düşerse 0'a sabitle
	    if (this.guncelBorc < 0) {
	        this.guncelBorc = 0.0;
	    }

	    if (this.guncelBorc == 0) {
	        System.out.println("Tebrikler! Kartın tüm borcu ödendi. Güncel Borç: 0.0 TL");
	    } else {
	        System.out.println("Kartın kalan borcu: " + this.guncelBorc + " TL");
	    }
	}
	
	
}
