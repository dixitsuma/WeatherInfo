package com.example.user3.weatherinfo;

import java.util.List;

/**
 * Created by user3 on 11/5/18.
 */

public class FinalWeatherData {

    private ExtraMain extraMain;
    private List<Forecast_Wholeday> forecast_wholeday;

    public FinalWeatherData(ExtraMain extraMain, List<Forecast_Wholeday> forecast_wholeday) {
        this.extraMain = extraMain;
        this.forecast_wholeday = forecast_wholeday;
    }

    public FinalWeatherData() {
    }

    public ExtraMain getExtraMain() {
        return extraMain;
    }

    public void setExtraMain(ExtraMain extraMain) {
        this.extraMain = extraMain;
    }

    public List<Forecast_Wholeday> getForecast_wholeday() {
        return forecast_wholeday;
    }

    public void setForecast_wholeday(List<Forecast_Wholeday> forecast_wholeday) {
        this.forecast_wholeday = forecast_wholeday;
    }
}
