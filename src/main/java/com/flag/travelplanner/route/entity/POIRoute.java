package com.flag.travelplanner.route.entity;

import java.io.Serializable;

public class POIRoute implements Serializable {
    private static final long serialVersionUID = -2499805386024864470L;

    private long poiId;
    private long routeId;
    private int seqNo;
    public POIRoute() {}
    public POIRoute(long poiId, long routeId, int seqNo) {
        this.poiId = poiId;
        this.routeId = routeId;
        this.seqNo = seqNo;
    }

    public long getPoiId() {
        return poiId;
    }

    public void setPoiId(long poiId) {
        this.poiId = poiId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }
}
