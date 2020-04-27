package com.example.pogoda;

public class ForecastWeather {
    private String temp;
    private String feelsLike;
    private String pressure;
    private String humidity;
    private String cloudIcon;
    private String wind_speed;
    private String date;

    public ForecastWeather(String temp, String feelsLike, String pressure, String humidity, String cloudIcon, String wind_speed, String date) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudIcon = cloudIcon;
        this.wind_speed = wind_speed;
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCloudIcon() {
        return cloudIcon;
    }

    public void setCloudIcon(String cloudIcon) {
        this.cloudIcon = cloudIcon;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
