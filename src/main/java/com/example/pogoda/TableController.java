package com.example.pogoda;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
//@RequestMapping(path="/all")
public class TableController {
    @Autowired
    private Edit2Repository edit2Repository;
    @Autowired
    private MonthdataRepository monthdataRepository;

    private Table table;
    private TableYear tableYearr;
    private TableCurrentWeather tableCurrentWeather;
    @GetMapping("/")
    public String tableForm(Model model, @ModelAttribute TableCurrentWeather table) throws IOException {
        edit2Repository.findAll().forEach(table.listOfStation::add);
        for(Edit2 i: table.listOfStation) {
            int londegrees = Integer.parseInt(i.getLon().substring(0,2));
            int latdegrees = Integer.parseInt(i.getLat().substring(0,2));
            float lonminutes = Integer.parseInt(i.getLon().substring(2,4));
            float latminutes = Integer.parseInt(i.getLat().substring(2,4));
            float templon = londegrees + lonminutes/60;
            float templat = latdegrees +latminutes/60;
            i.setLat(String.valueOf(templat));
            i.setLon(String.valueOf(templon));

        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://danepubliczne.imgw.pl/api/data/synop")
                .get()
                .build();

        okhttp3.ResponseBody responseBody = client.newCall(request).execute().body();
        String text = responseBody.string();
        text = "{\"array\":"+text+"}";
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(text).getAsJsonObject();
        for(int i = 0; i<json.getAsJsonArray("array").size();i++) {
            String idStation = json.getAsJsonArray("array").get(i).getAsJsonObject().get("id_stacji").toString();
            idStation = idStation.replaceAll("\"","");
            idStation = idStation.replaceAll("\\\\","");
            String stationName = json.getAsJsonArray("array").get(i).getAsJsonObject().get("stacja").toString();
            stationName = stationName.replaceAll("\"","");
            String date = json.getAsJsonArray("array").get(i).getAsJsonObject().get("data_pomiaru").toString();
            date = date.replaceAll("\"","");
            String time = json.getAsJsonArray("array").get(i).getAsJsonObject().get("godzina_pomiaru").toString();
            time = time.replaceAll("\"","");
            String temperature = json.getAsJsonArray("array").get(i).getAsJsonObject().get("temperatura").toString();
            temperature = temperature.replaceAll("\"","");
            String windSpeed = json.getAsJsonArray("array").get(i).getAsJsonObject().get("predkosc_wiatru").toString();
            windSpeed = windSpeed.replaceAll("\"","");
            String humidity = json.getAsJsonArray("array").get(i).getAsJsonObject().get("wilgotnosc_wzgledna").toString();
            humidity = humidity.replaceAll("\"","");
            String rainfall = json.getAsJsonArray("array").get(i).getAsJsonObject().get("suma_opadu").toString();
            rainfall = rainfall.replaceAll("\"","");
            String pressure = json.getAsJsonArray("array").get(i).getAsJsonObject().get("cisnienie").toString();
            pressure = pressure.replaceAll("\"","");
            String lat="1";
            String lon="1";
            for(int j=0; j < rainfall.length(); j++) {
                if(rainfall.charAt(j) == '0') {
                    rainfall = rainfall.substring(0,j) + rainfall.substring(j+1);
                    j--;
                } else {
                    rainfall = "0" + rainfall;
                    break;
                }
            }
            if(rainfall.equals("")){
                rainfall = "Brak pomiaru";
            }
            if(pressure.equals("null")) {
                pressure = "Brak pomiaru";
            }
            for(Edit2 x :table.listOfStation) {
                String temp1 = x.getKod().substring(x.getKod().length()-3);
                String temp2 = idStation.substring(idStation.length()-3);
                if(temp1.equals(temp2)) {
                    lat = x.getLat();
                    lon = x.getLon();
                }
            }
            CurrentWeather currentWeather = new CurrentWeather(idStation, stationName, date, time, temperature, windSpeed, humidity, rainfall, pressure, lat, lon);
            table.currentWeather.add(currentWeather);
        }
        model.addAttribute("index",table);
        return "index";
    }

    @PostMapping("/graphs")
    public String greetingSubmit(Model model, @ModelAttribute Table table2) {
        table2.listOfStationForMonthdata = table.listOfStationForMonthdata;
        for(Monthdata i: table.monthdata) {
            if(i.getNazwa_stacji().toLowerCase().equals(table2.stationName.toLowerCase()) && i.getRok().equals(table2.year)){
                table2.dataSingleStation.add(i);
            }
        }
        model.addAttribute("graphs", table2);
        return "graphs";
    }

    @GetMapping(path="/graphs")
    public String graphs(Model model, @ModelAttribute Table table2) {

        table2 = xxx();
        table = table2;
        model.addAttribute("graphs",table2);
        return "graphs";
    }
    @GetMapping(path="/year")
    public String year(Model model, @ModelAttribute TableYear table2) {
        table = xxx();
        table2 = zzz();
        table2.listOfStationForYearData = table.listOfStationForMonthdata;
        for(YearData i: table2.tableYear) {
            if(i.getNazwa_stacji().equals(table2.stationName)) {
                table2.dataSingleStationYear.add(i);
            }
        }
        tableYearr = table2;
        model.addAttribute("year",table2);
        return "year";
    }

    @PostMapping("/year")
    public String yearPost(Model model, @ModelAttribute TableYear table2) {
        table2.listOfStationForYearData = tableYearr.listOfStationForYearData;
        for(YearData i: tableYearr.tableYear) {
            if(i.getNazwa_stacji().equals(table2.stationName)) {
                table2.dataSingleStationYear.add(i);
            }
        }
        model.addAttribute("year", table2);
        return "year";
    }

    @GetMapping(path="/compare")
    public String compare(Model model, @ModelAttribute Table table2) {
        table2 = xxx();
        table = table2;
        for(Monthdata i: table.monthdata) {
            if(i.getNazwa_stacji().toLowerCase().equals(table2.stationName2.toLowerCase()) && i.getRok().equals(table2.year2)){
                table2.dataSingleStation2.add(i);
            }
        }
        model.addAttribute("compare",table2);
        return "compare";
    }

    @PostMapping("/compare")
    public String comparePost(Model model, @ModelAttribute Table table2) {

        table2.listOfStationForMonthdata = table.listOfStationForMonthdata;
        for(Monthdata i: table.monthdata) {
            if(i.getNazwa_stacji().toLowerCase().equals(table2.stationName.toLowerCase()) && i.getRok().equals(table2.year)){
                table2.dataSingleStation.add(i);
            }
            if(i.getNazwa_stacji().toLowerCase().equals(table2.stationName2.toLowerCase()) && i.getRok().equals(table2.year2)){
                table2.dataSingleStation2.add(i);
            }
        }
        model.addAttribute("compare", table2);
        return "compare";
    }

    @GetMapping(path="/forecast")
    public String forecast(Model model, @ModelAttribute ForecastWeatherTable table) throws IOException {
        table.stationLat = "53.1";
        table.stationLon = "23.15";
        table.nazwaStacji = "Warszawa";
        edit2Repository.findAll().forEach(table.listOfStation::add);
        for(Edit2 i: table.listOfStation) {
            int londegrees = Integer.parseInt(i.getLon().substring(0,2));
            int latdegrees = Integer.parseInt(i.getLat().substring(0,2));
            float lonminutes = Integer.parseInt(i.getLon().substring(2,4));
            float latminutes = Integer.parseInt(i.getLat().substring(2,4));
            float templon = londegrees + lonminutes/60;
            float templat = latdegrees +latminutes/60;
            i.setLat(String.valueOf(templat));
            i.setLon(String.valueOf(templon));
        }
        String lat = table.stationLat;
        String lon = table.stationLon;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&appid=73e8a7db4bbec721a3c1e9623edd0093")
                .get()
                .build();
        okhttp3.ResponseBody responseBody = client.newCall(request).execute().body();
        JSONObject json = new JSONObject(responseBody.string());
        int counter = 0;
        for(int i =0; i<json.getJSONArray("list").length(); i++) {
            double tempp = Double.parseDouble(json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("temp").toString()) -  273.15;
            tempp = Math.round(tempp * 100.0) / 100.0;
            String temp = "Temperatura: "+Double.toString(tempp) + "\u2103";
            double feelsLikee = Double.parseDouble(json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("feels_like").toString()) - (float) 273.15;
            feelsLikee = Math.round(feelsLikee * 100.0) / 100.0;
            String feelsLike = "Temperatura odczuwalna: "+Double.toString(feelsLikee) + "\u2103";
            String pressure = "Ciśnienie: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("pressure").toString() +"hPa";
            String humidity = "Wilgotność: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("humidity").toString()+"%";
            String cloudIcon = "http://openweathermap.org/img/wn/"+json.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("icon").toString()+"@2x.png";
            String wind_speed = "Prędkość wiatru: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("wind").get("speed").toString()+"m/s";
            String date = json.getJSONArray("list").getJSONObject(i).get("dt_txt").toString();
            String dateif = date.substring(0,10);
            ForecastWeather forecastWeather = new ForecastWeather(temp, feelsLike, pressure, humidity, cloudIcon, wind_speed, date);
            if(i==5){
                counter = 1;
            }
            if(i==10){
                counter = 2;
            }
            if(i==15){
                counter = 3;
            }
            if(i==20){
                counter = 4;
            }
            if(i==25){
                counter = 5;
            }
            if(i==30){
                counter = 6;
            }
            if(i==35){
                counter = 7;
            }

            switch (counter) {
                case 0:
                    table.firstDay.add(forecastWeather);
                    break;
                case 1:
                    table.secondDay.add(forecastWeather);
                    break;
                case 2:
                    table.thirdDay.add(forecastWeather);
                    break;
                case 3:
                    table.fourthDay.add(forecastWeather);
                    break;
                case 4:
                    table.fifthDay.add(forecastWeather);
                    break;
                case 5:
                    table.sixthDay.add(forecastWeather);
                    break;
                case 6:
                    table.seventhDay.add(forecastWeather);
                    break;
                case 7:
                    table.eighthDay.add(forecastWeather);
                    break;
            }
        }
        model.addAttribute("forecast",table);
        return "forecast";
    }

    @PostMapping("/forecast")
    public String forecastPost(Model model, @ModelAttribute ForecastWeatherTable table2) throws IOException {
        edit2Repository.findAll().forEach(table2.listOfStation::add);
        for(Edit2 i: table2.listOfStation) {
            int londegrees = Integer.parseInt(i.getLon().substring(0,2));
            int latdegrees = Integer.parseInt(i.getLat().substring(0,2));
            float lonminutes = Integer.parseInt(i.getLon().substring(2,4));
            float latminutes = Integer.parseInt(i.getLat().substring(2,4));
            float templon = londegrees + lonminutes/60;
            float templat = latdegrees +latminutes/60;
            i.setLat(String.valueOf(templat));
            i.setLon(String.valueOf(templon));
        }
        String lat = table2.stationLat;
        String lon = table2.stationLon;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&appid=73e8a7db4bbec721a3c1e9623edd0093")
                .get()
                //.addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                //.addHeader("x-rapidapi-key", "2c73e1dc78msh14de78fe93c6aa8p1cc091jsn2500814b47b2")
                .build();
        okhttp3.ResponseBody responseBody = client.newCall(request).execute().body();
        JSONObject json = new JSONObject(responseBody.string());
        int counter = 0;
        for(int i =0; i<json.getJSONArray("list").length(); i++) {
            double tempp = Double.parseDouble(json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("temp").toString()) -  273.15;
            tempp = Math.round(tempp * 100.0) / 100.0;
            String temp = "Temperatura: "+Double.toString(tempp) + "\u2103";
            double feelsLikee = Double.parseDouble(json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("feels_like").toString()) - (float) 273.15;
            feelsLikee = Math.round(feelsLikee * 100.0) / 100.0;
            String feelsLike = "Temperatura odczuwalna: "+Double.toString(feelsLikee) + "\u2103";
            String pressure = "Ciśnienie: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("pressure").toString() +"hPa";
            String humidity = "Wilgotność: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("humidity").toString()+"%";
            String cloudIcon = "http://openweathermap.org/img/wn/"+json.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("icon").toString()+"@2x.png";
            String wind_speed = "Prędkość wiatru: "+json.getJSONArray("list").getJSONObject(i).getJSONObject("wind").get("speed").toString()+"m/s";
            String date = json.getJSONArray("list").getJSONObject(i).get("dt_txt").toString();
            ForecastWeather forecastWeather = new ForecastWeather(temp, feelsLike, pressure, humidity, cloudIcon, wind_speed, date);
            if(i==5){
                counter = 1;
            }
            if(i==10){
                counter = 2;
            }
            if(i==15){
                counter = 3;
            }
            if(i==20){
                counter = 4;
            }
            if(i==25){
                counter = 5;
            }
            if(i==30){
                counter = 6;
            }
            if(i==35){
                counter = 7;
            }

            switch (counter) {
                case 0:
                    table2.firstDay.add(forecastWeather);
                    break;
                case 1:
                    table2.secondDay.add(forecastWeather);
                    break;
                case 2:
                    table2.thirdDay.add(forecastWeather);
                    break;
                case 3:
                    table2.fourthDay.add(forecastWeather);
                    break;
                case 4:
                    table2.fifthDay.add(forecastWeather);
                    break;
                case 5:
                    table2.sixthDay.add(forecastWeather);
                    break;
                case 6:
                    table2.seventhDay.add(forecastWeather);
                    break;
                case 7:
                    table2.eighthDay.add(forecastWeather);
                    break;
            }
        }
        model.addAttribute("forecast", table2);
        return "forecast";
    }

    public Table xxx() {
        Table table = new Table();
        monthdataRepository.findAll().forEach(table.monthdata::add);
        for(Monthdata i: table.monthdata) {
            i.setNazwa_stacji(i.getNazwa_stacji().replaceAll("\"", ""));
            i.setNazwa_stacji(i.getNazwa_stacji().replaceAll("\\\\",""));
            i.setRok(i.getRok().replaceAll("\"", ""));
            i.setRok(i.getRok().replaceAll("\\\\",""));
            i.setMiesiac(i.getMiesiac().replaceAll("\"", ""));
            i.setMiesiac(i.getMiesiac().replaceAll("\\\\",""));
            if (i.getNazwa_stacji().equals("POZNAŃ-ŁAWICA")) {
                i.setNazwa_stacji("POZNAŃ");
            }
            switch (i.getMiesiac()) {
                case "1":
                case "01":
                    i.setMiesiac("Styczeń");
                    break;
                case "2":
                case "02":
                    i.setMiesiac("Luty");
                    break;
                case "3":
                case "03":
                    i.setMiesiac("Marzec");
                    break;
                case "4":
                case "04":
                    i.setMiesiac("Kwiecień");
                    break;
                case "5":
                case "05":
                    i.setMiesiac("Maj");
                    break;
                case "6":
                case "06":
                    i.setMiesiac("Czerwiec");
                    break;
                case "7":
                case "07":
                    i.setMiesiac("Lipiec");
                    break;
                case "8":
                case "08":
                    i.setMiesiac("Sierpień");
                    break;
                case "9":
                case "09":
                    i.setMiesiac("Wrzesień");
                    break;
                case "10":
                    i.setMiesiac("Październik");
                    break;
                case "11":
                    i.setMiesiac("Listopad");
                    break;
                case "12":
                    i.setMiesiac("Grudzień");
                    break;

            }
            if (!table.listOfStationForMonthdata.contains(i.getNazwa_stacji())) {
                table.listOfStationForMonthdata.add(i.getNazwa_stacji());
            }
            if (i.getNazwa_stacji().equals(table.stationName) && i.getRok().equals(table.year)) {
                table.dataSingleStation.add(i);
            }
        }
        return table;
    }

    public TableYear zzz() {
        TableYear table2 = new TableYear();
        for(int i = 177; i<table.monthdata.size()-11; i=i+12) {
            //Monthdata temp = new Monthdata();
            // temp = table.monthdata.get(i);
            //if(table.monthdata.get(i).getNazwa_stacji().equals(table2.stationName)) {
            String kod = table.monthdata.get(i).getKod();
            String nazwa_stacji = table.monthdata.get(i).getNazwa_stacji();
            String rok = table.monthdata.get(i).getRok();
            float wiatr = Float.parseFloat(table.monthdata.get(i).getPredkosc_wiatru());
            float temperatura = Float.parseFloat(table.monthdata.get(i).getTemperatura());
            float wilgotnosc = Float.parseFloat(table.monthdata.get(i).getWilgotnosc());
            float cisnienie = Float.parseFloat(table.monthdata.get(i).getCisnienie_stacja());
            float opad = Float.parseFloat(table.monthdata.get(i).getOpad_dzien()) + Float.parseFloat(table.monthdata.get(i).getOpad_noc());
            int counter = 1;
            boolean save = true;
            for(int x = i+1; x<i+12; x++) {
                String yy = table.monthdata.get(x).getFws();
                String z = table.monthdata.get(x).getTemp();
                String ww = table.monthdata.get(x).getWlgs();
                if(table.monthdata.get(x).getFws().equals("\"8\"") || table.monthdata.get(x).getTemp().equals("\"8\"") || table.monthdata.get(x).getWlgs().equals("\"8\"") || table.monthdata.get(x).getPpps().equals("\"8\"") || table.monthdata.get(x).getWodz().equals("\"8\"") || table.monthdata.get(x).getWono().equals("\"8\"")) {
                    save = false;
                    counter--;

                }

                if(!table.monthdata.get(x).getNazwa_stacji().equals(nazwa_stacji)) {
                    //save = false;
                    i=i+counter-11;
                    break;
                }

                wiatr = wiatr + Float.parseFloat(table.monthdata.get(x).getPredkosc_wiatru());
                temperatura = temperatura + Float.parseFloat(table.monthdata.get(x).getTemperatura());
                wilgotnosc = wilgotnosc + Float.parseFloat(table.monthdata.get(x).getWilgotnosc());
                cisnienie = cisnienie + Float.parseFloat(table.monthdata.get(x).getCisnienie_stacja());
                opad = opad + Float.parseFloat(table.monthdata.get(x).getOpad_dzien()) + Float.parseFloat(table.monthdata.get(x).getOpad_noc());
                counter++;

            }
            wiatr = wiatr/counter;
            temperatura = temperatura/counter;
            cisnienie = cisnienie/counter;
            opad = opad/counter;
            if(save) {
                YearData data = new YearData(kod,nazwa_stacji,rok,Float.toString(wiatr),Float.toString(temperatura),Float.toString(wilgotnosc),Float.toString(cisnienie),Float.toString(opad));
                table2.tableYear.add(data);
            }

            //}
        }
        return table2;
    }
}
