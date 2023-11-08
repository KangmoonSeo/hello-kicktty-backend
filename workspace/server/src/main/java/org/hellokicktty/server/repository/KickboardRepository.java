package org.hellokicktty.server.repository;

import org.hellokicktty.server.domain.Kickboard;

import java.util.List;

public interface KickboardRepository {

    public Long save(Kickboard kickboard);

    public void remove(Kickboard kickboard);

    public Kickboard findById(Long id);

    public List<Kickboard> findKickboardsInRange(Double lat, Double lng, Double length);

    public List<Kickboard> findAll();
}
