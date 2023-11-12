package org.hellokicktty.server.dto;

import lombok.Getter;
import org.hellokicktty.server.domain.Kickboard;

import java.util.List;

@Getter
public class UpdateRequestDto {
    List<Kickboard> kickboards;


}
