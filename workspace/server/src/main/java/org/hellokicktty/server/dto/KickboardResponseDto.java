package org.hellokicktty.server.dto;

import lombok.Getter;
import org.hellokicktty.server.domain.Kickboard;

@Getter
public class KickboardResponseDto {
    Long id;
    Double lat;
    Double lng;
    final Integer cluster_number = -1;
    final Boolean danger = false;

    public KickboardResponseDto(Kickboard kickboard) {
        if (kickboard == null) {
            this.id = 0L;
            this.lat = 0.0;
            this.lng = 0.0;
        } else {
            this.id = kickboard.getId();
            this.lat = kickboard.getLat();
            this.lng = kickboard.getLng();
        }
    }
}
