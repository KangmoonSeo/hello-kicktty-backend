package org.hellokicktty.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public class JpaKickboardRepository implements KickboardRepository {
    private final EntityManager em;

    public JpaKickboardRepository(EntityManager em) {
        this.em = em;
    }

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

    public List<Kickboard> findKickboardsInRange(Double lat, Double lng, Double length) {
        return em.createQuery("SELECT k FROM Kickboard k " +
                        "WHERE k.lat BETWEEN :minLat AND :maxLat " +
                        "AND k.lng BETWEEN :minLng AND :maxLng", Kickboard.class)
                .setParameter("minLat", lat - length / 2)
                .setParameter("maxLat", lat + length / 2)
                .setParameter("minLng", lng - length / 2)
                .setParameter("maxLng", lng + length / 2)
                .getResultList();
    }


}
