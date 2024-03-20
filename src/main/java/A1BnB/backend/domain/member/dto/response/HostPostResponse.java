package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDateTime;

// 호스트 게시물 응답
public record HostPostResponse(Long postId, LocalDateTime createdAt, String hostName, String thumbnail,
                               String location, Integer pricePerNight, boolean hasReservation) {
    public HostPostResponse {
    }
}