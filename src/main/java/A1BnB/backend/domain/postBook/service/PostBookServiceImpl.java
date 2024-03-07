package A1BnB.backend.domain.postBook.service;

import static A1BnB.backend.global.exception.constants.PostExceptionMessages.POST_BOOK_INFO_NOT_FOUND;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postBook.repository.PostBookRepostiory;
import A1BnB.backend.global.exception.PostException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostBookServiceImpl implements PostBookService {

    private final PostBookRepostiory postBookRepostiory;
    private final DateService dateService;

    @Override
    @Transactional
    public void bookPost(Post post, Member currentMember, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        savePostBookInfo(post, currentMember, checkInDate, checkOutDate);
        // 게시물 예약 가능 날짜 - 예약 날짜
        List<Date> availableDates = dateService.deleteFromAvailableDates(post.getAvailableDates(), checkInDate, checkOutDate);
        post.setAvailableDates(availableDates);
    }

    private void savePostBookInfo (Post post, Member currentMember, LocalDateTime checkInDate, LocalDateTime checkOutDate){
        PostBookInfo postBookInfo = PostBookInfo.builder()
                .post(post)
                .member(currentMember)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();
        postBookRepostiory.save(postBookInfo);
    }

    @Override
    @Transactional
    public void unbookPost(Post post, Member currentMember) {
        PostBookInfo postBookInfo = findPostBookInfo(post, currentMember);
        LocalDateTime checkInDate = postBookInfo.getCheckInDate();
        LocalDateTime checkOutDate = postBookInfo.getCheckOutDate();
        postBookRepostiory.deleteByPostAndMember(post, currentMember);
        // 게시물 예약 가능 날짜 + 예약 취소 날짜
        List<Date> availableDates = dateService.revertToAvailableDates(post.getAvailableDates(), checkInDate, checkOutDate);
        post.setAvailableDates(availableDates);
    }

    private PostBookInfo findPostBookInfo(Post post, Member currentMember){
        return postBookRepostiory.findByPostAndMember(post, currentMember)
                .orElseThrow(()->new PostException(POST_BOOK_INFO_NOT_FOUND.getMessage()));
    }

}
