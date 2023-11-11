package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hellokicktty.server.domain.Kickboard;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class KickboardListResponseDto {
    Integer rewardable = 0;
    List<Kickboard> kickboardList;


}
