package com.flag.travelplanner.poi.repository;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;

@Repository
public class POIRepositoryJdbc implements POIRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from pois", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int save(List<POI> poiList) {

        String sqlQuery = "insert ignore into pois ( name, geoLocation, imageUrl, description, popularity, estimateVisitTime) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> poiBatchArgs = new ArrayList<>();
        for (POI poi : poiList) {
            String point = "POINT("+poi.getLat()+" "+poi.getLng()+")";
            Object [] obj = new Object[]{ poi.getName(),point, poi.getImageUrl(),poi.getDescription(),poi.getPopularity()};
            poiBatchArgs.add(obj);
        }
        int [] rows = null;
        try {
            rows = jdbcTemplate.batchUpdate(sqlQuery, poiBatchArgs);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rows.length;

    }

    @Override
    public int save(POI poi) {
        String point = "POINT("+poi.getLat()+" "+poi.getLng()+")";
        String sqlQuery = "insert ignore into pois (poiId, name, geoLocation, imageUrl, description, popularity, estimateVisitTime) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        int row = 0;
        try {
            row = jdbcTemplate.update(sqlQuery, poi.getPoiId(),
                    poi.getName(),
                    point,
                    poi.getImageUrl(),
                    poi.getDescription(),
                    poi.getPopularity(),
                    poi.getEstimateVisitTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public POI findById(long id) {
        String sqlQuery = "select poiId, name, ST_X(geoLocation) as lat, ST_Y(geoLocation) as lng," +
                " imageUrl, description, popularity, estimateVisitTime from pois " +
                "where poiId = ?";

        POI poi = null;
        try {
            poi = jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new int[]{BIGINT},
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
        return poi;
    }

    @Override
    public int update(POI poi) {
        String point = "POINT("+poi.getLat()+" "+poi.getLng()+")";
        String sqlQuery ="update pois set name = ?, geoLocation = ?, imageUrl = ?, " +
                "description = ?, popularity = ?, estimateVisitTime = ? where poiId = ?";

        return jdbcTemplate.update(sqlQuery, poi.getPoiId(),
                poi.getName(),
                point,
                poi.getImageUrl(),
                poi.getDescription(),
                poi.getPopularity(),
                poi.getEstimateVisitTime());
    }

    @Override
    public int update(List<POI> poiList) {
        String sqlQuery ="update pois set name = ?, geoLocation = ?, " +
                "imageUrl = ?, description = ?, popularity = ?, estimateVisitTime = ? where poiId = ?";

        List<Object[]> poiBatchArgs = new ArrayList<>();
        for (POI poi : poiList) {
            String point = "POINT("+poi.getLat()+" "+poi.getLng()+")";
            Object [] obj = new Object[]{poi.getPoiId(), poi.getName(),point, poi.getImageUrl(),
                    poi.getDescription(),poi.getPopularity(), poi.getEstimateVisitTime()};
            poiBatchArgs.add(obj);
        }
        int [] rows = null;
        try {
            rows = jdbcTemplate.batchUpdate(sqlQuery, poiBatchArgs);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rows.length;
    }

    @Override
    public int deleteById(long id) {
        return jdbcTemplate.update("delete from pois where poiId = ?", id);
    }

    @Override
    public int delete(List<POI> poiList) {
        String sqlQuery = "delete from pois where poiId = ?";
        List<Object[]> poiBatchArgs = new ArrayList<>();
        for (POI poi : poiList) {
            Object [] obj = new Object[]{poi.getPoiId()};
            poiBatchArgs.add(obj);
        }
        int [] rows = null;
        try {
            rows = jdbcTemplate.batchUpdate(sqlQuery, poiBatchArgs);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rows.length;
    }
}
