package A1BnB.backend.domain.postBook.service;

import static A1BnB.backend.global.exception.constants.PostExceptionMessages.POST_BOOK_INFO_NOT_FOUND;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.request.PostBookRequest;
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
    public void bookPost(Post post, Member currentMember, PostBookRequest postBookRequest) {
        LocalDateTime checkInDate = postBookRequest.checkInDate();
        LocalDateTime checkOutDate = postBookRequest.checkOutDate();
        savePostBookInfo(post, currentMember, postBookRequest);
        // 게시물 예약 가능 날짜 - 예약 날짜
        List<Date> availableDates = dateService.deleteFromAvailableDates(post.getAvailableDates(), checkInDate, checkOutDate);
        // 게시물 예약 가능 날짜 변경
        post.setAvailableDates(availableDates);
        // 호스트 정산 금액 추가
        Member host = post.getHost();
        host.addAmount(postBookRequest.paymentAmount());
    }

    private void savePostBookInfo (Post post, Member currentMember, PostBookRequest postBookRequest){
        PostBookInfo postBookInfo = PostBookInfo.builder()
                .post(post)
                .guest(currentMember)
                .checkInDate(postBookRequest.checkInDate())
                .checkOutDate(postBookRequest.checkOutDate())
                .paymentAmount(postBookRequest.paymentAmount())
                .build();
        postBookRepostiory.save(postBookInfo);
    }

    @Override
    @Transactional
    public void unbookPost(Post post, Member currentMember, Long bookId) {
        PostBookInfo postBookInfo = findPostBookInfo(bookId);
        LocalDateTime checkInDate = postBookInfo.getCheckInDate();
        LocalDateTime checkOutDate = postBookInfo.getCheckOutDate();
        Integer paymentAmount = postBookInfo.getPaymentAmount();
        postBookRepostiory.deleteById(bookId);
        // 게시물 예약 가능 날짜 + 예약 취소 날짜
        List<Date> availableDates = dateService.revertToAvailableDates(post.getAvailableDates(), checkInDate, checkOutDate);
        // 게시물 예약 가능 날짜 변경
        post.setAvailableDates(availableDates);
        // 호스트 정산 금액 차감
        post.getHost().subAmount(paymentAmount);
    }

    private PostBookInfo findPostBookInfo(Long bookId){
        return postBookRepostiory.findById(bookId)
                .orElseThrow(()->new PostException(POST_BOOK_INFO_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostBookInfo> findByGuest(Member currentMember){
        return postBookRepostiory.findByGuest(currentMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostBookInfo> findByPosts(List<Post> posts) {
        return postBookRepostiory.findByPosts(posts);
    }

}
