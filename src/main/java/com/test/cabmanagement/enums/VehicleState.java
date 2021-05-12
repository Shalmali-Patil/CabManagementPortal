package com.test.cabmanagement.enums;

public enum VehicleState {
    IDLE(1), ON_TRIP(2);
    
    private int id;
    
    private VehicleState(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public static VehicleState getStateById(int id) {
        for(VehicleState vs: VehicleState.values()) {
            if(vs.getId() == id) {
                return vs;
            }
        }
        return null;
    }
}
