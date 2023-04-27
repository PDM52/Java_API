package zad1;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class Service {

    public static String getWeather(String city) throws Exception {
        String urlGeoloc = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=71170abf768266d0272d64a7cb0f7ba4";
        URL url = null;
        double lat=-1, lon=-1;

        try {url = new URL(urlGeoloc);}
        catch(Exception e){throw new RuntimeException(e);}

        try (InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));)
        {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            if(jsonString.length()<3)
            {
                throw new IllegalArgumentException("No city found for the given city name: " + city);
            }
            JSONArray parse = (JSONArray)new JSONParser().parse(jsonString);
            lat = Double.parseDouble(((JSONObject)parse.get(0)).get("lat").toString());
            lon = Double.parseDouble(((JSONObject)parse.get(0)).get("lon").toString());
        }
        catch(IOException | ParseException e){throw new Exception("Could not connect to http://api.openweathermap.org");}

        String urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=71170abf768266d0272d64a7cb0f7ba4";

        try {url = new URL(urlWeather);}
        catch(MalformedURLException e){throw new RuntimeException(e);}

        try (InputStream inputStream = url.openConnection().getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));)
        {
            return bufferedReader.lines().collect(Collectors.joining());
        }
        catch(IOException e){throw new Exception("Could not connect to http://api.openweathermap.org");}
    }

    public static Double getRateFor(String currency1, String currency2) throws Exception {
        String urlRate = "https://api.exchangerate.host/convert?from="+currency1+"&to="+currency2;
        URL url = null;
        try {url = new URL(urlRate);}
        catch (MalformedURLException e) {throw new RuntimeException(e);}

        try (InputStream inputStream = url.openConnection().getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));)
        {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());

            JSONObject parse = (JSONObject) new JSONParser().parse(jsonString);
            if(parse.get("result")==null)
            {
                throw new IllegalArgumentException("No currency found for one of given currency names");
            }
            return Double.parseDouble(parse.get("result").toString());
        }
        catch(IOException | ParseException e){throw new Exception("https://api.exchangerate.host");}
    }
}
