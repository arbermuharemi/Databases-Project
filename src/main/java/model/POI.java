package main.java.model;

import java.sql.Timestamp;

public class POI {

    private String locationName;
    private String city;
    private String state;
    private String zip;
    private Boolean flagged;
    private Timestamp dateFlagged;
    private double moldMin;
    private double moldAvg;
    private double moldMax;
    private double aqMin;
    private double aqAvg;
    private double aqMax;
    private int numPoints;

    public String getFlaggedString() {
        return flagged ? "yes" : "no";
    }

    public double getAqAvg () {
        return aqAvg;
    }

    public double getAqMax () {
        return aqMax;
    }

    public double getAqMin () {
        return aqMin;
    }

    public double getMoldAvg () {
        return moldAvg;
    }

    public double getMoldMax () {
        return moldMax;
    }

    public double getMoldMin () {
        return moldMin;
    }

    public int getNumPoints () {
        return numPoints;
    }

    public void setAqAvg (double aqAvg) {
        this.aqAvg = aqAvg;
    }

    public void setAqMax (double aqMax) {
        this.aqMax = aqMax;
    }

    public void setAqMin (double aqMin) {
        this.aqMin = aqMin;
    }

    public void setMoldAvg (double moldAvg) {
        this.moldAvg = moldAvg;
    }

    public void setMoldMax (double moldMax) {
        this.moldMax = moldMax;
    }

    public void setMoldMin (double moldMin) {
        this.moldMin = moldMin;
    }

    public void setNumPoints (int numPoints) {
        this.numPoints = numPoints;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setState (String state) {
        this.state = state;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public void setDateFlagged (Timestamp dateFlagged) {
        this.dateFlagged = dateFlagged;
    }

    public void setFlagged (Boolean flagged) {
        this.flagged = flagged;
    }

    public void setZip (String zip) {
        this.zip = zip;
    }

    public Timestamp getDateFlagged () {
        return dateFlagged;
    }

    public String getState () {
        return state;
    }

    public String getCity () {
        return city;
    }

    public Boolean getFlagged () {
        return flagged;
    }

    public String getLocationName () {
        return locationName;
    }

    public String getZip () {
        return zip;
    }

}