package com.flag.travelplanner.search.repository;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Types.DOUBLE;
import static java.sql.Types.VARCHAR;

@Repository
public class SearchRepositoryJdbc implements SearchRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<POI> findPOIByKeyword(String keyword) {
        String sqlQuery = "select poiId, name, ST_X(geoLocation) as lat, ST_Y(geoLocation) as lng," +
                " imageUrl, description, popularity, estimateVisitTime from pois " +
                "where match(name, description) against(? in natural language mode)";

        List<POI> allPOIs = null;
        try {
            allPOIs = jdbcTemplate.query(sqlQuery, new Object[]{keyword}, new int[]{VARCHAR},
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
        return allPOIs;
    }

    @Override
    public List<POI> findNearByPOIs(double lat, double lng, double range) {
        String origin = "POINT("+lat+" "+lng+")";
        String sqlQuery = "select poiId, name, ST_X(geoLocation) as lat, ST_Y(geoLocation) as lng," +
                " imageUrl, description, popularity, estimateVisitTime  from pois " +
                "where ST_distance_Sphere(ST_GeomFromText(?), geoLocation) < ?";

        List<POI> allPOIs = null;
        try {
            allPOIs = jdbcTemplate.query(sqlQuery, new Object[]{origin, range}, new int[]{VARCHAR, DOUBLE},
                    (rs, rowNum) -> new POI(rs.getInt("poiId"),
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
        return allPOIs;
    }

}
