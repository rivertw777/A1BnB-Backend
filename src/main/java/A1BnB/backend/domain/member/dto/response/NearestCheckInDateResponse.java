package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDateTime;

public record NearestCheckInDateResponse(LocalDateTime checkInDate) {
    public NearestCheckInDateResponse {
    }
}
