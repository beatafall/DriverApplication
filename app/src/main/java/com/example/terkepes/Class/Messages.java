package com.example.terkepes.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("jelzesId")
    @Expose
    public String messageTypeId;

    @SerializedName("soforId")
    @Expose
    public String driverId;

    @SerializedName("vonalId")
    @Expose
    public String line;

    @SerializedName("buszId")
    @Expose
    public String bus;

    @SerializedName("datum")
    @Expose
    public String date;

    @SerializedName("lon")
    @Expose
    public String lon;

    @SerializedName("lat")
    @Expose
    public String lat;

    public Messages(){}

    public Messages(String messageTypeId, String driverId, String line, String bus, String date, String lon, String lat) {
        this.messageTypeId = messageTypeId;
        this.driverId = driverId;
        this.line = line;
        this.bus = bus;
        this.date = date;
        this.lon = lon;
        this.lat = lat;
    }


    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeName) {
        this.messageTypeId = messageTypeName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverName) {
        this.driverId = driverName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "{" +
                ", MessageTypeId='" + messageTypeId + '\'' +
                ", DriverId=" + driverId +
                ", Line=" + line +
                ", Bus=" + bus +
                ", MessageDate=" + date +
                ", MessageLon=" + lon +
                ", MessageLat=" + lat +
                '}';
    }
}
