package org.hellokicktty.server.service;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.repository.KickboardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KickboardService {
    private final KickboardRepository kickboardRepository;
}
