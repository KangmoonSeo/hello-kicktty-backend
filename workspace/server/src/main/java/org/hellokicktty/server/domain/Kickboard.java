package org.hellokicktty.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Kickboard {
    @Id
    Long id;
    Double lat;
    Double lng;
    final Integer clusterNumber = -1;
    final Boolean danger = false;

    @Builder
    protected Kickboard(Long id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }
}
