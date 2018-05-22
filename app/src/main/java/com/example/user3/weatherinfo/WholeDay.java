package com.example.user3.weatherinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user3 on 10/5/18.
 */

public class WholeDay {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<com.example.user3.weatherinfo.Forecast_list> list = null;



    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.example.user3.weatherinfo.Forecast_list> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.user3.weatherinfo.Forecast_list> list) {
        this.list = list;
    }


}
