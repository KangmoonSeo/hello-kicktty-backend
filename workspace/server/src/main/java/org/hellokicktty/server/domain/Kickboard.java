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
    Double lon;
    Integer clusterNumber;
    Boolean isDanger;

    @Builder
    public Kickboard(Long id, Double lat, Double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
