package com.example.gupta.sugarcane;

public class UserProfile {
    public String uid;
    public String name;
    public String contact;
    public String address;
    public String sow;
    public String reap;
    public String dateid;
    public String timeaccepted;
    public String status;

    public String landarea;


    public UserProfile(String uid, String name, String contact, String address, String sow, String reap, String dateid, String landarea) {
        this.uid = uid;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.sow = sow;
        this.reap = reap;
        this.dateid = dateid;
        this.landarea = landarea;
    }

    public UserProfile(String uid, String name, String contact, String address, String landarea, String sow, String reap, String dateid, String timeaccepted, String status) {
        this.uid = uid;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.landarea = landarea;
        this.sow = sow;
        this.reap = reap;
        this.dateid = dateid;
        this.timeaccepted = timeaccepted;
        this.status = status;
    }

}
