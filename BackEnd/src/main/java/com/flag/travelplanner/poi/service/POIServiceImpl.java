package com.flag.travelplanner.poi.service;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.poi.repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIServiceImpl implements POIService{

    @Autowired
    private POIRepository poiRepository;

    @Override
    public POI getPOIDetails(long id) {

        return poiRepository.findById(id);
    }

    @Override
    public List<POI> getPOIByCity(String city) {
        return null;
    }
}
