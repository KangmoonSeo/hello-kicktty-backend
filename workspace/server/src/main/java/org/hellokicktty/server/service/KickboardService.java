package org.hellokicktty.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KickboardService {

    private final KickboardRepository kickboardRepository;
    Logger log = LoggerFactory.getLogger(Logger.class);
    private final double FIND_RANGE = 0.05;
    private final String URL = "http://localhost:8081/cluster"; // AI Server Request End-Point

    @PostConstruct
    public void init() {
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

    public List<Kickboard> findKickboardsInRange(Double lat, Double lng) {
        if (lat == null || lng == null) return kickboardRepository.findAll();

        return kickboardRepository.findAllInRange(lat, lng, FIND_RANGE);
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

}
