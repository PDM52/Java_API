package zad1;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {

  public static void main(String[] args)
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the city to see weather: ");
    String city = scanner.nextLine();

    try {
      String weatherJson = Service.getWeather(city);
      Gson gson = new Gson();
      Weather weather = gson.fromJson(weatherJson, Weather.class);

      System.out.println();
      System.out.println("Temperature: " + (int) (weather.main.temp - 273.15) + "°C");
      System.out.println("Pressure: " + weather.main.pressure + "hPa");
      System.out.println("Humidity: " + weather.main.humidity + "%");
      System.out.println();
    }
    catch(Exception e){System.out.println(e.getMessage());}

    System.out.println();
    System.out.println("Enter the name of the currency for which you want to see the exchange rate: ");
    String currency1 = scanner.nextLine();
    System.out.println("Enter the name of the currency against which you want to check the exchange rate: ");
    String currency2 = scanner.nextLine();
    try {
      Double rate = Service.getRateFor(currency1, currency2);
      System.out.println("Exchange rate for "+ currency1 + "/" + currency2 + ": " + String.format("%.3f", rate));
    }
    catch(Exception e){System.out.println(e.getMessage());}


  }
}