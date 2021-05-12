package com.test.cabmanagement.bean;

import java.util.Date;

public class Trip {
    private Integer id;
    private Cab cab;
    private Date startTime;
    private Date endTime;
    private City city;
    private static Integer IDCounter = 1;
    
    public Cab getCab() {
        return cab;
    }
    public void setCab(Cab cab) {
        this.cab = cab;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public Integer getId() {
        return id;
    }
    public Trip(Cab cab, Date startTime, City city) {
        this.id = IDCounter++;
        this.cab = cab;
        this.startTime = startTime;
        this.city = city;
    }
    @Override
    public String toString() {
        return "Trip [id=" + id + ", cab=" + cab + ", startTime=" + startTime + ", endTime=" + endTime + ", city="
                + city + "]";
    }
    
}
