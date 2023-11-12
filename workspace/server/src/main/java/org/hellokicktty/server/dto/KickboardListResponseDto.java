package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hellokicktty.server.domain.Cluster;
import org.hellokicktty.server.domain.Kickboard;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class KickboardListResponseDto {

    Double distance = -1d;
    List<Cluster> clusters;
    List<Kickboard> kickboards;
}
