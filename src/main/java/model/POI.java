package main.java.model;

public class POI {

    private String locationName;
    private DataReading[] dataReadings;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setdataReadings(DataReading[] dataReadings) {
        this.dataReadings = dataReadings;
    }

}