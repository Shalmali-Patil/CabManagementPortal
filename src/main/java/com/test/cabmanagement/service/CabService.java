package com.test.cabmanagement.service;

import java.util.Date;
import java.util.List;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.CabHistory;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.enums.VehicleState;

public interface CabService {
    public void registerCabs(List<Cab> cabs);
    public void updateCabState(Integer cabId, VehicleState vehicleState);
    public List<Cab> getAllCabs();
    public List<Cab> getCabsForCity(City city);
    public long getIdleTimeForCab(Integer cabId, Date startTime, Date endTime);
    public List<CabHistory> getCabHistory(Integer cabId);
    public void updateCabCity(Integer cabId, City city);
}
