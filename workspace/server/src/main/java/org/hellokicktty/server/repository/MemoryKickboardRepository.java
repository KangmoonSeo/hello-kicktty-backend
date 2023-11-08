package org.hellokicktty.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryKickboardRepository implements KickboardRepository {

    private List<Kickboard> kickboardList = new ArrayList<>();

    public Long save(Kickboard kickboard) {
        kickboardList.add(kickboard);

        return kickboard.getId();
    }

    public void remove(Kickboard kickboard) {
        kickboardList.remove(kickboard);
    }


    public Kickboard findById(Long id) {
        for (Kickboard kickboard : kickboardList) {
            if (kickboard.getId().equals(id)) {
                return kickboard;
            }
        }
        return null;
    }

    public List<Kickboard> findAll() {
        return kickboardList;
    }

    public List<Kickboard> findKickboardsInRange(Double lat, Double lng, Double length) {
        List<Kickboard> kickboardsInRange = new ArrayList<>();

        for (Kickboard kickboard : kickboardList) {
            if (kickboard.getLat() >= (lat - length / 2) &&
                    kickboard.getLat() <= (lat + length / 2) &&
                    kickboard.getLng() >= (lng - length / 2) &&
                    kickboard.getLng() <= (lng + length / 2)) {
                kickboardsInRange.add(kickboard);
            }
        }
        return kickboardsInRange;
    }


}
