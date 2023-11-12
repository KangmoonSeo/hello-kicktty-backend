package org.hellokicktty.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.lang.Nullable;

@Entity
@Getter
public class Kickboard {
    @Id
    Long id;
    Double lat;
    Double lng;
    Long cluster_id;
    Boolean danger;
    Boolean border;

    @Builder
    protected Kickboard(Long id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.cluster_id = 0L;
        this.danger = false;
        this.border = false;
    }

    public Kickboard() {
        this.id = 0L;
        this.lat = 0.0;
        this.lng = 0.0;
        this.cluster_id = 0L;
        this.danger = false;
        this.border = false;
    }

    public void update(@Nullable Long cluster_id, @Nullable Boolean danger, @Nullable Boolean border) {
        this.cluster_id = cluster_id;
        this.danger = danger;
        this.border = border;
    }
}
