package com.example.user3.weatherinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.Route;

/**
 * Created by user3 on 3/5/18.
 */

public class DirectionInfo {

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> geocodedWaypoints = null;
    @SerializedName("routes")
    @Expose
    private List<com.example.user3.weatherinfo.Route> routes = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<com.example.user3.weatherinfo.Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<com.example.user3.weatherinfo.Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectionInfo that = (DirectionInfo) o;

        if (geocodedWaypoints != null ? !geocodedWaypoints.equals(that.geocodedWaypoints) : that.geocodedWaypoints != null)
            return false;
        if (routes != null ? !routes.equals(that.routes) : that.routes != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = geocodedWaypoints != null ? geocodedWaypoints.hashCode() : 0;
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DirectionInfo{" +
                "geocodedWaypoints=" + geocodedWaypoints +
                ", routes=" + routes +
                ", status='" + status + '\'' +
                '}';
    }
}
