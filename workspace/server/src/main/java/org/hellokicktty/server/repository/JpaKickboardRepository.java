package org.hellokicktty.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaKickboardRepository implements KickboardRepository {
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

    public List<Kickboard> findAllInRange(Double lat, Double lng, Double radius) {
        return em.createQuery("SELECT k FROM Kickboard k"
                                + " WHERE (k.lat - :a) * (k.lat - :a) + (k.lng - :b) * (k.lng - :b) < :r * :r"
                        , Kickboard.class)
                .setParameter("a", lat)
                .setParameter("b", lng)
                .setParameter("r", radius)
                .getResultList();

    }


}
