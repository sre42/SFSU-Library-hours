package com.omarasifshaikh.libhours.libraryhours.model;

/**
 * Created by Omar on 4/10/2015.
 */
public class LibraryEntry {
    private int Id;
    private String name;
    private String mon_thurs;
    private String friday;
    private String saturday;
    private String sunday;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMon_thurs() {
        return mon_thurs;
    }

    public void setMon_thurs(String mon_thurs) {
        this.mon_thurs = mon_thurs;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
}
