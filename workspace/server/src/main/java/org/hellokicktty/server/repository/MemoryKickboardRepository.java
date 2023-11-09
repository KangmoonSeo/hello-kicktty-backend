package org.hellokicktty.server.repository;

import org.hellokicktty.server.domain.Kickboard;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public List<Kickboard> findAllInRange(Double lat, Double lng, Double radius) {
        List<Kickboard> kickboardsInRange = new ArrayList<>();

        for (Kickboard kickboard : kickboardList) {
            if ((kickboard.getLat() - lat) * (kickboard.getLat() - lat)
                    + (kickboard.getLng() - lng) * (kickboard.getLng() - lng)
                    < radius * radius) {
                kickboardsInRange.add(kickboard);
            }
        }
        return kickboardsInRange;
    }
}



