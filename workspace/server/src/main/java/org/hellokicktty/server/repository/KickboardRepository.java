package org.hellokicktty.server.repository;

import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Kickboard;

import java.util.List;

public interface KickboardRepository {

    public Long save(Kickboard kickboard);

    public Long update(Kickboard kickboard);

    public void remove(Kickboard kickboard);

    public Kickboard findById(Long id);

    public List<Kickboard> findAll();
}
