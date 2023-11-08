package org.hellokicktty.server.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Layer {

    Long id;
    List<Coordinate> coordinates;

}
