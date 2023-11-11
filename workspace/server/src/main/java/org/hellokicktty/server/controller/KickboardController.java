package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.KickboardListResponseDto;
import org.hellokicktty.server.dto.KickboardResponseDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("kickboards")
public class KickboardController {


    private final KickboardService kickboardService;
    Logger log = LoggerFactory.getLogger(Logger.class);

    @GetMapping
    KickboardListResponseDto findKickboardsInRange(Double lat, Double lng, @Nullable Boolean tow) {

        List<Kickboard> kickboardList = kickboardService.findKickboardsInRange(lat, lng);
        Integer rewardable = -1;
        double distance = -1d;
        for (Kickboard kickboard : kickboardList) {
            if (lat == null || lng == null) break;
            log.debug("cluster_number = {}, distance = {}", kickboard.getClusterNumber(), distance);
            if (kickboard.getClusterNumber() == -1) continue;
            distance = KickboardService.convertCoordinateToMeter(
                    new Coordinate(kickboard.getLat(), kickboard.getLng()),
                    new Coordinate(lat, lng));

            if (distance < KickboardService.CLUSTER_METER_RANGE) {
                rewardable = kickboard.getClusterNumber(); // reward 설정
            }
            break;
        }
        return new KickboardListResponseDto(rewardable, distance, kickboardList);
    }

    @GetMapping("/{id}")
    KickboardResponseDto findKickboard(@PathVariable Long id) {
        return new KickboardResponseDto(kickboardService.findKickboard(id));
    }

    @PostMapping
    KickboardResponseDto addKickboard(Long id, Double lat, Double lng) {
        return new KickboardResponseDto(kickboardService.addKickboard(id, lat, lng));
    }

    @DeleteMapping("/{id}")
    void removeKickboard(@PathVariable Long id) {
        kickboardService.removeKickboard(id);
    }

}
