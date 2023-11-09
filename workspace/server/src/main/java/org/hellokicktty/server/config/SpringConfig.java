package org.hellokicktty.server.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.repository.*;
import org.hellokicktty.server.service.KickboardService;
import org.hellokicktty.server.service.LayerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SpringConfig {
    private final EntityManager em;

    @Bean
    public KickboardService kickboardService() {
        return new KickboardService(kickboardRepository());
    }

    @Bean
    public KickboardRepository kickboardRepository() {
        // return new JpaKickboardRepository(em);
        return new MemoryKickboardRepository();
    }

    @Bean
    public LayerService layerService() {
        return new LayerService(layerRepository());
    }

    @Bean
    public LayerRepository layerRepository() {
        return new JpaLayerRepository(em);
    }
}
