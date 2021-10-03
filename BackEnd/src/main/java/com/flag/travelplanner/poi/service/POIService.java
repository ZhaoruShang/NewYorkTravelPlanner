package com.flag.travelplanner.poi.service;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.stereotype.Service;

import java.util.List;


public interface POIService {
    POI getPOIDetails(long id);
    List<POI> getPOIByCity(String city);
}
