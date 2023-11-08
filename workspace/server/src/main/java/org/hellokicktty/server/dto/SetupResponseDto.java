package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hellokicktty.server.domain.Layer;

import java.util.List;

@Getter
@AllArgsConstructor
public class SetupResponseDto {
    List<Layer> restrictLayers;
    List<Layer> spareLayers;

}

