package com.example.pogoda;

public class YearData {
    private String kod;
    private String nazwa_stacji;
    private String rok;
   // private String zachmurzenie;
    private String predkosc_wiatru;
    private String temperatura;
   // private String temp;
    //private String cisnienie_pary;
    private String wilgotnosc;
    private String cisnienie_stacja;
   // private String cisnienie_morze;
    private String opad_dzien;

    public YearData(String kod, String nazwa_stacji, String rok, String predkosc_wiatru, String temperatura, String wilgotnosc, String cisnienie_stacja, String opad_dzien) {
        this.kod = kod;
        this.nazwa_stacji = nazwa_stacji;
        this.rok = rok;
        //this.zachmurzenie = zachmurzenie;
        this.predkosc_wiatru = predkosc_wiatru;
        this.temperatura = temperatura;
        this.wilgotnosc = wilgotnosc;
        this.cisnienie_stacja = cisnienie_stacja;
        this.opad_dzien = opad_dzien;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getNazwa_stacji() {
        return nazwa_stacji;
    }

    public void setNazwa_stacji(String nazwa_stacji) {
        this.nazwa_stacji = nazwa_stacji;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getPredkosc_wiatru() {
        return predkosc_wiatru;
    }

    public void setPredkosc_wiatru(String predkosc_wiatru) {
        this.predkosc_wiatru = predkosc_wiatru;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getWilgotnosc() {
        return wilgotnosc;
    }

    public void setWilgotnosc(String wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public String getCisnienie_stacja() {
        return cisnienie_stacja;
    }

    public void setCisnienie_stacja(String cisnienie_stacja) {
        this.cisnienie_stacja = cisnienie_stacja;
    }

    public String getOpad_dzien() {
        return opad_dzien;
    }

    public void setOpad_dzien(String opad_dzien) {
        this.opad_dzien = opad_dzien;
    }
}
