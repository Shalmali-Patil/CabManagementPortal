package com.test.cabmanagement.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.bean.Trip;
import com.test.cabmanagement.enums.BookingStrategy;
import com.test.cabmanagement.enums.VehicleState;

public class BookingServiceImpl implements BookingService {
    private List<Trip> trips = new ArrayList<>();
    private CabService cabService;
    public static final BookingStrategy strategy = BookingStrategy.MOST_IDLE_TIME;
    
    public BookingServiceImpl(CabService cabService) {
        this.cabService = cabService;
    }

    @Override
    public Trip bookCab(City city) {
        if(city == null) {
            return null;
        }
        
        Trip trip = null;
        List<Cab> cabList = cabService.getCabsForCity(city);
        if(cabList == null || cabList.isEmpty()) {
            return null;
        }
        
        List<Cab> idleCabs = cabList.stream().filter(c -> c.getState() != null && c.getState().equals(VehicleState.IDLE)).collect(Collectors.toList());
        Cab cabToBook = null;
        switch(strategy) {
            case MOST_IDLE_TIME:
            default:
                Optional<Cab> optional = idleCabs.stream().max(new Comparator<Cab>() {

                    @Override
                    public int compare(Cab o1, Cab o2) {
                        return o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime());
                    }
                    
                });
                if(optional.isPresent()) {
                    cabToBook = optional.get();
                }
        }
        if(cabToBook != null) {
            cabService.updateCabState(cabToBook.getId(), VehicleState.ON_TRIP);
            trip = new Trip(cabToBook, new Date(), city);
            trips.add(trip);
        }
        return trip;
    }

    @Override
    public List<City> getTopNCities(int n) {
        Map<City, List<Trip>> cityToTripMap = trips.stream().collect(Collectors.groupingBy(Trip::getCity));
        if(cityToTripMap != null && !cityToTripMap.isEmpty()) {
            PriorityQueue<City> topN = new PriorityQueue<City>(n, new Comparator<City>() {
               @Override
                public int compare(City o1, City o2) {
                   List<Trip> l1 = cityToTripMap.get(o1);
                   List<Trip> l2 = cityToTripMap.get(o2);
                   int l1Count = 0, l2Count = 0;
                   if(l1 != null && !l1.isEmpty()) {
                       l1Count = l1.size();
                   }
                   if(l2 != null && !l2.isEmpty()) {
                       l2Count = l2.size();
                   }
                   return Integer.compare(l1Count, l2Count);
                } 
            });
            
            for(Map.Entry<City, List<Trip>> entry: cityToTripMap.entrySet()) {
                if(topN.size() < n) {
                    topN.add(entry.getKey());
                } else if (cityToTripMap.get(topN.peek()).size() < entry.getValue().size()) {
                    // if queue if full, remove min value from the queue and replace with new value from the map
                    topN.poll();
                    topN.add(entry.getKey());
                }
            }
            
            return Arrays.asList(topN.toArray(new City[0]));
        }
        return null;
    }

}
