package com.example.pogoda;

import java.util.ArrayList;
import java.util.List;

public class ForecastWeatherTable {
    public List<ForecastWeather> firstDay = new ArrayList<>();
    public List<ForecastWeather> secondDay = new ArrayList<>();
    public List<ForecastWeather> thirdDay = new ArrayList<>();
    public List<ForecastWeather> fourthDay = new ArrayList<>();
    public List<ForecastWeather> fifthDay = new ArrayList<>();
    public List<ForecastWeather> sixthDay = new ArrayList<>();
    public List<ForecastWeather> seventhDay = new ArrayList<>();
    public List<ForecastWeather> eighthDay = new ArrayList<>();
    public String stationLat;
    public String stationLon;
    public String nazwaStacji;
    public List<Edit2> listOfStation = new ArrayList<>();

    public String getStationLat() {
        return stationLat;
    }

    public void setStationLat(String stationLat) {
        this.stationLat = stationLat;
    }

    public String getStationLon() {
        return stationLon;
    }

    public void setStationLon(String stationLon) {
        this.stationLon = stationLon;
    }

    public String getNazwaStacji() {
        return nazwaStacji;
    }

    public void setNazwaStacji(String nazwaStacji) {
        this.nazwaStacji = nazwaStacji;
    }
}
