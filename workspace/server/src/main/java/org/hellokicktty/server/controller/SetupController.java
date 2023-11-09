package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.dto.SetupResponseDto;
import org.hellokicktty.server.service.LayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SetupController {

    private final LayerService layerService;

    // @GetMapping("/setup")
    SetupResponseDto getLayers() {
        return new SetupResponseDto(layerService.groupRestrictLayers(), layerService.groupSpareLayers());
    }

}
