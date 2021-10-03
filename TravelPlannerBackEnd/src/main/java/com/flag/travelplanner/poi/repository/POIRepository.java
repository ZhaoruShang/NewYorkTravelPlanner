package com.flag.travelplanner.poi.repository;

import com.flag.travelplanner.poi.entity.POI;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface POIRepository {
    public long count();
    public int save(POI poi);
    public int save(List<POI> poiList);
    public POI findById(long id);
    public int update(POI poi);
    public int update(List<POI> poiList);
    public int deleteById(long id);
    public int delete(List<POI> poiList);
}
