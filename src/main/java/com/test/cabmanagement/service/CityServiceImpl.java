package com.test.cabmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.cabmanagement.bean.City;

public class CityServiceImpl implements CityService {
    private List<City> cityList = new ArrayList<>();

    @Override
    public City addCity(String cityName) {
        // check if city name already exists
        Optional<City> optional = cityList.stream().filter(c-> cityName.equals(c.getName())).findFirst();
        if(optional.isPresent()) {
            return optional.get();
        }
        
        City city = new City(cityName);
        cityList.add(city);
        return city;
    }

    @Override
    public boolean removeCity(String cityName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<City> getCityList() {
        return cityList;
    }

    @Override
    public City getCityById(Integer id) {
        if(id == null) {
            return null;
        }
        Optional<City> city = cityList.stream().filter(c-> id.equals(c.getId())).findFirst();
        if(city.isPresent()) {
            return city.get();
        }
        return null;
    }

}
