package com.flag.travelplanner.map.entity;

import java.io.Serializable;

public class Place implements Serializable {
    private static final long serialVersionUID = 898327211220096325L;
    private String placeId;
    private String address;
    private double lat;
    private double lng;
    private String name;
    private float rating;
    private String type;

    public Place(String placeId, String address, double lat, double lng, String name, float rating, String type) {
        this.placeId = placeId;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.rating = rating;
        this.type = type;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
