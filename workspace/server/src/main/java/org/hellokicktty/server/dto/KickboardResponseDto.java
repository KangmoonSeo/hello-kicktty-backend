package org.hellokicktty.server.dto;

import org.hellokicktty.server.domain.Kickboard;

public class KickboardResponseDto {
    Long id;
    Double lat;
    Double lng;
    Integer clusterNumber;
    Boolean danger;

    public KickboardResponseDto() {
        this.id = 0L;
        this.lat = 0.0;
        this.lng = 0.0;
        this.clusterNumber = -1;
        this.danger = false;
    }

    public KickboardResponseDto(Kickboard kickboard) {
        this.id = kickboard.getId();
        this.lat = kickboard.getLat();
        this.lng = kickboard.getLng();
        this.clusterNumber = kickboard.getClusterNumber();
        this.danger = kickboard.getDanger();
    }
}
