package com.flag.travelplanner.route.repository;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.entity.POIRoute;

import java.util.List;

public interface POIRouteRepository {

    long count();
    int save(POIRoute poiRoute);
    int save(List<POIRoute> poiRouteList);
    int delete(POIRoute poiRoute);
    int deleteByPOI(long poiId);
    int deleteByRoute(long routeId);
    List<POI> findByRoute(long routeId);

}
