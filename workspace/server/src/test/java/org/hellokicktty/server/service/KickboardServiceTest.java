package org.hellokicktty.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KickboardServiceTest {

    @Autowired
    private KickboardService kickboardService;

    @Test
    public void 파이썬_서버_통신_테스트() {
        // given

        // when
        Boolean result = kickboardService.requestCluster(120.5, 121);

        // then
        assertThat(result).isTrue();
    }
}