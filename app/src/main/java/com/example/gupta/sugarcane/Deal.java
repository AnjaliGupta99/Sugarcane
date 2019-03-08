package com.example.gupta.sugarcane;

public class Deal {

    private String name;
    private String contact;
    private String address;
    private String reap;
    private String sow;
    private String uid;
    private String dateid;
    private String landarea;

    public Deal(){

    }

    public Deal(String name, String contact, String address, String reap, String sow, String uid, String dateid, String landarea) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.reap = reap;
        this.sow = sow;
        this.uid = uid;
        this.dateid = dateid;
        this.landarea = landarea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReap() {
        return reap;
    }

    public void setReap(String reap) {
        this.reap = reap;
    }

    public String getSow() {
        return sow;
    }

    public void setSow(String sow) {
        this.sow = sow;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDateid() {
        return dateid;
    }

    public void setDateid(String dateid) {
        this.dateid = dateid;
    }

    public String getLandarea() {
        return landarea;
    }

    public void setLandarea(String landarea) {
        this.landarea = landarea;
    }
}
