package com.example.pogoda;

import java.util.ArrayList;
import java.util.List;

public class Table {
    //public List<Edit2> listOfStation = new ArrayList<>();
    //public List<CurrentWeather> currentWeather = new ArrayList<>();
    public List<Monthdata> monthdata = new ArrayList<>();
    public List<String> listOfStationForMonthdata = new ArrayList<>();
    public String stationName = "BIELSKO-BIAŁA";
    public String stationName2 = "USTKA";
    public String year = "2020";
    public String year2 = "2020";
    public List<Monthdata> dataSingleStation = new ArrayList<>();
    public List<Monthdata> dataSingleStation2 = new ArrayList<>();

    public String getStationName2() {
        return stationName2;
    }

    public void setStationName2(String stationName2) {
        this.stationName2 = stationName2;
    }

    public String getYear2() {
        return year2;
    }

    public void setYear2(String year2) {
        this.year2 = year2;
    }

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
