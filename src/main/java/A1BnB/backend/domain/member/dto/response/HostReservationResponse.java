package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDateTime;

public record HostReservationResponse(Long postId, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                      String thumbnail, String location, String guestName, Integer paymentAmount) {
    public HostReservationResponse {
    }
}
