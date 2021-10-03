package com.flag.travelplanner.route.repository;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.entity.Plan;
import com.flag.travelplanner.route.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

@Repository
public class PlanRepositoryJdbc implements PlanRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from plans", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Plan save(Plan plan) {
        String sqlPlan = "insert ignore into plans " +
                "(name, owner) " +
                "values ( ?, ?)";
        String getLastId = "select last_insert_id()";
        try {
            jdbcTemplate.update(sqlPlan,
                    plan.getName(),
                    plan.getOwner().getUsername());
            long planId = jdbcTemplate.queryForObject(getLastId, Long.class);
            plan.setPlanId(planId);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return plan;
    }

    @Override
    public Plan findById(long id) {
        Plan plan = null;
        String sqlQuery = "select * from plans where planId = ?";

        try {
            plan = jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new int[]{BIGINT},
                    (rs, rowNum)-> new Plan(rs.getInt("planId"),
                            rs.getString("name"))
                    );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return plan;
    }

    @Override
    public int update(Plan plan) {
        String sqlQuery ="update plans set name = ?, owner  = ? where planId = ?";

        int row = 0;
        try {
            row = jdbcTemplate.update(sqlQuery, plan.getName(),
                    plan.getOwner().getUsername(),
                    plan.getPlanId()
                    );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int deleteById(long id) {
        String routeQuery = "delete from plans where planId = ? ";
        int row = 0;
        try {
            row = jdbcTemplate.update(routeQuery, id);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public List<Plan> findPlansByUser(String username) {
        String sqlQuery = "select * from plans where owner = ?";

        List<Plan> plans = null;
        try {
            plans = jdbcTemplate.query(sqlQuery, new Object[]{username}, new int[]{VARCHAR},
                    (rs, rowNum)-> new Plan(rs.getInt("planId"),
                            rs.getString("name")
                    ));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return plans;
    }
}
