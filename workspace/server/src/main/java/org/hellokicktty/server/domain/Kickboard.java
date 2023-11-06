package org.hellokicktty.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Kickboard {
    @Id
    Long id;
    Double lat;
    Double lon;
    Integer clusterNumber;
    Boolean isDanger;
}
