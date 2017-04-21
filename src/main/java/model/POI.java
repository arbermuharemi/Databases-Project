package main.java.model;

public class POI {

    private String locationName;
    private dataReading[] dataReadings;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setdataReadings(dataReading[] dataReadings) {
        this.dataReadings = dataReadings;
    }

}