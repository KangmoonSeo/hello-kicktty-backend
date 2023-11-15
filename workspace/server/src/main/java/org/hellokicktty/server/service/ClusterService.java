package org.hellokicktty.server.service;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ClusterService {
    public static List<Cluster> clusterKickboards(List<Kickboard> kickboardList) {

        Map<Long, Cluster> clusterMap = new TreeMap<>();
        for (Kickboard kickboard : kickboardList) {
            if (kickboard.getBorder()) {
                Long cluster_id = kickboard.getCluster_id();
                Cluster cluster = clusterMap.getOrDefault(cluster_id, new Cluster());
                cluster.setCluster_id(cluster_id);
                Coordinate coordinate = new Coordinate(kickboard.getLat(), kickboard.getLng());
                cluster.getBorders().add(coordinate);
                clusterMap.put(cluster_id, cluster);
            }
        }

        List<Cluster> clusterList = new ArrayList<>(clusterMap.values());

        for (Cluster cluster : clusterList) {
            List<Coordinate> borders = cluster.getBorders();
            Coordinate center = getCenter(borders);
            cluster.setCenter(center);
        }
        return new ArrayList<>(clusterMap.values());
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
