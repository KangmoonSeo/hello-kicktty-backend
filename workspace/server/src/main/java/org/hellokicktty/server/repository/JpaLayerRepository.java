package org.hellokicktty.server.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.RestrictLayer;
import org.hellokicktty.server.domain.SpareLayer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
@RequiredArgsConstructor
public class JpaLayerRepository implements LayerRepository {
    private final EntityManager em;

    public List<RestrictLayer> findAllRestrictLayers() {
        return em.createQuery("SELECT r FROM RestrictLayer r", RestrictLayer.class)
                .getResultList();
    }

    public List<SpareLayer> findAllSpareLayers() {
        return em.createQuery("SELECT s FROM SpareLayer s", SpareLayer.class)
                .getResultList();
    }

}
