package org.hellokicktty.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClusterName {
    Long id;
    Coordinate center;
    String name;

    public ClusterName(Cluster cluster) {
        this.id = cluster.getCluster_id();
        this.center = cluster.getCenter();
        this.name = "";
    }
}
