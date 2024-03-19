package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.response.HostReservationResponse;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class HostReservationResponseMapper {

    public List<HostReservationResponse> toReservationResponses(List<PostBookInfo> postBookInfos) {
        return postBookInfos.stream()
                .map(postBookInfo -> toReservationResponse(postBookInfo))
                .collect(Collectors.toList());
    }

    private HostReservationResponse toReservationResponse(PostBookInfo postBookInfo) {
        return new HostReservationResponse(
                postBookInfo.getPost().getId(),
                postBookInfo.getCheckInDate(),
                postBookInfo.getCheckOutDate(),
                postBookInfo.getPost().getPhotos().get(0).getOriginalUrl(),
                postBookInfo.getPost().getLocation(),
                postBookInfo.getGuest().getName(),
                postBookInfo.getPaymentAmount()
        );
    }

}
