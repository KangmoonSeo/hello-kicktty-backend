package org.hellokicktty.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class KickboardRepository {
    private final EntityManager em;

    public void save(Kickboard kickboard) {
        if (kickboard.getId() == null) {
            em.persist(kickboard);
        } else {
            em.merge(kickboard);
        }
    }

    public Kickboard findOne(Long id) {
        return em.find(Kickboard.class, id);
    }

    public List<Kickboard> findAll() {
        return em.createQuery("select k from Kickboard k", Kickboard.class)
                .getResultList();
    }

}
