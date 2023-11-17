package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.dto.AddRequestDto;
import org.hellokicktty.server.dto.KickboardListResponseDto;
import org.hellokicktty.server.dto.KickboardResponseDto;
import org.hellokicktty.server.service.KickboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Cluster> clusters = clusterKickboards(kickboardList, lat, lng);

        Double distance = getShortestDistance(clusters);

        return new KickboardListResponseDto(distance, clusters, kickboardList);
    }

    @GetMapping("/{id}")
    KickboardResponseDto findKickboard(@PathVariable Long id) {
        return new KickboardResponseDto(kickboardService.findKickboard(id));
    }

    @PostMapping
    Long addKickboard(@RequestBody AddRequestDto dto) {

        Long id = dto.getId();
        Double lat = dto.getLat();
        Double lng = dto.getLng();

        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .lat(lat)
                .lng(lng)
                .build();
        return kickboardService.addKickboard(kickboard);
    }

    @DeleteMapping("/{id}")
    Long removeKickboard(@PathVariable Long id) {
        return kickboardService.removeKickboard(id);
    }
}
