package com.flag.travelplanner.route.algos;

import com.flag.travelplanner.route.entity.Route;

import java.util.List;

public interface RouteAlgorithm {
    List<List<Integer>> generateRoute(Route route, double[][] timeMatrix, double maxAllowableHours);
}
