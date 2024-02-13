package A1BnB.backend.domain.redis.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void setToken(String username, String refreshToken, long expiration) {
        redisTemplate.opsForValue().set(username, refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Transactional
    public void deleteToken(String username) {
        redisTemplate.delete(username);
    }
}