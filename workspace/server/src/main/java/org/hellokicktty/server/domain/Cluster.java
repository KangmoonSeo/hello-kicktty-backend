package org.hellokicktty.server.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Cluster {

    Long cluster_id;
    Double distance;
    Coordinate center = new Coordinate(0d, 0d);
    List<Border> borders = new ArrayList<>();
}
