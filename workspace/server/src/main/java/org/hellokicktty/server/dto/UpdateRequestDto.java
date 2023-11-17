package org.hellokicktty.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateRequestDto {
    List<Kickboard> kickboards = new ArrayList<>();

}
