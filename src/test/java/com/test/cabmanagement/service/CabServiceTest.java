package com.test.cabmanagement.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.enums.VehicleState;

class CabServiceTest {
    CabService cabService = new CabServiceImpl();

    @Test
    void testRegisterCabs() {
        List<Cab> cabs = new ArrayList<>();
        City city1 = new City("Pune");
        Cab cab1 = new Cab("1234", city1, VehicleState.IDLE);
        Cab cab2 = new Cab("9h3i1", city1, VehicleState.IDLE);
        cabs.add(cab1);
        cabs.add(cab2);
        cabService.registerCabs(cabs);
        
        List<Cab> actualList = cabService.getAllCabs();
        assertTrue(actualList != null && !actualList.isEmpty());
        assertTrue(actualList.size() == 2);
    }

    @Test
    void testUpdateCabState() {
        List<Cab> cabs = new ArrayList<>();
        String registrationNumber = "8812h";
        Cab cab1 = new Cab(registrationNumber, null, VehicleState.ON_TRIP);
        cabs.add(cab1);
        cabService.registerCabs(cabs);
        
        List<Cab> actualList = cabService.getAllCabs();
        Cab fetchedCab = actualList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber)).findAny().get();
        
        cabService.updateCabState(fetchedCab.getId(), VehicleState.IDLE);
        
        List<Cab> updatedList = cabService.getAllCabs();
        fetchedCab = updatedList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber)).findAny().get();
        
        assertTrue(fetchedCab != null && fetchedCab.getState().equals(VehicleState.IDLE));
    }
    
    @Test
    void testUpdateCabCity() {
        List<Cab> cabs = new ArrayList<>();
        City city1 = new City("Pune");
        String registrationNumber = "12nkl4";
        Cab cab1 = new Cab(registrationNumber, city1, VehicleState.ON_TRIP);
        cabs.add(cab1);
        cabService.registerCabs(cabs);
        
        List<Cab> actualList = cabService.getAllCabs();
        Cab fetchedCab = actualList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber)).findAny().get();
        City city2 = new City("Mumbai");
        cabService.updateCabCity(fetchedCab.getId(), city2);
        
        List<Cab> updatedList = cabService.getAllCabs();
        fetchedCab = updatedList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber)).findAny().get();
        
        assertTrue(fetchedCab != null && fetchedCab.getCity().getName().equals(city2.getName()));
    }
    
    @Test
    void testGetIdleTimeForCab() throws InterruptedException {
        Date startTime = new Date();
        List<Cab> cabs = new ArrayList<>();
        String registrationNumber1 = "123sdas";
        City city1 = new City("Pune");
        Cab cab1 = new Cab(registrationNumber1, city1, VehicleState.IDLE);
        cabs.add(cab1);
        
        String registrationNumber2 = "8872hdu";
        Cab cab2 = new Cab(registrationNumber2, city1, VehicleState.IDLE);
        cabs.add(cab2);
        List<Cab> actualList = cabService.registerCabs(cabs);
        
        Cab fetchedCab1 = actualList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber1)).findAny().get();
        Cab fetchedCab2 = actualList.stream().filter(c -> c.getRegistrationNumber().equals(registrationNumber2)).findAny().get();
        TimeUnit.SECONDS.sleep(10);
        cabService.updateCabState(fetchedCab1.getId(), VehicleState.ON_TRIP);
        
        TimeUnit.SECONDS.sleep(10);
        
        Date endTime = new Date();
        long idleTimeForCab1 = cabService.getIdleTimeForCab(fetchedCab1.getId(), startTime, endTime);
        System.out.println("idle 1:" + idleTimeForCab1);
        assertTrue(idleTimeForCab1 > 10000);
        
        long idleTimeForCab2 = cabService.getIdleTimeForCab(fetchedCab2.getId(), startTime, endTime);
        System.out.println("idle 2:" + idleTimeForCab2);
        assertTrue(idleTimeForCab2 > 20000);
        
    }
}
