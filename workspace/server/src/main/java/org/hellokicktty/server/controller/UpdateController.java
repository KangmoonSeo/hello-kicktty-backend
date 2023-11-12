package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.dto.UpdateRequestDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kickboards")
public class UpdateController {

    private final KickboardService kickboardService;

    Logger log = LoggerFactory.getLogger(Logger.class);

    // call from cluster
    @PostMapping
    public void update(UpdateRequestDto dto) {

        dto.getKickboards().forEach(kickboard -> kickboardService.updateKickboard(kickboard));
        log.info("{} kickboard data are updated", dto.getKickboards().size());
    }
}
