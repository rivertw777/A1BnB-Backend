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
    public void setToken(String userName, String refreshToken, long expiration) {
        redisTemplate.opsForValue().set(userName, refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String accessToken) {
        return redisTemplate.opsForValue().get(accessToken);
    }

    @Transactional
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}