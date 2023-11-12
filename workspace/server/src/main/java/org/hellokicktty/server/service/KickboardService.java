package org.hellokicktty.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
    public static double FIND_COORDINATE_RANGE, CLUSTER_COORDINATE_RANGE;
    public static double FIND_METER_RANGE, CLUSTER_METER_RANGE;



    private final String URL = "http://localhost:8081/cluster"; // AI Server Request End-Point

    @PostConstruct
    public void init() {
        this.FIND_COORDINATE_RANGE = 0.05;
        this.CLUSTER_COORDINATE_RANGE = 0.005;
        this.CLUSTER_METER_RANGE = 100; // 100 meter

        addDummyKickboards();
    }

    public Kickboard addKickboard(Long id, Double lat, Double lng) {

        if (kickboardRepository.findById(id) != null) {
            log.info("duplicate kickboard {} addition called", id);
            return new Kickboard();
        }

        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .lat(lat)
                .lng(lng)
                .build();
        kickboardRepository.save(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLng());
        log.info("kickboard {} added", kickboard.getId());
        return kickboard;

    }

    public void removeKickboard(Long id) {
        Kickboard kickboard = kickboardRepository.findById(id);
        if (kickboard == null) {
            log.info("void kickboard {} removal called", id);
            return;
        }

        kickboardRepository.remove(kickboard);
        log.info("kickboard {} removed", id);
        requestCluster(kickboard.getLat(), kickboard.getLng());
    }

    public void updateKickboard(Kickboard kickboard) {
        kickboardRepository.update(kickboard);

    }

    public List<Kickboard> findKickboardsInRange(Double lat, Double lng) {

        if (lat == null || lng == null) return kickboardRepository.findAll();
        // 결과 거리 별 정렬해서 제공
        List<Kickboard> kickboardList = kickboardRepository.findAllInRange(lat, lng, FIND_COORDINATE_RANGE);
        Collections.sort(
                kickboardList,
                Comparator.comparingDouble(
                        kickboard ->
                                convertCoordinateToMeter(
                                        new Coordinate(lat, lng),
                                        new Coordinate(kickboard.getLat(), kickboard.getLng()))));
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


    public static double convertCoordinateToMeter(Coordinate a, Coordinate b) {
        // 지구 반지름 (미터 단위)
        final double EARTH_RADIUS = 6371000;

        // 라디안으로 변환
        double lat1 = Math.toRadians(a.getLat());
        double lng1 = Math.toRadians(a.getLng());
        double lat2 = Math.toRadians(b.getLat());
        double lng2 = Math.toRadians(b.getLng());

        // 위도 및 경도의 차이 계산
        double dLat = lat2 - lat1;
        double dLon = lng2 - lng1;

        // Haversine 공식을 사용하여 거리 계산
        double haversineLat = Math.pow(Math.sin(dLat / 2), 2);
        double haversineLng = Math.pow(Math.sin(dLon / 2), 2);

        double a1 = haversineLat + Math.cos(lat1) * Math.cos(lat2) * haversineLng;
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));

        return EARTH_RADIUS * c;
    }


}
