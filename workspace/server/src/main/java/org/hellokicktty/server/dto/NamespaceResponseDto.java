package org.hellokicktty.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hellokicktty.server.domain.ClusterName;
import org.hellokicktty.server.domain.ClusterWithName;
import org.hellokicktty.server.domain.KickboardName;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NamespaceResponseDto {
    List<KickboardName> kickboards = new ArrayList<>();
    List<ClusterName> clusters = new ArrayList<>();
}
