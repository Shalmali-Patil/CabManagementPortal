package com.test.cabmanagement.bean;

import java.util.Date;

import com.test.cabmanagement.enums.VehicleState;

public class CabHistory {
    private Integer cabId;
    private VehicleState vehicleState;
    private Date modificationTime;
    public Integer getCabId() {
        return cabId;
    }
    public VehicleState getVehicleState() {
        return vehicleState;
    }
    public Date getModificationTime() {
        return modificationTime;
    }
    
    public CabHistory(Integer cabId, VehicleState vehicleState) {
        this.cabId = cabId;
        this.vehicleState = vehicleState;
        this.modificationTime = new Date();
    }
    
    @Override
    public String toString() {
        return "CabHistory [cabId=" + cabId + ", vehicleState=" + vehicleState + ", modificationTime="
                + modificationTime + "]";
    }
    
    
}
