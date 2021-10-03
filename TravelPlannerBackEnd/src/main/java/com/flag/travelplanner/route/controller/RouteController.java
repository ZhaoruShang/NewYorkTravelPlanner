package com.flag.travelplanner.route.controller;

import com.flag.travelplanner.route.entity.Route;
import com.flag.travelplanner.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping(value="/{route_id}")
    @ResponseBody
    public Route retrieveRoute(@PathVariable("route_id") long id) {
        return routeService.retrieveRouteDetails(id);
    }

    @PostMapping("")
    @ResponseStatus(value= HttpStatus.CREATED)
    @ResponseBody
    public Route saveRoute(@RequestBody Route route) {
        return routeService.saveRoute(route);
    }

    @DeleteMapping(value="/{route_id}")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public void deleteRoute(@PathVariable("route_id") long id) {
        routeService.deleteRoute(id);
    }

    @PutMapping("")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public void updateRoute(@RequestBody Route route) {
         routeService.updateRoute(route);
    }

}
