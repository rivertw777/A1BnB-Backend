package A1BnB.backend.global.redis.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeCountService {

    private final StringRedisTemplate redisTemplate;

    // 좋아요 수 초기화
    public void initCount(Long postId) {
        redisTemplate.opsForValue().set("postLikeCount:" + postId, String.valueOf(0));
        redisTemplate.opsForZSet().add("postLikeRanking", String.valueOf(postId), 0);
    }

    // 좋아요 수 증가
    public void increaseCount(Long postId) {
        redisTemplate.opsForValue().increment("postLikeCount:" + postId);
        redisTemplate.opsForZSet().incrementScore("postLikeRanking", String.valueOf(postId), 1);
    }

    // 좋아요 수 감소
    public void decreaseCount(Long postId) {
        redisTemplate.opsForValue().decrement("postLikeCount:" + postId);
        redisTemplate.opsForZSet().incrementScore("postLikeRanking", String.valueOf(postId), -1);
    }

    // 게시물 좋아요 수 조회
    public int getCount(Long postId) {
        String count = redisTemplate.opsForValue().get("postLikeCount:" + postId);
        return Integer.parseInt(count);
    }

    // 게시물 좋아요 순위 조회
    public List<Long> getRanking(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        long start = pageNumber * pageSize;
        long end = start + pageSize - 1;

        // 지정된 범위에 해당하는 게시물 ID 조회
        return redisTemplate.opsForZSet().reverseRange("postLikeRanking", start, end)
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}