package com.bank.app.main;

//tüm sınıfları ekliyoruz.
import com.bank.app.people.*;
import com.bank.app.accounts.*;
import com.bank.app.cards.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Personel nesnesi 
        BankaPersoneli personel = new BankaPersoneli("Muhammed Emin", "Karakurt", "emin@bank.com", 5551234);
        
        int secim = -1;
        System.out.println("\n--- NYP BANKA SİSTEMİ ---\n");
        System.out.println("PERSONEL:Muhammed Emin Karakurt");
        //while ile her işlemde işlem menüsünü ekrana veriyoruz.
        while (secim != 0) {
            System.out.println("\n--- Islem Menusu---");
            System.out.println("1-Müşteri Oluştur  2-Hesap Aç  3-Para Yatır  4-Transfer");
            System.out.println("5-Kart Tanımla     6-Borç Öde  7-Silme       8-Bilgileri Göster");
            System.out.println("0-Çıkış");
            System.out.print("\nİşlem seçiniz: ");
            //Hata kontrolü için try-catch kullanalım
            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("HATA: Lütfen sadece sayısal bir değer giriniz!");
                    scanner.next(); 
                    continue;
                }
                secim = scanner.nextInt();
                scanner.nextLine(); 
                //Switch-case yapısı kurarak menüyü oluşturalım.
                switch (secim) {
                    case 1: // Müşteri Oluşturma
                        System.out.print("Ad: "); String ad = scanner.nextLine();
                        System.out.print("Soyad: "); String soyad = scanner.nextLine();
                        System.out.print("Email: "); String mail = scanner.nextLine();
                        System.out.print("Telefon: "); int tel = scanner.nextInt();
                        personel.getMusteriler().add(new Musteri(ad, soyad, mail, tel));
                        System.out.println("Müşteri kaydedildi.");
                        break;

                    case 2: // Hesap Açma
                        Musteri m2 = musteriSec(personel, scanner);
                        if (m2 != null) {
                            System.out.print("Tür (Vadesiz / Yatirim): ");
                            String tur = scanner.nextLine();
                            m2.hesapEkle(tur);
                            System.out.println("İşlem tamamlandı.");
                        }
                        break;

                    case 3: // Para Yatırma (Eski 83. Satır Hatası Çözümü)
                        Musteri m3 = musteriSec(personel, scanner);
                        if (m3 != null) {
                            int hIdx = hesapSec(m3, scanner);
                            if (hIdx != -1) {
                                System.out.print("Miktar: ");
                                double miktar = scanner.nextDouble();
                                BankaHesabi hesap = m3.getHesaplar().get(hIdx);
                                if (hesap instanceof YatirimHesabi) {
                                    ((YatirimHesabi) hesap).paraEkle(miktar);
                                } else {
                                    hesap.setBakiye(hesap.getBakiye() + miktar);
                                }
                                System.out.println("Yeni Bakiye: " + hesap.getBakiye());
                            }
                        }
                        break;

                    case 4: //Para transferi
                        Musteri m4 = musteriSec(personel, scanner);
                        if (m4 != null && m4.getHesaplar().size() >= 2) {
                            System.out.println("--- Gönderen Hesap ---");
                            int gIdx = hesapSec(m4, scanner);
                            System.out.println("--- Alıcı Hesap ---");
                            int aIdx = hesapSec(m4, scanner);
                            if (gIdx != -1 && aIdx != -1 && gIdx != aIdx) {
                                System.out.print("Miktar: ");
                                double miktar = scanner.nextDouble();
                                BankaHesabi g = m4.getHesaplar().get(gIdx);
                                BankaHesabi a = m4.getHesaplar().get(aIdx);
                                if (g instanceof VadesizHesap) {
                                    ((VadesizHesap) g).paraTransferi(a, g, miktar);
                                } else {
                                    System.out.println("HATA: Sadece vadesiz hesaptan transfer yapılır.");
                                }
                            }
                        } else {
                            System.out.println("HATA: Müşteri bulunamadı veya yetersiz hesap sayısı.");
                        }
                        break;

                    case 5: // Kart Tanımlama
                        Musteri m5 = musteriSec(personel, scanner);
                        if (m5 != null) {
                            System.out.print("Limit: ");
                            double limit = scanner.nextDouble();
                            m5.krediKartiEkle(limit);
                        // Son eklenen karta ulaşıp rastgele borç atayalım
                            int sonKartIdx = m5.getKrediKartlari().size() - 1;
                            KrediKarti yeniKart = m5.getKrediKartlari().get(sonKartIdx);
                            
                            // Rastgele borç oluşturma (Limitten küçük, tek haneli)
                            double rastgeleBorc = Math.random() * (limit * 0.8); // Limitin %80'ine kadar rastgele
                            rastgeleBorc = Math.floor(rastgeleBorc * 10.0) / 10.0; // Virgülden sonra tek hane
                            
                            yeniKart.setGuncelBorc(rastgeleBorc); // Borcu karta işle
                            
                            System.out.println("Kart tanımlandı. Test Borcu Atandı: " + rastgeleBorc + " TL");
                        }
                        break;
                        
                    case 6: // Kredi Kartı Borcu Ödeme
                        Musteri m6 = musteriSec(personel, scanner);
                        if (m6 != null) {
                            // Müşterinin kartı veya hesabı yoksa işlem yapamaz
                            if (m6.getKrediKartlari().isEmpty()) {
                                System.out.println("HATA: Müşterinin tanımlı bir kredi kartı bulunmamaktadır.");
                            } else if (m6.getHesaplar().isEmpty()) {
                                System.out.println("HATA: Borç ödemek için en az bir vadesiz hesabınız olmalı!");
                            } else {
                                // 1. Kart Seçimi
                                System.out.println("\n--- Kredi Kartlarınız ve Borçlarınız ---");
                                for (int i = 0; i < m6.getKrediKartlari().size(); i++) {
                                    double b = m6.getKrediKartlari().get(i).getGuncelBorc();
                                    System.out.println(i + " - Mevcut Borç: " + b + " TL");
                                }
                                System.out.print("Ödeme yapılacak kartı seçin: ");
                                int kIdx = scanner.nextInt();

                                // 2. Hesap Seçimi
                                System.out.println("\n--- Ödemenin Çekileceği Hesabı Seçin ---");
                                int hIdx = hesapSec(m6, scanner);

                                if (kIdx >= 0 && kIdx < m6.getKrediKartlari().size() && hIdx != -1) {
                                    KrediKarti secilenKart = m6.getKrediKartlari().get(kIdx);
                                    BankaHesabi secilenHesap = m6.getHesaplar().get(hIdx);

                                    if (secilenKart.getGuncelBorc() <= 0) {
                                        System.out.println("Bu kartın borcu bulunmamaktadır. Borç: 0.0 TL");
                                    } else {
                                        System.out.print("Ödemek istediğiniz miktar: ");
                                        double miktar = scanner.nextDouble();
                                        scanner.nextLine();

                                        // --- MANTIK KONTROLLERİ ---
                                        if (miktar <= 0) {
                                            System.out.println("HATA: Ödeme miktarı pozitif olmalıdır!");
                                        } else if (miktar > secilenHesap.getBakiye()) {
                                            System.out.println("HATA: Yetersiz bakiye! Hesabınızda sadece " + secilenHesap.getBakiye() + " TL var.");
                                        } else if (miktar > secilenKart.getGuncelBorc()) {
                                            System.out.println("HATA: Borcunuzdan (" + secilenKart.getGuncelBorc() + " TL) daha fazla ödeme yapamazsınız!");
                                        } else {
                                            // İşlem onaylandı: Parayı hesaptan düş, borçtan düş
                                            secilenHesap.setBakiye(secilenHesap.getBakiye() - miktar);
                                            secilenKart.borcOde(miktar); // Sınıf içindeki borcOde metodunu çağırır

                                            System.out.println("\nİŞLEM BAŞARILI!");
                                            System.out.println("Hesaptan çekilen: " + miktar + " TL");
                                            System.out.println("Kalan Hesap Bakiyesi: " + secilenHesap.getBakiye() + " TL");
                                        }
                                    }
                                } else {
                                    System.out.println("HATA: Geçersiz kart veya hesap seçimi!");
                                }
                            }
                        }
                        break;
                   

                    case 7: // Silme
                        Musteri m7 = musteriSec(personel, scanner);
                        if (m7 != null) {
                            System.out.println("1-Hesap Sil / 2-Kart Sil");
                            int silSec = scanner.nextInt();
                            if(silSec == 1) {
                                int hIdx = hesapSec(m7, scanner);
                                if(hIdx != -1) m7.hesapSil(m7.getHesaplar().get(hIdx));
                            } else if (!m7.getKrediKartlari().isEmpty()) {
                                System.out.print("Kart No: ");
                                int kIdx = scanner.nextInt();
                                m7.krediKartiSil(m7.getKrediKartlari().get(kIdx));
                            }
                        }
                        break;

                    case 8: // Bilgi Göster
                        Musteri m8 = musteriSec(personel, scanner);
                        if (m8 != null) System.out.println(m8.toString());
                        break;

                    case 0: System.out.println("Çıkış yapılıyor..."); break;
                    default: System.out.println("Geçersiz işlem!");
                }
            } catch (Exception e) {
                System.out.println("BİR HATA OLUŞTU! Lütfen girişlerinizi kontrol edin.");
                scanner.nextLine(); 
            }
        }
    }

    // GÜVENLİ SEÇİM YARDIMCILARI
    public static Musteri musteriSec(BankaPersoneli p, Scanner s) {
        if (p.getMusteriler().isEmpty()) {
            System.out.println("Sistemde müşteri kayıtlı değil!");
            return null;
        }
        System.out.println("\n--- Kayıtlı Müşteriler ---");
        for (int i = 0; i < p.getMusteriler().size(); i++) 
            System.out.println(i + " - " + p.getMusteriler().get(i).getAd() + " " + p.getMusteriler().get(i).getSoyad());
        System.out.print("Müşteri No: ");
        if (s.hasNextInt()) {
            int idx = s.nextInt(); s.nextLine();
            if (idx >= 0 && idx < p.getMusteriler().size()) return p.getMusteriler().get(idx);
        } else { s.next(); }
        return null;
    }

    public static int hesapSec(Musteri m, Scanner s) {
        if (m.getHesaplar().isEmpty()) {
            System.out.println("Hesap bulunamadı!");
            return -1;
        }
        for (int i = 0; i < m.getHesaplar().size(); i++) 
            System.out.println(i + " - IBAN: " + m.getHesaplar().get(i).getIban());
        System.out.print("Hesap No: ");
        if (s.hasNextInt()) {
            int idx = s.nextInt(); s.nextLine();
            if (idx >= 0 && idx < m.getHesaplar().size()) return idx;
        } else { s.next(); }
        return -1;
    }
}