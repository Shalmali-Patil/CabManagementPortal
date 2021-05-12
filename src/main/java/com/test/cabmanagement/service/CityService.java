package com.test.cabmanagement.service;

import java.util.List;

import com.test.cabmanagement.bean.City;

public interface CityService {
    public City addCity(String cityName);
    
    public boolean removeCity(String cityName);
    
    public List<City> getCityList();
    
    public City getCityById(Integer id);
}
