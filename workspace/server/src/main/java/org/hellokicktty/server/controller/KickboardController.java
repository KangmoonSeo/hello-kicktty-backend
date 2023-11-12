package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.KickboardListResponseDto;
import org.hellokicktty.server.dto.KickboardResponseDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kickboards")
public class KickboardController {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final KickboardService kickboardService;


    @GetMapping
    KickboardListResponseDto findKickboards(Double lat, Double lng) {

        double distance = -1d;
        List<Kickboard> kickboardList = kickboardService.findKickboardsInOrder(lat, lng);

        for (Kickboard kickboard : kickboardList) {
            if (lat == null || lng == null) break;
            log.debug("cluster_number = {}, distance = {}", kickboard.getCluster_id(), distance);
            if (kickboard.getCluster_id() == -1) continue;
            distance = KickboardService.getCoordinateDelta(lat, lng, kickboard.getLat(), kickboard.getLng());

            if (distance < KickboardService.CLUSTER_METER_RANGE) {
                distance = KickboardService.convertCtoM(distance); // meter distance 설정
            }
            break;
        }
        List<Cluster> clusters = KickboardService.clusterKickboards(kickboardList);
        return new KickboardListResponseDto(distance, clusters, kickboardList);
    }

    @GetMapping("/{id}")
    KickboardResponseDto findKickboard(@PathVariable Long id) {
        return new KickboardResponseDto(kickboardService.findKickboard(id));
    }

    @PostMapping
    Long addKickboard(Long id, Double lat, Double lng) {
        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .lat(lat)
                .lng(lng)
                .build();
        return kickboardService.addKickboard(kickboard);
    }

    @PatchMapping("/{id}")
    Long updateKickboard(@PathVariable Long id, Long cluster_id, Boolean danger, Boolean border) {
        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .build();
        kickboard.update(cluster_id, danger, border); // nullable
        return kickboardService.updateKickboard(kickboard);
    }

    @DeleteMapping("/{id}")
    Long removeKickboard(@PathVariable Long id) {
        return kickboardService.removeKickboard(id);
    }
}
