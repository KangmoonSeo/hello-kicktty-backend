package org.hellokicktty.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.repository.KickboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KickboardService {

    private final KickboardRepository kickboardRepository;
    Logger log = LoggerFactory.getLogger(Logger.class);

    public static double CLUSTER_METER_RANGE = 5d; // meter

    public static double CLUSTER_COORDINATE_RANGE = 0.00001 * CLUSTER_METER_RANGE; // degree

    private final String URL = "http://localhost:8081/cluster"; // AI Server Request End-Point

    @PostConstruct
    public void init() {
        addDummyKickboards();
    }

    public Long addKickboard(Kickboard kickboard) {

        if (kickboardRepository.findById(kickboard.getId()) != null) {
            log.info("duplicate kickboard {} addition called", kickboard.getId());
            return 0L;
        }

        kickboardRepository.save(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLng());
        log.info("kickboard {} added", kickboard.getId());
        return kickboard.getId();
    }

    public Long updateKickboard(Kickboard kickboard) {
        if (kickboardRepository.findById(kickboard.getId()) == null) {
            log.info("update for null kickboard {} called", kickboard.getId());
            return 0L;
        }
        kickboardRepository.update(kickboard);
        return kickboard.getId();
    }

    public Long removeKickboard(Long id) {
        Kickboard kickboard = kickboardRepository.findById(id);
        if (kickboard == null) {
            log.info("void kickboard {} removal called", id);
            return 0L;
        }

        kickboardRepository.remove(kickboard);
        log.info("kickboard {} removed", id);
        requestCluster(kickboard.getLat(), kickboard.getLng());
        return id;
    }

    public List<Kickboard> findKickboardsInOrder(Double lat, Double lng) {

        List<Kickboard> kickboardList = kickboardRepository.findAll();

        if (lat == null || lng == null) return kickboardList;
        Collections.sort(
                kickboardList,
                Comparator.comparingDouble(
                        kickboard -> (getCoordinateDelta(lat, lng, kickboard.getLat(), kickboard.getLng()))
                )
        );

        return kickboardList;
    }

    public Kickboard findKickboard(Long id) {
        return kickboardRepository.findById(id);
    }


    private void requestCluster(double lat, double lng) {

        final WebClient webClient = WebClient.create();
        HashMap<String, Double> requestBody = new HashMap<>();
        requestBody.put("lat", lat);
        requestBody.put("lng", lng);

        webClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> log.info("Cluster request sent to AI server"),
                        error -> log.warn("No cluster requests, AI Server closed")
                );
    }

    private void addDummyKickboards() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("static/data/dummy.json");
            InputStream inputStream = resource.getInputStream();

            Map<String, List<Map<String, Object>>> jsonData = objectMapper.readValue(inputStream, Map.class);

            List<Map<String, Object>> items = jsonData.get("items");

            for (Map<String, Object> item : items) {
                Kickboard kickboard = Kickboard.builder()
                        .id(Long.parseLong(item.get("title").toString()))
                        .lat((Double) item.get("lat"))
                        .lng((Double) item.get("lng"))
                        .build();

                kickboardRepository.save(kickboard);
            }

            log.info("{} kickboards are saved on repository", kickboardRepository.findAll().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // == utils ==

    public static Double getCoordinateDelta(Double uLat, Double uLng, Double vLat, Double vLng) {
        return getCoordinateDelta(new Coordinate(uLat, uLng), new Coordinate(vLat, vLng));
    }

    public static Double getCoordinateDelta(Coordinate u, Coordinate v) {
        Double dLat = u.getLat() - v.getLat();
        Double dLng = u.getLng() - v.getLng();
        return Math.sqrt(Math.pow(dLat, 2) + Math.pow(dLng, 2));
    }

    public static Double convertCtoM(Double coordinateDelta) {
        return coordinateDelta * 0.00001;
    }


}
