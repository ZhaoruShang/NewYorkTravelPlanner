package com.flag.travelplanner.route.algos;


import com.flag.travelplanner.route.entity.Route;

import java.util.*;

public class SimpleRoute implements RouteAlgorithm{
    private static class Pair {
        int key;
        double value;
        Pair(int key, double value) {
            this.key = key;
            this.value = value;
        }
    }
    private  List<List<Integer>> POIArrangement;


    @Override
    public List<List<Integer>> generateRoute(Route route, double[][] timeMatrix, double maxAllowableHours) {
        POIArrangement = new ArrayList<>();
        if (timeMatrix.length == 0) return POIArrangement;
        //PriorityQueue<Pair> minTime = new PriorityQueue<>(Comparator.comparingDouble(x -> x.value));
        Set<Integer> visited = new HashSet<>();
        int curPOI = 0;
        List<Integer> poiList = new ArrayList<>();
        poiList.add(0);
        while(visited.size() < timeMatrix.length - 2) {
            //minTime.clear();
            double minTime = Double.MAX_VALUE;
            int next = 0;
            for (int i = 1; i < timeMatrix.length-1; i++){

                if (!visited.contains(i) && timeMatrix[curPOI][i] < minTime) {
                    minTime = timeMatrix[curPOI][i];
                    next = i;
                }

                //    minTime.add(new Pair(i, timeMatrix[curPOI][i]));

            }
            poiList.add(next);
            visited.add(next);
        }
//        poiList.add(timeMatrix.length - 1);

        /*
        List<Integer> poiList = new ArrayList<>();
        while(!minTime.isEmpty()) {
            poiList.add(minTime.poll().key);
        }

         */
        POIArrangement.add(poiList);
        return POIArrangement;
    }
}
