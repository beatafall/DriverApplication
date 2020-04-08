package com.example.terkepes.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusLineDriver {

    @SerializedName("vonalbuszsoforId")
    @Expose
    private int vonalbuszsoforId;
    @SerializedName("buszId")
    @Expose
    private String buszId;
    @SerializedName("vonalId")
    @Expose
    private String vonalId;
    @SerializedName("soforId")
    @Expose
    private String soforId;
    @SerializedName("datum")
    @Expose
    private String datum;
    @SerializedName("Busz")
    @Expose
    private Object busz;
    @SerializedName("Sofor")
    @Expose
    private Object sofor;
    @SerializedName("Vonal")
    @Expose
    private Object vonal;

    private List<BusLineDriver> results = null;

    public List<BusLineDriver> getResults() {
        return results;
    }

    public Integer getVonalbuszsoforId() {
        return vonalbuszsoforId;
    }

    public void setVonalbuszsoforId(Integer vonalbuszsoforId) {
        this.vonalbuszsoforId = vonalbuszsoforId;
    }

    public String getBuszId() {
        return buszId;
    }

    public void setBuszId(String buszId) {
        this.buszId = buszId;
    }

    public String getVonalId() {
        return vonalId;
    }

    public void setVonalId(String vonalId) {
        this.vonalId = vonalId;
    }

    public String getSoforId() {
        return soforId;
    }

    public void setSoforId(String soforId) {
        this.soforId = soforId;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Object getBusz() {
        return busz;
    }

    public void setBusz(Object busz) {
        this.busz = busz;
    }

    public Object getSofor() {
        return sofor;
    }

    public void setSofor(Object sofor) {
        this.sofor = sofor;
    }

    public Object getVonal() {
        return vonal;
    }

    public void setVonal(Object vonal) {
        this.vonal = vonal;
    }

}

