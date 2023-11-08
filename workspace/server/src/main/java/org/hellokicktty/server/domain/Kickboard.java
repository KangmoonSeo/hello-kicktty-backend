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
    Integer clusterNumber;
    Boolean danger;

    @Builder
    public Kickboard(Long id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        clusterNumber = -1;
        danger = false;
    }
}
