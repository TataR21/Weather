package com.example.pogoda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Monthdata {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String kod;
    private String nazwa_stacji;
    private String rok;
    private String miesiac;
    private String zachmurzenie;
    private String nos;
    private String predkosc_wiatru;
    private String fws;
    private String temperatura;
    private String temp;
    private String cisnienie_pary;
    private String cpw;
    private String wilgotnosc;
    private String wlgs;
    private String cisnienie_stacja;
    private String ppps;
    private String cisnienie_morze;
    private String pppm;
    private String opad_dzien;
    private String wodz;
    private String opad_noc;
    private String wono;

    public Monthdata() {}

    public Monthdata(String id, String kod, String nazwa_stacji, String rok, String miesiac, String zachmurzenie, String nos, String predkosc_wiatru, String fws, String temperatura, String temp, String cisnienie_pary, String cpw, String wilgotnosc, String wlgs, String cisnienie_stacja, String ppps, String cisnienie_morze, String pppm, String opad_dzien, String wodz, String opad_noc, String wono) {
        this.id = id;
        this.kod = kod;
        this.nazwa_stacji = nazwa_stacji;
        this.rok = rok;
        this.miesiac = miesiac;
        this.zachmurzenie = zachmurzenie;
        this.nos = nos;
        this.predkosc_wiatru = predkosc_wiatru;
        this.fws = fws;
        this.temperatura = temperatura;
        this.temp = temp;
        this.cisnienie_pary = cisnienie_pary;
        this.cpw = cpw;
        this.wilgotnosc = wilgotnosc;
        this.wlgs = wlgs;
        this.cisnienie_stacja = cisnienie_stacja;
        this.ppps = ppps;
        this.cisnienie_morze = cisnienie_morze;
        this.pppm = pppm;
        this.opad_dzien = opad_dzien;
        this.wodz = wodz;
        this.opad_noc = opad_noc;
        this.wono = wono;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getZachmurzenie() {
        return zachmurzenie;
    }

    public void setZachmurzenie(String zachmurzenie) {
        this.zachmurzenie = zachmurzenie;
    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;
    }

    public String getPredkosc_wiatru() {
        return predkosc_wiatru;
    }

    public void setPredkosc_wiatru(String predkosc_wiatru) {
        this.predkosc_wiatru = predkosc_wiatru;
    }

    public String getFws() {
        return fws;
    }

    public void setFws(String fws) {
        this.fws = fws;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCisnienie_pary() {
        return cisnienie_pary;
    }

    public void setCisnienie_pary(String cisnienie_pary) {
        this.cisnienie_pary = cisnienie_pary;
    }

    public String getCpw() {
        return cpw;
    }

    public void setCpw(String cpw) {
        this.cpw = cpw;
    }

    public String getWilgotnosc() {
        return wilgotnosc;
    }

    public void setWilgotnosc(String wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public String getWlgs() {
        return wlgs;
    }

    public void setWlgs(String wlgs) {
        this.wlgs = wlgs;
    }

    public String getCisnienie_stacja() {
        return cisnienie_stacja;
    }

    public void setCisnienie_stacja(String cisnienie_stacja) {
        this.cisnienie_stacja = cisnienie_stacja;
    }

    public String getPpps() {
        return ppps;
    }

    public void setPpps(String ppps) {
        this.ppps = ppps;
    }

    public String getCisnienie_morze() {
        return cisnienie_morze;
    }

    public void setCisnienie_morze(String cisnienie_morze) {
        this.cisnienie_morze = cisnienie_morze;
    }

    public String getPppm() {
        return pppm;
    }

    public void setPppm(String pppm) {
        this.pppm = pppm;
    }

    public String getOpad_dzien() {
        return opad_dzien;
    }

    public void setOpad_dzien(String opad_dzien) {
        this.opad_dzien = opad_dzien;
    }

    public String getWodz() {
        return wodz;
    }

    public void setWodz(String wodz) {
        this.wodz = wodz;
    }

    public String getOpad_noc() {
        return opad_noc;
    }

    public void setOpad_noc(String opad_noc) {
        this.opad_noc = opad_noc;
    }

    public String getWono() {
        return wono;
    }

    public void setWono(String wono) {
        this.wono = wono;
    }




}
