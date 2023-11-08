package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Layer;
import org.hellokicktty.server.domain.RestrictLayer;
import org.hellokicktty.server.domain.SpareLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SetupResponseDto {
    List<Layer> restrictLayers;
    List<Layer> spareLayers;

}

