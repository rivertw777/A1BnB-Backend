package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDate;

public record NearestCheckInDateResponse(LocalDate checkInDate) {
    public NearestCheckInDateResponse {
    }
}
