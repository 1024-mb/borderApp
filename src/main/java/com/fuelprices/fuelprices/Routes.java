package com.fuelprices.fuelprices;

public class Routes {
    private String Location;
    private String Distance;
    private String TimeDestination;

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getDistance() {
        return this.Distance;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public void setTimeDestination(String TimeDestination) {
        this.TimeDestination = TimeDestination;
    }

    public String getTimeDestination() {
        return this.TimeDestination;
    }


    public Routes(String Location, String Distance, String TimeDestination) {
        this.Location = Location;
        this.Distance = Distance;
        this.TimeDestination = TimeDestination;
    }
}
