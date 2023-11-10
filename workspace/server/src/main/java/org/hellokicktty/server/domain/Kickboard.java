package org.hellokicktty.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
public class Kickboard {
    @Id
    Long id;
    Double lat;
    Double lng;
    final Integer clusterNumber = -1;
    final Boolean danger = false;
    final Boolean border = false;

    @Builder
    protected Kickboard(Long id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public Kickboard() {
        this.id = 0L;
        this.lat = 0.0;
        this.lng = 0.0;
    }
}
