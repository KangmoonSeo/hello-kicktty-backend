package org.hellokicktty.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.repository.KickboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KickboardService {

    private final KickboardRepository kickboardRepository;
    private final double CLUSTER_RANGE = 0.005;
    private String url = "http://localhost:8081/cluster"; // AI Server Request End-Point

    @PostConstruct
    public void init() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public KickboardService(KickboardRepository kickboardRepository) {
        this.kickboardRepository = kickboardRepository;
    }


    public void addKickboard(Long id, Double lat, Double lng) {
        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .lat(lat)
                .lng(lng)
                .build();
        kickboardRepository.save(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLng());

    }

    public void removeKickboard(Long id) {
        Kickboard kickboard = kickboardRepository.findById(id);
        kickboardRepository.remove(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLng());
    }

    // box scope
    public List<Kickboard> findKickboardsInRange(Double lat, Double lng) {
        if (lat == null && lng == null) {
            return kickboardRepository.findAll();
        }
        return kickboardRepository.findKickboardsInRange(lat, lng, CLUSTER_RANGE);
    }

    public Kickboard findKickboard(Long id) {
        return kickboardRepository.findById(id);
    }


    // lat, lng 기준 클러스터링 요청
    private Boolean requestCluster(double lat, double lng) {
        if (true)
            return true;

        final RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, Double> requestBody = new HashMap<>();
        requestBody.put("lat", lat);
        requestBody.put("lng", lng);

        HttpEntity<HashMap<String, Double>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // log.warn("cluster server response returns");
            return true;
        } else {
            // log.warn("cluster server could not ");
            return false;
        }
    }

}
