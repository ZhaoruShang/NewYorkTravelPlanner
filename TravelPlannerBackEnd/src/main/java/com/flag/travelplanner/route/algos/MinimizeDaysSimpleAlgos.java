package com.flag.travelplanner.route.algos;

import com.flag.travelplanner.poi.entity.POI;
import com.flag.travelplanner.route.entity.Route;

import java.util.*;

public class MinimizeDaysSimpleAlgos implements RouteAlgorithm{
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
        int n = timeMatrix.length;

        for (int i = 0; i < n; i++) {
            int j = 1;
            for (POI poi : route.getPoiList()) {
                timeMatrix[i][j++] += poi.getEstimateVisitTime();
            }
        }

        PriorityQueue<Pair>[] neighboursPQ = new PriorityQueue[n];
        for (int i = 0; i < n; i++) {
            neighboursPQ[i] =  new PriorityQueue<>(Comparator.comparingDouble(x -> x.value));
            for (int j = 0; j < n; j++){
                if (j != i) {
                    neighboursPQ[i].offer(new Pair(j,timeMatrix[i][j]));
                }
            }
        }
        Set<Integer> visited = new HashSet<>();

        visited.add(0);

        while(visited.size() < n) {
            dfs(0, visited, new ArrayList<>(), neighboursPQ, 0, maxAllowableHours);
        }
        return POIArrangement;
    }
    private void dfs(int i, Set<Integer> visited, List<Integer> curPath,
                     PriorityQueue<Pair>[] neighboursPQ,
                     double curTime, double maxTime) {
        if (curTime > maxTime) {
            if (curPath.size() > 1)
                POIArrangement.add(new ArrayList<>(curPath));
            return;
        }
        visited.add(i);
        curPath.add(i);
        while(!neighboursPQ[i].isEmpty() && visited.contains(neighboursPQ[i].peek().key)) {
            neighboursPQ[i].poll();
        }
        if (neighboursPQ[i].isEmpty()) {
            if (curPath.size() > 1)
                POIArrangement.add(new ArrayList<>(curPath));
            return;
        }
        Pair next = neighboursPQ[i].poll();
        dfs(next.key, visited, curPath, neighboursPQ, curTime + next.value, maxTime);
    }
}
