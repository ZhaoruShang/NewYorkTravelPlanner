package com.flag.travelplanner.search.repository;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository {
    List<POI> findPOIByKeyword(String keyword);
    List<POI> findNearByPOIs(double lat, double lng, double range);
}
