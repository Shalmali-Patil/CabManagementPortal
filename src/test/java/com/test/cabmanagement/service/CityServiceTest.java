package com.test.cabmanagement.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.test.cabmanagement.bean.City;

class CityServiceTest {
    CityService cityService = new CityServiceImpl();

    @Test
    void testAddNewCity() {
        String cityName = "Pune";
        cityService.addCity(cityName);
        List<City> cityList = cityService.getCityList();
        assertTrue(cityList != null && !cityList.isEmpty());
        boolean cityFound = false;
        for(City city: cityList) {
            if(city.getName().equals(cityName)) {
                cityFound = true;
                break;
            }
        }
        assertTrue(cityFound);
    }

    @Test
    void testGetCityById() {
        String cityName = "Pune";
        City addedCity = cityService.addCity(cityName);
        City expectedCity = cityService.getCityById(addedCity.getId());
        assertTrue(expectedCity != null && cityName.equals(expectedCity.getName()));
    }
    
    @Test
    void testGetCityList() {
        String cityName = "Mumbai";
        cityService.addCity(cityName);
        List<City> cityList = cityService.getCityList();
        assertTrue(cityList != null && !cityList.isEmpty());
        assertTrue(cityList.size() == 1);
    }
}
