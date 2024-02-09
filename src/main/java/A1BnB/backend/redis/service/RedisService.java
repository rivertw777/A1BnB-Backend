package A1BnB.backend.redis.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    public void setToken(String token, String username, long expiration) {
        redisTemplate.opsForValue().set(token, username);
        redisTemplate.expire(token, expiration, TimeUnit.MILLISECONDS);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}