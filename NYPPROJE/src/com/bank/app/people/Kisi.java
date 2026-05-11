package com.bank.app.people;

public class Kisi {
	
	
	private String ad;
	private String soyad;
	private String email;
	private int telefonNumarasi;
	
	
	public Kisi(String ad,String soyad,String email,int telefonNumarasi){
		this.ad=ad;
		this.soyad=soyad;
		this.email=email;
		this.telefonNumarasi=telefonNumarasi;
	}
	
	//toString metodu ile kişiye ait bilgileri görelim.
	public String toString() {
		return "Kisi [Ad: " + ad + ", Soyad: " + soyad + 
	               ", Email: " + email + ", Tel: " + telefonNumarasi + "]";
	}
	
	
	public void setAd(String ad) {
		this.ad=ad;
	}
	public void setSoyad(String soyad) {
		this.soyad=soyad;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public void setTelefonNumarasi(int telefonNumarasi) {
		this.telefonNumarasi=telefonNumarasi;
	}
	
	
	    public String getAd() {
	        return ad;
	    }

	    
	    public String getSoyad() {
	        return soyad;
	    }

	    
	    public String getEmail() {
	        return email;
	    }

	    
	    public int getTelefonNumarasi() {
	        return telefonNumarasi;
	    }
	

}
