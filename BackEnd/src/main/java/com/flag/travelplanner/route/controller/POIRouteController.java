package com.flag.travelplanner.route.controller;

import com.flag.travelplanner.route.entity.POIRoute;
import com.flag.travelplanner.route.entity.Route;
import com.flag.travelplanner.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/routes/pois")
public class POIRouteController {

    @Autowired
    private RouteService routeService;

    @DeleteMapping(value="")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public void deletePOIFromRoute(@RequestBody POIRoute poiRoute) {
        routeService.deletePOIFromRoute(poiRoute);
    }

}
