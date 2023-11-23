package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hellokicktty.server.domain.ClusterWithName;
import org.hellokicktty.server.domain.KickboardWithName;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamespaceResponseDto {
    List<KickboardWithName> kickboards = new ArrayList<>();
    List<ClusterWithName> clusters = new ArrayList<>();
}
