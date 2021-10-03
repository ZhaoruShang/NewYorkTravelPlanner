package com.flag.travelplanner.poi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="pois")
public class POI implements Serializable {

    private static final long serialVersionUID = 263384195550410516L;
    @Id
    private long poiId;
    private String name;
    private double lat;
    private double lng;
    private String imageUrl;
    private String description;
    private double popularity;
    private double estimateVisitTime;

    public POI() { }
    public POI(long poiId, String name, double lat, double lng, String imageUrl, String description, double popularity, double estimateVisitTime) {
        this.poiId = poiId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.imageUrl = imageUrl;
        this.description = description;
        this.popularity = popularity;
        this.estimateVisitTime = estimateVisitTime;
    }

    public long getPoiId() {
        return poiId;
    }

    public void setPoiId(long poiId) {
        this.poiId = poiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getEstimateVisitTime() {
        return estimateVisitTime;
    }

    public void setEstimateVisitTime(double estimateVisitTime) {
        this.estimateVisitTime = estimateVisitTime;
    }
}

