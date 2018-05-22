package com.example.user3.weatherinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user3 on 10/5/18.
 */

public class Forecast_list {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Forecast_main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Forecast_weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Forecast_clouds clouds;
    @SerializedName("wind")
    @Expose
    private Forecast_wind wind;
    @SerializedName("rain")
    @Expose
    private Forecast_rain rain;

    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Forecast_main getMain() {
        return main;
    }

    public void setMain(Forecast_main main) {
        this.main = main;
    }

    public java.util.List<Forecast_weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Forecast_weather> weather) {
        this.weather = weather;
    }

    public Forecast_clouds getClouds() {
        return clouds;
    }

    public void setClouds(Forecast_clouds clouds) {
        this.clouds = clouds;
    }

    public Forecast_wind getWind() {
        return wind;
    }

    public void setWind(Forecast_wind wind) {
        this.wind = wind;
    }

    public Forecast_rain getRain() {
        return rain;
    }

    public void setRain(Forecast_rain rain) {
        this.rain = rain;
    }


    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

}
