package main.java.model;
import java.util.Date;

/**
 * Created by rishabhmahajani on 4/21/17.
 */
public class DataPoint {
    private String locationName;
    private Boolean accepted;
    private Date myDate;
    private Type pointType;
    private int dataValue;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }

    public void setPointType(Type pointType) {
        this.pointType = pointType;
    }

    public void setDataValue(int dataValue) {
        this.dataValue = dataValue;
    }
}
