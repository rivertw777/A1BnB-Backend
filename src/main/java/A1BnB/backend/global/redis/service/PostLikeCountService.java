package A1BnB.backend.global.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeCountService {

    private final StringRedisTemplate redisTemplate;

    // 좋아요 수 초기화
    public void initCount(Long postId) {
        redisTemplate.opsForValue().set("postLikeCount:" + postId, String.valueOf(0));
    }

    // 좋아요 수 증가
    public void increaseCount(Long postId) {
        redisTemplate.opsForValue().increment("postLikeCount:" + postId);
    }

    // 좋아요 수 감소
    public void decreaseCount(Long postId) {
        redisTemplate.opsForValue().decrement("postLikeCount:" + postId);
    }

    // 특정 게시물의 좋아요 수 조회
    public int getCount(Long postId) {
        String count = redisTemplate.opsForValue().get("postLikeCount:" + postId);
        return count != null ? Integer.parseInt(count) : 0;
    }
}