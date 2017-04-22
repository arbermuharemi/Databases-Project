package main.java.model;
import java.sql.Timestamp;

/**
 * Created by rishabhmahajani on 4/21/17.
 */
public class DataPoint {
    private String locationName;
    private Boolean accepted;
    private Timestamp myDate;
    private Type pointType;
    private int dataValue;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setMyDate(Timestamp myDate) {
        this.myDate = myDate;
    }

    public void setPointType(Type pointType) {
        this.pointType = pointType;
    }

    public void setDataValue(int dataValue) {
        this.dataValue = dataValue;
    }

    public String getLocationName() {
        return locationName;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Timestamp getMyDate() {
        return myDate;
    }

    public String getPointTypeString() {
        return pointType.name();
    }

    public int getDataValue () {
        return dataValue;
    }
}
