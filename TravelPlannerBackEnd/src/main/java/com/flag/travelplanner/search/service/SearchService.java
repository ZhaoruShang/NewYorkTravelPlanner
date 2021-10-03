package com.flag.travelplanner.search.service;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SearchService {
    List<POI> searchPOIByKeyword( String keyword);
    List<POI> searchNearbyPOIs(double lat, double lng, double range);
}
