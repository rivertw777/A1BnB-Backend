package A1BnB.backend.global.redis.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest
class RedisServiceTest {
    final String KEY = "username";
    final String VALUE = "refreshToken";
    final Long DURATION = 6000L;

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        redisService.setRefreshToken(KEY, VALUE, DURATION);
    }

    @AfterEach
    void shutDown() {
        redisService.deleteRefreshToken(KEY);
    }

    @Test
    @DisplayName("Refresh 토큰 조회 확인")
    void saveAndFindTest() {
        // when
        String findToken = redisService.getRefreshToken(KEY);
        // then
        assertThat(findToken).isEqualTo(VALUE);
    }

    @Test
    @DisplayName("Refresh 토큰 삭제 확인")
    void deleteTest() {
        // when
        redisService.deleteRefreshToken(KEY);
        String findValue = redisService.getRefreshToken(KEY);
        // then
        assertThat(findValue).isEqualTo(null);
    }

    @Test
    @DisplayName("만료시간 이후 토큰 삭제 확인")
    void expiredTest() {
        // when
        String findValue = redisService.getRefreshToken(KEY);
        await().pollDelay(Duration.ofMillis(6000L)).untilAsserted(
                () -> {
                    String expiredValue = redisService.getRefreshToken(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo(null);
                }
        );
    }

}