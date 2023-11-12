package org.hellokicktty.server.dto;

import lombok.Getter;
import org.hellokicktty.server.domain.Kickboard;

@Getter
public class KickboardResponseDto {
    Long id;
    Double lat;
    Double lng;
    Long cluster_id = 0L;
    Boolean danger = false;
    Boolean border = false;

    public KickboardResponseDto(Kickboard kickboard) {
        if (kickboard == null) {
            this.id = 0L;
            this.lat = 0.0;
            this.lng = 0.0;
        } else {
            this.id = kickboard.getId();
            this.lat = kickboard.getLat();
            this.lng = kickboard.getLng();
            this.cluster_id = kickboard.getCluster_id();
            this.danger = kickboard.getDanger();
            this.border = kickboard.getBorder();
        }
    }
}
