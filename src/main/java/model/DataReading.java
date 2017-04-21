package main.java.model;
import java.util.Date;


class DataReading {

    private Type dataType;
    private int dataValue;
    private Date myDate;

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }

    public void setDataType(Type dataType) {
        this.dataType = dataType;
    }

    public void setDataValue(int dataValue) {
        this.dataValue = dataValue;
    }
}