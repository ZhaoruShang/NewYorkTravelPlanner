package com.flag.travelplanner.route.repository;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

@Repository
public class RouteRepositoryJdbc implements RouteRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from routes", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Route save(Route route) {

        String sqlRoute = "insert ignore into routes " +
                "(name, startAddress, endAddress, planId) " +
                "values ( ?, ?, ?, ?)";
        String getLastId = "select last_insert_id()";

        try {
            jdbcTemplate.update(sqlRoute,
                    route.getName(),
                    route.getStartAddress(),
                    route.getEndAddress(),
                    route.getPlan().getPlanId());
            long routeId = jdbcTemplate.queryForObject(getLastId, Long.class);
            route.setRouteId(routeId);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public Route findById(long id) {

        Route route = null;
        String sqlQuery = "select * from routes where routeId = ?";

        try {
            route = jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new int[]{BIGINT},
                    (rs, rowNum)-> new Route(rs.getInt("routeId"),
                            rs.getString("name"),
                            rs.getDate("createTime"),
                            rs.getString("startAddress"),
                            rs.getString("endAddress"),
                            rs.getDate("updateTime")));

        } catch(Exception e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public int update(Route route) {
        String sqlQuery ="update routes set name = ?, startAddress = ?, endAddress = ? where routeId = ?";
      /*  String deletePOIRoute = "delete from poi_route where routeId = ?";
        String sqlPOIRoute = "insert into poi_route (poiId, routeId) values(?, ?)";

        List<Object[]> poiRouteBatchArgs = new ArrayList<>();
        for (POI poi : route.getPoiList()) {
            Object [] obj = new Object[]{poi.getPoiId(), route.getRouteId()};
            poiRouteBatchArgs.add(obj);
        }
        */

        int row = 0;
        try {
            row = jdbcTemplate.update(sqlQuery, route.getName(),
                    route.getStartAddress(),
                    route.getEndAddress(),
                    route.getRouteId());
           // jdbcTemplate.update(deletePOIRoute, route.getRouteId());
           // row = jdbcTemplate.batchUpdate(sqlPOIRoute, poiRouteBatchArgs).length;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int deleteById(long id) {
        String routeQuery = "delete from routes where routeId = ? ";
        int row = 0;
        try {
            row = jdbcTemplate.update(routeQuery, id);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int deleteByPlan(long planId) {
        String routeQuery = "delete from routes where planId = ? ";
        int row = 0;
        try {
            row = jdbcTemplate.update(routeQuery, planId);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public List<Route> findRoutesByPlan(long planId) {
        String sqlQuery = "select * from routes where planId = ?";

        List<Route> routes = null;
        try {
            routes = jdbcTemplate.query(sqlQuery, new Object[]{planId}, new int[]{BIGINT},
                    (rs, rowNum)-> new Route(rs.getInt("routeId"),
                            rs.getString("name"),
                            rs.getDate("createTime"),
                            rs.getString("startAddress"),
                            rs.getString("endAddress"),
                            rs.getDate("updateTime")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return routes;
    }
}
