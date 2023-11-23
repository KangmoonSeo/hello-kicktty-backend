package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hellokicktty.server.domain.Cluster;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResponseDto {
    Long my_id;

    List<Cluster> clusters = new ArrayList<>();
}
