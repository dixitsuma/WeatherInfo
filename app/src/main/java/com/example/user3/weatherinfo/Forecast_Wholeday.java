package com.example.user3.weatherinfo;

/**
 * Created by user3 on 11/5/18.
 */

public class Forecast_Wholeday {
    private String time;
   private double temperature;
   private String description;

    public Forecast_Wholeday(String day, double temperature, String description) {
        this.time = day;
        this.temperature = temperature;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String day) {
        this.time = day;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Forecast_Wholeday{" +
                "time='" + time + '\'' +
                ", temperature=" + temperature +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
