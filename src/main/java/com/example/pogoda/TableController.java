package com.example.pogoda;

import okhttp3.OkHttpClient;
import okhttp3.Request;
//import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/index")
    public String tableForm(Model model, @ModelAttribute TableCurrentWeather table) throws IOException {
        // This returns a JSON or XML with the users
        //Iterable<Edit2> getAllUsers();
        //Edit2 edit2 = edit2Repository.findById(1).get();
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


       // String text = edit2.getKod() +" "+ edit2.getNazwa_stacji()+" "+edit2.getLat()+" "+edit2.getLon();
        //String text = edit2.nazwa_stacji;
        //JSONObject json = new JSONObject(edit2Repository.findAll());
       // monthdataRepository.findAll().forEach(table.monthdata::add);
        model.addAttribute("index",table);
        return "index";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Edit2> getAllUsers() {
        // This returns a JSON or XML with the users
        return edit2Repository.findAll();
    }

    @GetMapping(path="/new")
    public @ResponseBody Iterable<Monthdata> getAllstation() {
        // This returns a JSON or XML with the users
        return monthdataRepository.findAll();
    }

    @PostMapping("/graphs")
    public String greetingSubmit(Model model, @ModelAttribute Table table2) {
        //greeting.addNumber();
        //String text = greeting.getSite(greeting.getUrl());
       // greeting.setTitle(text);
      // table2=table;
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
        //table2.listOfStationForYearData = table.listOfStationForMonthdata;


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

    public Table xxx() {
        Table table = new Table();
        monthdataRepository.findAll().forEach(table.monthdata::add);
        for(Monthdata i: table.monthdata) {
            i.setNazwa_stacji(i.getNazwa_stacji().replaceAll("\"", ""));
            i.setRok(i.getRok().replaceAll("\"", ""));
            i.setMiesiac(i.getMiesiac().replaceAll("\"", ""));
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
            for(int x = i; x<i+12; x++) {
                wiatr = wiatr + Float.parseFloat(table.monthdata.get(x).getPredkosc_wiatru());
                temperatura = temperatura + Float.parseFloat(table.monthdata.get(x).getTemperatura());
                wilgotnosc = wilgotnosc + Float.parseFloat(table.monthdata.get(x).getWilgotnosc());
                cisnienie = cisnienie + Float.parseFloat(table.monthdata.get(x).getCisnienie_stacja());
                opad = opad + Float.parseFloat(table.monthdata.get(x).getOpad_dzien()) + Float.parseFloat(table.monthdata.get(x).getOpad_noc());

            }
            wiatr = wiatr/12;
            temperatura = temperatura/12;
            cisnienie = cisnienie/12;
            opad = opad/12;

            YearData data = new YearData(kod,nazwa_stacji,rok,Float.toString(wiatr),Float.toString(temperatura),Float.toString(wilgotnosc),Float.toString(cisnienie),Float.toString(opad));
            table2.tableYear.add(data);
            //}
        }
        return table2;
    }
}
