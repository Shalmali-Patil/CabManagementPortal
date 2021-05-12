package com.test.cabmanagement.bean;

import java.util.Date;

import com.test.cabmanagement.enums.VehicleState;

public class Cab {
    private Integer id;
    private String registrationNumber;
    private City city;
    private VehicleState state;
    private Date lastModifiedTime;
    private static Integer IDCounter = 1;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public VehicleState getState() {
        return state;
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getId() {
        return id;
    }

    public Cab(String registrationNumber, City city, VehicleState state) {
        this.id = IDCounter++;
        this.registrationNumber = registrationNumber;
        this.city = city;
        this.state = state;
        this.lastModifiedTime = new Date();
    }

    @Override
    public String toString() {
        return "Cab [id=" + id + ", registrationNumber=" + registrationNumber + ", city=" + city + ", state="
                + state + ", lastModifiedTime=" + lastModifiedTime + "]";
    }

    
}
