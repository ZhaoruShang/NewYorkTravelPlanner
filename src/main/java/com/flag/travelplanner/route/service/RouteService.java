package com.flag.travelplanner.route.service;

import com.flag.travelplanner.route.entity.POIRoute;
import com.flag.travelplanner.route.entity.Route;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public interface RouteService {

    Route retrieveRouteDetails(long id);
    void updateRoute(Route route);
    void deleteRoute(long id);
    Route saveRoute(Route route);
    void deleteAllRoutesFromPlan(long planId);
    List<Route> retrieveAllRoutesOfPlan(long planId);
    void deletePOIFromRoute(POIRoute poiRoute);
}

// RouteService interface to be implemented