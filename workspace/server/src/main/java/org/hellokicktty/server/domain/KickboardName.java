package org.hellokicktty.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KickboardName {
    Long id;
    Coordinate center;
    String name;

    public KickboardName(Kickboard kickboard) {
        this.id = kickboard.getId();
        this.center = new Coordinate(kickboard.getLat(), kickboard.getLng());
        this.name = "";
    }
}
