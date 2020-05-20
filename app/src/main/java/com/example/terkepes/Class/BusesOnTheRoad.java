package com.example.terkepes.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusesOnTheRoad {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Line")
    @Expose
    private Integer line;
    @SerializedName("Bus")
    @Expose
    private Integer bus;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("Date")
    @Expose
    private String date;

    public BusesOnTheRoad(){}

    public BusesOnTheRoad(Integer id, Integer line, Integer bus, Double lon, Double lat, String date) {
        this.id = id;
        this.line = line;
        this.bus = bus;
        this.lon = lon;
        this.lat = lat;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getBus() { return bus; }

    public void setBus(Integer bus) { this.bus = bus; }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
