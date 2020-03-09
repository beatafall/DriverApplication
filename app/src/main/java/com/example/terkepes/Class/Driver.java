package com.example.terkepes.Class;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("Id")
    public String driverId;

    @SerializedName("DriverName")
    public String driverName;

    @SerializedName("DriverPassword")
    public String driverPassword;

    public Driver(String driverId, String driverName, String driverPassword) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverPassword = driverPassword;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
