package com.test.cabmanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.CabHistory;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.enums.VehicleState;

public class CabServiceImpl implements CabService {
    List<Cab> cabList = new ArrayList<>();
    List<CabHistory> cabHistoryList = new ArrayList<>();

    @Override
    public void registerCabs(List<Cab> cabs) {
        cabList.addAll(cabs);

    }

    @Override
    public void updateCabState(Integer cabId, VehicleState vehicleState) {
        cabList.stream().filter(c -> cabId.equals(c.getId())).forEach(c -> {
            c.setState(vehicleState); 
            c.setLastModifiedTime(new Date()); 
            c.setCity(null);
        });
        CabHistory cabHistoryRecord = new CabHistory(cabId, vehicleState);
        cabHistoryList.add(cabHistoryRecord);
    }

    @Override
    public List<Cab> getAllCabs() {
        return cabList;
    }

    @Override
    public List<Cab> getCabsForCity(City city) {
        return cabList.stream().filter(c -> c.getCity()!= null && c.getCity().equals(city)).collect(Collectors.toList());
    }

    @Override
    public long getIdleTimeForCab(Integer cabId, Date startTime, Date endTime) {
        List<CabHistory> filteredCabHistory = cabHistoryList.stream().filter(c -> cabId.equals(c.getCabId()) && c.getModificationTime().after(startTime) && c.getModificationTime().before(endTime)).collect(Collectors.toList());
        if(filteredCabHistory == null || filteredCabHistory.isEmpty()) {
            return 0;
        }
        long idleMilliSeconds = 0;
        Date prevIdleTime = startTime;
        VehicleState lastState = null;
        for(CabHistory ch: filteredCabHistory) {
            if(ch.getVehicleState().equals(VehicleState.ON_TRIP)) {
                idleMilliSeconds += ch.getModificationTime().getTime() - prevIdleTime.getTime();
            }
            prevIdleTime = ch.getModificationTime();
            lastState = ch.getVehicleState();
        }
        if(VehicleState.IDLE.equals(lastState)) {
            idleMilliSeconds += endTime.getTime() - prevIdleTime.getTime();
        }
        return idleMilliSeconds;
    }

    @Override
    public List<CabHistory> getCabHistory(Integer cabId) {
        if(cabId == null) {
            return null;
        }
        return cabHistoryList.stream().filter(c -> cabId.equals(c.getCabId())).collect(Collectors.toList());
    }

    @Override
    public void updateCabCity(Integer cabId, City city) {
        cabList.stream().filter(c -> cabId.equals(c.getId())).forEach(c -> {
            c.setCity(city);
        });
    }

}
