package com.test.cabmanagement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.service.CityService;
import com.test.cabmanagement.service.CityServiceImpl;

class CityServiceTest {
    CityService cityService;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        cityService = new CityServiceImpl();
    }

    @BeforeEach
    void setUp() throws Exception {
    }

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
        
        List<City> cityList = cityService.getCityList();
        assertTrue(cityList != null && !cityList.isEmpty());
        assertTrue(cityList.size() == 2);
    }
}
