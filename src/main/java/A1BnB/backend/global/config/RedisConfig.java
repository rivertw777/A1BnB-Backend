package A1BnB.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    private final RedisProperties redisProperties;

    // lettuce를 사용한 Redis 연결을 위한 ConnectionFactory 빈 설정
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 서버 호스트와 포트 정보를 사용하여 LettuceConnectionFactory 인스턴스 생성
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    // redis-cli 사용을 위한 RedisTemplate 빈 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // RedisTemplate 인스턴스 생성
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // Redis 연결을 위한 ConnectionFactory 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // 키(Key)와 값(Value)의 직렬화를 위해 StringRedisSerializer 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}

