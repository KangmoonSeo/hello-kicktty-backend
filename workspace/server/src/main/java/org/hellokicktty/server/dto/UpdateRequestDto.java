package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hellokicktty.server.domain.Kickboard;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestDto {
    List<Kickboard> kickboards;
}
