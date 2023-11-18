package org.hellokicktty.server.service;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClusterService {


    // == utils ==

    public static List<Cluster> clusterKickboards(List<Kickboard> kickboardList, Double lat, Double lng) {

        Map<Long, Cluster> clusterMap = new HashMap<>();
        for (Kickboard kickboard : kickboardList) {
            if (kickboard.getBorder() >= 0) {
                Long cluster_id = kickboard.getCluster_id();
                Cluster cluster = clusterMap.getOrDefault(cluster_id, new Cluster());
                cluster.setCluster_id(cluster_id);
                Coordinate coordinate = new Coordinate(kickboard.getLat(), kickboard.getLng());
                cluster.getBorders().add(coordinate);
                clusterMap.put(cluster_id, cluster);
            }
        }
        clusterMap.remove(-1L);
        List<Cluster> clusterList = new ArrayList<>(clusterMap.values());

        for (Cluster cluster : clusterList) {

            List<Coordinate> borders = cluster.getBorders();
            Coordinate center = getCenter(borders);
            cluster.setCenter(center);

            if (lat == null || lng == null) {
                cluster.setDistance(-1d);
                continue;
            }
            double dist = 1e9d;

            for (Coordinate border : borders) {
                Double delta = KickboardService.getCoordinateDelta(new Coordinate(lat, lng), border);
                delta = KickboardService.coordinateToMeter(delta);
                dist = Math.min(dist, delta);
            }
            cluster.setDistance(dist);
        }
        if (lat == null || lng == null) return clusterList;

        clusterList = clusterList.stream()
                .sorted(Comparator.comparingDouble(Cluster::getDistance))
                .collect(Collectors.toList());

        return clusterList;
    }

    public static Coordinate getCenter(List<Coordinate> borders) {
        Double lat = 0d;
        Double lng = 0d;
        if (borders.isEmpty()) return new Coordinate(lat, lng);

        for (Coordinate border : borders) {
            lat += border.getLat();
            lng += border.getLng();
        }
        lat /= borders.size();
        lng /= borders.size();
        return new Coordinate(lat, lng);
    }

    public static double getShortestDistance(List<Cluster> clusterList) {
        if (clusterList.isEmpty()) {
            return -1d;
        }
        return clusterList.get(0).getDistance();
    }

}
