package com.test.cabmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.bean.Trip;
import com.test.cabmanagement.enums.VehicleState;

class BookingServiceTEst {
    CityService cityService = new CityServiceImpl();
    CabService cabService = new CabServiceImpl();
    BookingService bookingService = new BookingServiceImpl(cabService);

    @Test
    void testBookCab() {
        City city1 = cityService.addCity("Pune");
        List<Cab> cabs = new ArrayList<>();
        String registrationNumber1 = "123";
        Cab cab1 = new Cab(registrationNumber1, city1, VehicleState.IDLE);
        City city2 = cityService.addCity("Mumbai");
        String registrationNumber2 = "891";
        Cab cab2 = new Cab(registrationNumber2, city2, VehicleState.IDLE);
        cabs.add(cab1);
        cabs.add(cab2);
        cabService.registerCabs(cabs);
        Trip trip = bookingService.bookCab(city1);
        
        assertTrue(trip != null && trip.getCab().getRegistrationNumber().equals(registrationNumber1) && trip.getCab().getState().equals(VehicleState.ON_TRIP));
    }

    @Test
    void testGetTopNCities() {
        City city1 = cityService.addCity("Pune");
        List<Cab> cabs = new ArrayList<>();
        String registrationNumber1 = "82791";
        Cab cab1 = new Cab(registrationNumber1, city1, VehicleState.IDLE);
        cabs.add(cab1);
        cabService.registerCabs(cabs);
        bookingService.bookCab(city1);
        
        int n = 1;
        List<City> topCities = bookingService.getTopNCities(n);
        
        assertTrue(topCities != null && !topCities.isEmpty() && topCities.size() == n && topCities.get(0).equals(city1));
    }

    
}
