package org.hellokicktty.server.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClusterWithName {

    Long cluster_id;
    Double distance;
    Coordinate center = new Coordinate(0d, 0d);
    String name = "";
    List<Border> borders = new ArrayList<>();


    public ClusterWithName(Cluster cluster) {
        this.cluster_id = cluster.getCluster_id();
        this.distance = cluster.getDistance();
        this.center = cluster.getCenter();
        this.name = "";
        this.borders = cluster.getBorders();
    }
}
