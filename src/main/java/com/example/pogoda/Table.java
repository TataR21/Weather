package com.example.pogoda;

import java.util.ArrayList;
import java.util.List;

public class Table {
    //public List<Edit2> listOfStation = new ArrayList<>();
    //public List<CurrentWeather> currentWeather = new ArrayList<>();
    public List<Monthdata> monthdata = new ArrayList<>();
    public List<String> listOfStationForMonthdata = new ArrayList<>();
    public String stationName = "BIELSKO-BIAŁA";
    public String year = "2020";
    public List<Monthdata> dataSingleStation = new ArrayList<>();

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }



}
