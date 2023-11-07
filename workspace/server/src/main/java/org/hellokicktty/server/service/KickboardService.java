package org.hellokicktty.server.service;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.repository.KickboardRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KickboardService {

    private final KickboardRepository kickboardRepository;
    private String url = "http://localhost:8081/cluster";

    void parkKickboard(Long id, Double lat, Double lon) {
        Kickboard kickboard = Kickboard.builder()
                .id(id)
                .lat(lat)
                .lon(lon)
                .build();
        kickboardRepository.save(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLon());
    }

    void rentKickboard(Long id) {
        Kickboard kickboard = kickboardRepository.findById(id);
        kickboardRepository.remove(kickboard);

        requestCluster(kickboard.getLat(), kickboard.getLon());
    }

    // box scope
    public List<Kickboard> findKickboardsInRange(double lat, double lon, double length) {
        return kickboardRepository.findKickboardsInRange(lat, lon, length);
    }

    // lat, lon 기준 클러스터링 요청
    Boolean requestCluster(double lat, double lon) {
        final RestTemplate restTemplate = new RestTemplate();


        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, Double> requestBody = new HashMap<>();
        requestBody.put("lat", lat);
        requestBody.put("lon", lon);

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
