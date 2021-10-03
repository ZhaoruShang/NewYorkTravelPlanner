package com.flag.travelplanner.route.controller;

import com.flag.travelplanner.route.entity.Plan;
import com.flag.travelplanner.route.entity.Route;
import com.flag.travelplanner.route.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping(value="")
    @ResponseBody
    public List<Plan> retrieveAllPlans() {
        return planService.retrieveAllPlansOfUser();
    }

    @GetMapping(value="/{plan_id}")
    @ResponseBody
    public Plan retrievePlan(@PathVariable("plan_id") long id) {
        return planService.retrievePlanDetails(id);
    }

    @PostMapping("")
    //@ResponseStatus(value= HttpStatus.CREATED)
    //@ResponseBody
    public ResponseEntity<Plan> savePlan(@RequestBody Plan plan) {
        Plan newPlan = planService.savePlan(plan);
        for (Route route : newPlan.getRoutes()) {
            route.setPlan(null);
        }
        if (newPlan == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(newPlan);
        }
    }

    /***
     *
     * @param id
     */
    @DeleteMapping(value="/{plan_id}")
    @ResponseStatus(value= HttpStatus.ACCEPTED)
    public void deletePlan(@PathVariable("plan_id") long id) {
        planService.deletePlan(id);
    }

    @PutMapping("")
    //@ResponseStatus(value= HttpStatus.ACCEPTED)
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan plan) {
        Plan newPlan = planService.updatePlan(plan);
        for (Route route : newPlan.getRoutes()) {
            route.setPlan(null);
        }
        if (newPlan == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(newPlan);
        }
    }

    @PostMapping(value="/generate/{max_hour}")
    @ResponseBody
    public Plan generatePlan(@RequestBody Plan plan, @PathVariable("max_hour") double maxHour) {
        return planService.generatePlan(plan, maxHour);

    }

}
