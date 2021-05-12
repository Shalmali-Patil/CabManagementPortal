package com.test.cabmanagement.service;

import java.util.List;

import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.bean.Trip;

public interface BookingService {
    public Trip bookCab(City city);
    
    public List<City> getTopNCities(int n);
}
