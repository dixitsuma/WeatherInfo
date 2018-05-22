package com.example.user3.weatherinfo;

/**
 * Created by user3 on 24/4/18.
 */

public class Main {

    private float temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double visibility;

    public Main(float temp, double pressure, double humidity, double temp_min, double temp_max, double visibility) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.visibility = visibility;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getVisibility() {
        return visibility;
    }
}
