package org.hellokicktty.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class SpareLayer {
    @Id
    Long id;
    Double lat;
    Double lng;
    
}
