package org.hellokicktty.server.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.*;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ApplicationContext ac;

    // Kakao API Request End-Point
    private static final String BASE_URL = "https://dapi.kakao.com";
    private static final String KEYWORDS_URL = "/v2/local/search/keyword";
    private static final String CATEGORY_URL = "/v2/local/search/category";
    private static final String ADDRESS_URL = "/v2/local/geo/coord2address";


    // == utils ==

    public List<KickboardWithName> assignKickboardName(List<Kickboard> kickboards) {

        List<KickboardWithName> kickboardsName = new ArrayList<>();

        for (Kickboard kickboard : kickboards) {
            KickboardWithName k = new KickboardWithName(kickboard);
            String doroName = getDoroName(k.getCenter().getLat(), k.getCenter().getLng());
            k.setName(doroName);
            kickboardsName.add(k);
        }
        return kickboardsName;
    }

    public List<ClusterWithName> assignClusterName(List<Cluster> clusters) {

        List<ClusterWithName> clustersWithName = new ArrayList<>();

        for (Cluster cluster : clusters) {
            ClusterWithName c = new ClusterWithName(cluster);
            String doroName = getDoroName(c.getCenter().getLat(), c.getCenter().getLng());
            c.setName(doroName);
            clustersWithName.add(c);
        }
        return clustersWithName;
    }

    public String getDoroName(Double lat, Double lng) {
        Environment env = ac.getEnvironment();
        final String API_KEY = env.getProperty("env.KAKAO_API_KEY");

        WebClient webClient = WebClient
                .builder()
                .baseUrl(BASE_URL)
                .build();

        // 키워드 검색 API 호출
        String keywordsResponse = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(KEYWORDS_URL)
                        .queryParam("query", "인하")
                        .queryParam("x", lng)
                        .queryParam("y", lat)
                        .queryParam("radius", 50)
                        .queryParam("sort", "distance")
                        .build())
                .header("Authorization", "KakaoAK " + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String keyword = parseAddress(keywordsResponse, "place_name");

        if (keyword != null && keyword != "") return keyword;

        // 카테고리 검색 API 호출
        String categoryResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CATEGORY_URL)
                        .queryParam("category_group_code", "MT1,CS2,PS3,SC4,AC5,PK6,OL7,SW8,BK9,CT1,AG2,PO3,AT4,AD5,FD6,CE7,HP8,PM9")
                        .queryParam("x", lng)
                        .queryParam("y", lat)
                        .queryParam("radius", 50)
                        .queryParam("sort", "distance")
                        .build())
                .header("Authorization", "KakaoAK " + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String category = parseAddress(categoryResponse, "place_name");

        if (category != null && category != "") return category;

        // 주소 검색 API 호출
        String addressResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ADDRESS_URL)
                        .queryParam("x", lng)
                        .queryParam("y", lat)
                        .build())
                .header("Authorization", "KakaoAK " + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String address = parseAddress(addressResponse, "road_address");
        if (address != null && address != "") return address;


        return "sorry it's error";
    }

    public static String parseAddress(String response, String target) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode documentsNode = rootNode.get("documents");
            if (documentsNode.isArray() && documentsNode.size() > 0) {
                JsonNode firstDocument = documentsNode.get(0);
                JsonNode targetNode = firstDocument.get(target);
                if (target.equals("address") || target.equals("road_address")) {
                    targetNode = targetNode.get("address_name");
                }
                if (targetNode != null) return targetNode.asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
