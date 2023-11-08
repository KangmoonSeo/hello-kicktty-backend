package org.hellokicktty.server.config;

import jakarta.persistence.EntityManager;
import org.hellokicktty.server.repository.JpaKickboardRepository;
import org.hellokicktty.server.repository.KickboardRepository;
import org.hellokicktty.server.repository.MemoryKickboardRepository;
import org.hellokicktty.server.service.KickboardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public KickboardService kickboardService() {
        return new KickboardService(kickboardRepository());
    }

    @Bean
    public KickboardRepository kickboardRepository() {

        //    return new MemoryKickboardRepository();
        return new JpaKickboardRepository(em);
    }
}
