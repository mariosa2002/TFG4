package com.example.tfg4;

public class Reserve {
    private String scName;
    private String scAddress;
    private String facilityName;
    private String userID;
    private String Date;
    private String Hour;
    private String DateHour;

    public Reserve() {
    }

    public Reserve(String scName, String scAddress, String facilityName, String userID, String date, String hour, String dateHour) {
        this.scName = scName;
        this.scAddress = scAddress;
        this.facilityName = facilityName;
        this.userID = userID;
        Date = date;
        Hour = hour;
        DateHour = dateHour;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScAddress() {
        return scAddress;
    }

    public void setScAddress(String scAddress) {
        this.scAddress = scAddress;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getDateHour() {
        return DateHour;
    }

    public void setDateHour(String dateHour) {
        DateHour = dateHour;
    }
}
