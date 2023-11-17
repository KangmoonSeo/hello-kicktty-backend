package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.UpdateRequestDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UpdateController {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final KickboardService kickboardService;

    @PostMapping("/update")
    public void updateKickboards(@RequestBody UpdateRequestDto dto) {

        List<Kickboard> kickboardList = dto.getKickboards();
        for (Kickboard k : kickboardList) {
            kickboardService.updateKickboard(k);
        }
        log.info("{} kickboards are updated on repository", dto.getKickboards().size());
    }
}
