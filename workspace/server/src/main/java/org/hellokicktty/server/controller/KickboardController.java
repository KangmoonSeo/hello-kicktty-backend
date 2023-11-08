package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.KickboardResponseDto;
import org.hellokicktty.server.service.KickboardService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("kickboards")
public class KickboardController {

    private final KickboardService kickboardService;

    @GetMapping
    List<Kickboard> findKickboardsInRange(Double lat, Double lng) {
        List<KickboardResponseDto> dto = new ArrayList<>();
        kickboardService.findKickboardsInRange(lat, lng)
                .stream()
                .forEach(item -> dto.add(new KickboardResponseDto(item)));
        return kickboardService.findKickboardsInRange(lat, lng);
    }

    @GetMapping("/{id}")
    Kickboard findKickboard(@PathVariable Long id) {
        return kickboardService.findKickboard(id);
    }


    @PostMapping
    Kickboard addKickboard(Long id, Double lat, Double lng) {
        kickboardService.addKickboard(id, lat, lng);
        return kickboardService.findKickboard(id);
    }

    @DeleteMapping("/{id}")
    void addKickboard(@PathVariable Long id) {
        kickboardService.removeKickboard(id);
    }


}
