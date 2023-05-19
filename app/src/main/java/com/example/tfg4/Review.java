package com.example.tfg4;

public class Review {
    private String reserveID;
    private String text;
    private float stars;
    private String DateHour;

    public Review() {
    }

    public Review(String reserveID, String text, float stars, String dateHour) {
        this.reserveID = reserveID;
        this.text = text;
        this.stars = stars;
        this.DateHour = dateHour;
    }

    public String getReserveID() {
        return reserveID;
    }

    public void setReserveID(String reserveID) {
        this.reserveID = reserveID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getDateHour() {
        return DateHour;
    }

    public void setDateHour(String dateHour) {
        DateHour = dateHour;
    }
}
