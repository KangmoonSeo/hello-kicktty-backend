package org.hellokicktty.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddRequestDto {
    Long id;

    Double lat;
    Double lng;
}
