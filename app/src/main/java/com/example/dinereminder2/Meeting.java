package com.example.dinereminder2;

public class Meeting {

    private String meetPurpose;
    private String date;
    private String time;
    private String place;

    public Meeting() {
    }

    public Meeting(String meetPurpose, String date, String time, String place) {
        this.meetPurpose = meetPurpose;
        this.date = date;
        this.time = time;
        this.place = place;
    }

    public String getMeetPurpose() {
        return meetPurpose;
    }

    public void setMeetPurpose(String meetPurpose) {
        this.meetPurpose = meetPurpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
