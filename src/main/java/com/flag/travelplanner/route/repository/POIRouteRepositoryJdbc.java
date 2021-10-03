package com.flag.travelplanner.route.repository;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.entity.POIRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;

@Repository
public class POIRouteRepositoryJdbc implements POIRouteRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from poi_route", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int save(POIRoute poiRoute) {
        String sqlPOIRoute = "insert into poi_route (poiId, routeId) values(?, ?, ?)";
        int row = 0;
        try {
            row = jdbcTemplate.update(sqlPOIRoute,
                    poiRoute.getPoiId(),
                    poiRoute.getRouteId(),
                    poiRoute.getSeqNo()
                    );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int save(List<POIRoute> poiRouteList) {
        String sqlPOIRoute = "insert into poi_route (poiId, routeId, seqNo) values(?, ?, ?)";
        List<Object[]> poiRouteBatchArgs = new ArrayList<>();
        for (POIRoute poiRoute : poiRouteList) {
            Object [] obj = new Object[]{poiRoute.getPoiId(), poiRoute.getRouteId(), poiRoute.getSeqNo()};
            poiRouteBatchArgs.add(obj);
        }
        int row = 0;
        try {
            row = jdbcTemplate.batchUpdate(sqlPOIRoute, poiRouteBatchArgs).length;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int delete(POIRoute poiRoute) {
        String deletePOIRoute = "delete from poi_route where poiId = ? and routeId = ?";
        int row = 0;
        try{
            row = jdbcTemplate.update(deletePOIRoute, poiRoute.getPoiId(), poiRoute.getRouteId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int deleteByPOI(long poiId) {
        String deletePOIRoute = "delete from poi_route where poiId = ?";
        int row = 0;
        try{
            row = jdbcTemplate.update(deletePOIRoute, poiId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public int deleteByRoute(long routeId) {
        String deletePOIRoute = "delete from poi_route where routeId = ?";
        int row = 0;
        try{
            row = jdbcTemplate.update(deletePOIRoute, routeId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public List<POI> findByRoute(long routeId) {
        String poiQuery = "select pois.poiId, name, ST_X(geoLocation) as lat, ST_Y(geoLocation) as lng, " +
                " imageUrl, description, popularity, estimateVisitTime  from pois " +
                "inner join poi_route on poi_route.poiId=pois.poiId where poi_route.routeId = ? " +
                "order by poi_route.seqNo";

        List<POI> poiList = null;
        try {
            poiList = jdbcTemplate.query(poiQuery, new Object[]{routeId}, new int[]{BIGINT},
                    (rs, rowNum)-> new POI(rs.getInt("poiId"),
                            rs.getString("name"),
                            rs.getDouble("lat"),
                            rs.getDouble("lng"),
                            rs.getString("imageUrl"),
                            rs.getString("description"),
                            rs.getDouble("popularity"),
                            rs.getDouble("estimateVisitTime")));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return poiList;

    }

}
