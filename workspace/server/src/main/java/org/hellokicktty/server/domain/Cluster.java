package org.hellokicktty.server.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Cluster {

    Long cluster_id;
    Double distance;
    Coordinate center;
    List<Coordinate> borders;
}
