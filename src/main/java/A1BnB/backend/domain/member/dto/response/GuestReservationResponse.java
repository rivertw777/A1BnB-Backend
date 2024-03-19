package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDateTime;

public record GuestReservationResponse(Long bookId, Long postId, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                       String thumbnail, String location, String hostName, Integer paymentAmount) {
    public GuestReservationResponse {
    }
}
