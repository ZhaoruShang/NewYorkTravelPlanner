package com.flag.travelplanner.route.service;

import com.flag.travelplanner.route.entity.Plan;
import com.flag.travelplanner.route.entity.Route;

import java.util.List;

public interface PlanService {
    Plan retrievePlanDetails(long id);
    List<Plan> retrieveAllPlansOfUser();
    Plan updatePlan(Plan plan);
    void deletePlan(long id);
    Plan savePlan(Plan plan);
    Plan generatePlan(Plan plan, double maxHour);
}
