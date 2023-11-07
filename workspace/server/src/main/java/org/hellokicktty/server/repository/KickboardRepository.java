package org.hellokicktty.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KickboardRepository {
    private final EntityManager em;

    @Transactional
    public Long save(Kickboard kickboard) {
        if (kickboard.getId() == null) {
            em.persist(kickboard);
        } else {
            em.merge(kickboard);
        }
        return kickboard.getId();
    }

    @Transactional
    public void remove(Kickboard kickboard) {
        em.remove(kickboard);
    }


    public Kickboard findById(Long id) {
        return em.find(Kickboard.class, id);
    }

    public List<Kickboard> findAll() {
        return em.createQuery("SELECT k FROM Kickboard k", Kickboard.class)
                .getResultList();
    }

    public List<Kickboard> findKickboardsInRange(double lat, double lon, double length) {
        return em.createQuery("SELECT k FROM Kickboard k " +
                        "WHERE k.lat BETWEEN :minLat AND :maxLat " +
                        "AND k.lon BETWEEN :minLon AND :maxLon", Kickboard.class)
                .setParameter("minLat", lat - length / 2)
                .setParameter("maxLat", lat + length / 2)
                .setParameter("minLon", lon - length / 2)
                .setParameter("maxLon", lon + length / 2)
                .getResultList();
    }


}
