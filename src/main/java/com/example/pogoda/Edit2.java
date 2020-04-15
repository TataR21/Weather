package com.example.pogoda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Edit2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private String id;

    private String kod;
    private String nazwa_stacji;
    private String lon;
    private String lat;

    public Edit2() {}

    public Edit2(String id, String kod, String nazwa_stacji, String lon, String lat){
        this.id = id;
        this.kod = kod;
        this.nazwa_stacji = nazwa_stacji;
        this.lon = lon;
        this.lat = lat;
    }

   public void setId(String id) {
       this.id = id;
   }

   public void setKod(String kod) {
       this.kod = kod;
   }

    public void setNazwa_stacji(String nazwa_stacji) {
        this.nazwa_stacji = nazwa_stacji;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

   public String getId() {
       return id;
    }

    public String getKod() {
       return kod;
    }

    public String getNazwa_stacji() {
        return nazwa_stacji;
    }

    public String getLon() {
        return lon;
    }

    public  String getLat() {
        return lat;
    }
}
