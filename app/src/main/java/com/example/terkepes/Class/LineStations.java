package com.example.terkepes.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineStations {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("lineId")
    @Expose
    private Integer lineId;
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("stationName")
    @Expose
    private String stationName;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public LineStations(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

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
}

