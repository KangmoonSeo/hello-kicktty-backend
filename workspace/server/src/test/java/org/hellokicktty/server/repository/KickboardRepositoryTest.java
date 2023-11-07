package org.hellokicktty.server.repository;

import org.assertj.core.api.Assertions;
import org.hellokicktty.server.domain.Kickboard;
import org.hellokicktty.server.service.KickboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class KickboardRepositoryTest {

    @Autowired
    private KickboardRepository kickboardRepository;

    @Test
    public void 킥보드_저장() {
        // given
        Long kickId = 1002L;
        Kickboard kickboard = Kickboard.builder()
                .id(kickId)
                .lon(102.0)
                .lat(120.0)
                .build();

        // when
        Long saved = kickboardRepository.save(kickboard);

        // then
        assertThat(saved).isEqualTo(kickId);


    }
}