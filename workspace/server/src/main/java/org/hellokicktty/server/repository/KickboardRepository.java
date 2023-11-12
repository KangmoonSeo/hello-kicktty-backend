package org.hellokicktty.server.repository;

import org.hellokicktty.server.domain.Kickboard;

import java.util.List;

public interface KickboardRepository {

    public Long save(Kickboard kickboard);

    public void remove(Kickboard kickboard);
    public void update(Kickboard kickboard);

    public Kickboard findById(Long id);

    public List<Kickboard> findAllInRange(Double lat, Double lng, Double radius);

    public List<Kickboard> findAll();
}
