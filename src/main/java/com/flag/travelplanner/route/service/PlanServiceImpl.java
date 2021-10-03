package com.flag.travelplanner.route.service;

import com.flag.travelplanner.map.service.MapService;
import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.algos.MinimizeDaysSimpleAlgos;
import com.flag.travelplanner.route.algos.RouteAlgorithm;
import com.flag.travelplanner.route.algos.SimpleRoute;
import com.flag.travelplanner.route.entity.Plan;
import com.flag.travelplanner.route.entity.Route;
import com.flag.travelplanner.route.repository.PlanRepository;
import com.flag.travelplanner.route.repository.RouteRepository;
import com.flag.travelplanner.user.entity.User;
import com.flag.travelplanner.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService{

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapService mapService;

    @Override
    @Transactional
    public Plan retrievePlanDetails(long id) {
        Plan plan = planRepository.findById(id);
        List<Route> routes = routeService.retrieveAllRoutesOfPlan(id);
        plan.setRoutes(routes);
        return plan;
    }

    @Override
    @Transactional
    public List<Plan> retrieveAllPlansOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Plan> plans = planRepository.findPlansByUser(username);
        for (Plan plan : plans) {
            List<Route> routes = routeRepository.findRoutesByPlan(plan.getPlanId());
            plan.setRoutes(routes);
        }
        return plans;
    }

    @Override
    @Transactional
    public Plan updatePlan(Plan plan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUserName(username);
        plan.setOwner(user);
        planRepository.update(plan);
        for (int i = 0;  i < plan.getRoutes().size(); i++) {
            Route route = plan.getRoutes().get(i);

            if (plan.getRoutes().get(i).getRouteId()<=0) {
                route.setPlan(plan);
                plan.getRoutes().set(i,routeService.saveRoute(route));
            } else {
                routeService.updateRoute(route);
            }
        }
        return plan;
    }

    @Override
    @Transactional
    public void deletePlan(long id) {
        planRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Plan savePlan(Plan plan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUserName(username);
        plan.setOwner(user);
        plan = planRepository.save(plan);
        for (Route route : plan.getRoutes()) {
            route.setPlan(plan);
            routeService.saveRoute(route);
        }
        return plan;
    }

    @Override
    public Plan generatePlan(Plan plan, double maxHour) {
        if (plan.getRoutes().isEmpty()) return plan;

        Route route = new Route();
        route.setName(plan.getRoutes().get(0).getName());
        route.setStartAddress(plan.getRoutes().get(0).getStartAddress());
        route.setEndAddress(plan.getRoutes().get(0).getEndAddress());
        route.setPoiList(new ArrayList<>());
        for (Route rt : plan.getRoutes()) {
            if (rt.getPoiList() != null) {
                for (POI poi : rt.getPoiList()) {
                    route.getPoiList().add(poi);
                }
            }
        }
        double [][] timeMatrix =  mapService.buildDistanceMatrix(route);
        List<List<Integer>> routePlan;
        RouteAlgorithm routeAlgorithm;
        if (!route.getStartAddress().equals(route.getEndAddress()) && !route.getEndAddress().trim().isEmpty()) {
            routeAlgorithm = new SimpleRoute();
        } else {
            routeAlgorithm = new MinimizeDaysSimpleAlgos();
        }
        routePlan = routeAlgorithm.generateRoute(route, timeMatrix, maxHour);

        List<Route> routes = new LinkedList<>();
        int i = 0;
        for (List<Integer> path : routePlan) {
            Route newRoute = new Route();
            newRoute.setStartAddress(route.getStartAddress());
            newRoute.setName("Route " + i);
            if (route.getEndAddress().isEmpty())
                newRoute.setEndAddress(route.getStartAddress());
            else
                newRoute.setEndAddress(route.getEndAddress());
            newRoute.setPoiList(new LinkedList<>());
            i++;
            for (int id : path) {
                if (id > 0) {
                    POI poi = route.getPoiList().get(id-1);
                    newRoute.getPoiList().add(poi);
                }
            }
            routes.add(newRoute);
        }

        plan.setRoutes(routes);
        return plan;
    }
}
