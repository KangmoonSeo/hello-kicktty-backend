package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.*;
import org.hellokicktty.server.dto.NamespaceResponseDto;
import org.hellokicktty.server.dto.RecommendResponseDto;
import org.hellokicktty.server.service.ClusterService;
import org.hellokicktty.server.service.KickboardService;
import org.hellokicktty.server.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.hellokicktty.server.service.ClusterService.clusterKickboards;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final KickboardService kickboardService;
    private final RecommendService recommendService;
    private final int MAX_CLUSTER_NUMBER = 4;


    @GetMapping("/namespace")
    public NamespaceResponseDto getNamespace() {
        Double lat = null;
        Double lng = null;
        List<Kickboard> kickboards = kickboardService.findKickboardsInOrder(lat, lng);

        List<Cluster> clusters = clusterKickboards(kickboards, lat, lng);
        kickboards = kickboards.stream()
                .filter(kickboard -> kickboard.getDanger())
                .collect(Collectors.toList());
        List<KickboardName> kickboardsName = recommendService.assignKickboardName(kickboards);
        List<ClusterName> clustersName = recommendService.assignClusterName(clusters);

        return new NamespaceResponseDto(kickboardsName, clustersName);
    }

    @GetMapping("/recommend")
    public RecommendResponseDto recommendClustersByKickboardId(Long id) {

        Kickboard kickboard = kickboardService.findKickboard(id);
        if (kickboard == null) return new RecommendResponseDto();

        List<Kickboard> kickboardList = kickboardService.findKickboardsInOrder(kickboard.getLat(), kickboard.getLng());
        List<Cluster> clusters = ClusterService.clusterKickboards(kickboardList, kickboard.getLat(), kickboard.getLng());
        clusters = clusters.subList(0, Math.min(MAX_CLUSTER_NUMBER, clusters.size()));


        return new RecommendResponseDto(id, clusters);
    }


}
