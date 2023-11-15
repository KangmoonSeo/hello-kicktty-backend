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

import java.util.List;

import static org.hellokicktty.server.service.ClusterService.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kickboards")
public class KickboardController {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final KickboardService kickboardService;

    @GetMapping
    KickboardListResponseDto findKickboards(Double lat, Double lng) {

        List<Kickboard> kickboardList = kickboardService.findKickboardsInOrder(lat, lng);
        List<Cluster> clusters = clusterKickboards(kickboardList);
        Double distance = getShortestDistance(clusters);

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
