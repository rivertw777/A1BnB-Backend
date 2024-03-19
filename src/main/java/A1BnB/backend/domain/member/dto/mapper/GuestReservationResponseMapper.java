package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.response.GuestReservationResponse;

import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GuestReservationResponseMapper {

    public List<GuestReservationResponse> toReservationResponses(List<PostBookInfo> postBookInfos) {
        return postBookInfos.stream()
                .map(postBookInfo -> toReservationResponse(postBookInfo))
                .collect(Collectors.toList());
    }

    private GuestReservationResponse toReservationResponse(PostBookInfo postBookInfo) {
        return new GuestReservationResponse(
                postBookInfo.getId(),
                postBookInfo.getPost().getId(),
                postBookInfo.getCheckInDate(),
                postBookInfo.getCheckOutDate(),
                postBookInfo.getPost().getPhotos().get(0).getOriginalUrl(),
                postBookInfo.getPost().getLocation(),
                postBookInfo.getPost().getHost().getName(),
                postBookInfo.getPaymentAmount()
        );
    }

}
