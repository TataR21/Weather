package com.example.pogoda;

import java.util.ArrayList;
import java.util.List;

public class TableYear {
    public List<YearData> tableYear = new ArrayList<>();
    public String stationName = "BIELSKO-BIAŁA";
    public List<String> listOfStationForYearData = new ArrayList<>();
    public List<YearData> dataSingleStationYear = new ArrayList<>();
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
