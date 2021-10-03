package com.flag.travelplanner.search.service;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.search.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<POI> searchPOIByKeyword(String keyword) {
        return searchRepository.findPOIByKeyword(keyword);
    }

    @Override
    public List<POI> searchNearbyPOIs(double lat, double lng, double range) {
        List<POI> res = searchRepository.findNearByPOIs(lat, lng, range);
        if (res.size() > 20) {
            res = res.subList(0, 20);
        }
        return res;
    }
}
