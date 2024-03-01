package A1BnB.backend.global.redis.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void setRefreshToken(String username, String refreshToken, Long expiration) {
        redisTemplate.opsForValue().set(username, refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    @Transactional(readOnly = true)
    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Transactional
    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }

}