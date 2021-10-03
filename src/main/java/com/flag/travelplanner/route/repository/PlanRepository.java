package com.flag.travelplanner.route.repository;

import com.flag.travelplanner.route.entity.Plan;
import com.flag.travelplanner.route.entity.Route;

import java.util.List;

public interface PlanRepository {
    long count();
    Plan save(Plan plan);
    Plan findById(long id);
    int update(Plan plan);
    int deleteById(long id);
    List<Plan> findPlansByUser(String username);
}
