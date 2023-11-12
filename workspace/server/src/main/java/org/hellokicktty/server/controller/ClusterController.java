package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.ClusterListResponseDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClusterController {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final KickboardService kickboardService;
    private final int LEAST_CLUSTER_NUMBER = 4;

    @GetMapping("/clusters/{id}")
    public ClusterListResponseDto findClustersByKickboardId(@PathVariable Long id) {

        Kickboard kickboard = kickboardService.findKickboard(id);
        if (kickboard == null) return new ClusterListResponseDto();
        List<Kickboard> kickboardList = kickboardService.findKickboardsInOrder(kickboard.getLat(), kickboard.getLng());

        List<Cluster> clusters = KickboardService.clusterKickboards(kickboardList);

        clusters.subList(0, Math.min(LEAST_CLUSTER_NUMBER, clusters.size()));
        return new ClusterListResponseDto(clusters);
    }
}
