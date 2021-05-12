package com.test.cabmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.test.cabmanagement.bean.Cab;
import com.test.cabmanagement.bean.CabHistory;
import com.test.cabmanagement.bean.City;
import com.test.cabmanagement.bean.Trip;
import com.test.cabmanagement.enums.VehicleState;
import com.test.cabmanagement.service.BookingService;
import com.test.cabmanagement.service.BookingServiceImpl;
import com.test.cabmanagement.service.CabService;
import com.test.cabmanagement.service.CabServiceImpl;
import com.test.cabmanagement.service.CityService;
import com.test.cabmanagement.service.CityServiceImpl;

public class CabManagementPortal {

    public static void main(String[] args) throws InterruptedException {
        CabService cabService = new CabServiceImpl();
        CityService cityService = new CityServiceImpl();
        BookingService bookingService = new BookingServiceImpl(cabService);

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(System.lineSeparator());
        System.out.println("Welcome to your Cab Management Portal.");
        boolean exit = false;
        int option = 0;
        do {
            System.out.println(
                    "Menu\n1. Get City list\n2. Onboard new city\n3. Get all cabs. \n4. Register cabs\n5. Change cab state (1 for IDLE, 2 for ON_TRIP)\n6. Change cab city\n7. Book cab\n8. Get idle time for cab\n9. History of a cab\n10. Top cities with high demand\n11. Time when demand is highest\n12. Exit");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println(cityService.getCityList());
                    break;
                case 2:
                    System.out.print("Enter city name:");
                    String cityName = sc.next();
                    City addCity = cityService.addCity(cityName);
                    if(addCity != null) {
                        System.out.println(cityName + " onboarded");
                    }
                    break;
                case 3:
                    System.out.println("Cab list:" + cabService.getAllCabs());
                    break;
                case 4:
                    List<Cab> cabList = new ArrayList<>();
                    System.out.println("Enter no. of cars to onboard:");
                    int count = sc.nextInt();
                    for(int i=0;i<count;i++) {
                        int cabNo = i+1;
                        System.out.print("Enter cab registration number for cab #:" + cabNo);
                        String regNo = sc.next();
                        System.out.println("Enter City ID for cab #:" + cabNo);
                        Integer cityId = sc.nextInt();
                        System.out.println("Enter cab state (1 for IDLE, 2 for ON_TRIP) for cab #" + cabNo);
                        int stateId = sc.nextInt();
                        VehicleState state = VehicleState.getStateById(stateId);
                        City city = null;
                        if(VehicleState.IDLE.equals(state)) {
                            city = cityService.getCityById(cityId);    
                        }
                        Cab cab = new Cab(regNo, city, state);
                        cabList.add(cab);
                    }
                    cabService.registerCabs(cabList);
                    break;
                case 5:
                    System.out.println("Enter cab ID to update: ");
                    int cabId = sc.nextInt();
                    System.out.println("Enter cab state (1 for IDLE, 2 for ON_TRIP) for cab #");
                    int stateId = sc.nextInt();
                    VehicleState state = VehicleState.getStateById(stateId);
                    cabService.updateCabState(cabId, state);
                    break;
                case 6:
                    System.out.println("Enter cab ID to update: ");
                    cabId = sc.nextInt();
                    System.out.println("Enter city ID");
                    Integer cityId = sc.nextInt();
                    City city = cityService.getCityById(cityId);
                    if(city == null) {
                        System.out.println("City Id does not exist.");
                        break;
                    }
                    cabService.updateCabCity(cabId, city);
                    break;
                case 7:
                    System.out.println("Enter City ID for booking cab: ");
                    cityId = sc.nextInt();
                    city = cityService.getCityById(cityId);
                    if(city == null) {
                        System.out.println("City Id does not exist.");
                        break;
                    }
                    Trip trip = bookingService.bookCab(city);
                    System.out.println("Cab booked. Trip details:" + trip);
                    break;
                case 8:
                    System.out.println("Enter Cab ID: ");
                    int cabIdToFetch = sc.nextInt();
                    System.out.println("Enter start time in dd-MM-yyyy: ");
                    String startStr = sc.next();
                    System.out.println("Enter end time dd-MM-yyyy: ");
                    String endStr = sc.next();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date startTime = null;
                    Date endTime = null;
                    try {
                        startTime = sdf.parse(startStr);
                        endTime = sdf.parse(endStr);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    long idleTime = cabService.getIdleTimeForCab(cabIdToFetch, startTime, endTime);
                    System.out.println("Total idle time in ms:" + idleTime);
                    break;
                case 9:
                    System.out.println("Enter Cab ID: ");
                    cabIdToFetch = sc.nextInt();
                    List<CabHistory> cabHistory = cabService.getCabHistory(cabIdToFetch);
                    System.out.println("Cab history:" + cabHistory);
                    break;
                case 10:
                    System.out.println("Enter no. of top cities to fetch?: ");
                    int n = sc.nextInt();
                    System.out.println("Top cities:" + bookingService.getTopNCities(n));
                    break;
                case 12:
                    exit = true;
                    break;
            }

        } while (!exit);

        sc.close();
    }

}
