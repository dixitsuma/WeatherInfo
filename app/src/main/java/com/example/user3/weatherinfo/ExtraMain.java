package com.example.user3.weatherinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user3 on 24/4/18.
 */

public class ExtraMain {

    private Main main;
    private String name;
    private Coord coord;
    private  Sys sys;
    private int dt;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    {
        weather = null;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public ExtraMain(Main main, String name, Coord coord, Sys sys, int dt) {
        this.main = main;
        this.name = name;
        this.coord = coord;
        this.sys = sys;
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }
}
