package A1BnB.backend.domain.postBook.service;

import static A1BnB.backend.domain.post.exception.PostExceptionMessages.POST_BOOK_INFO_NOT_FOUND;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.dto.PostDto.PostBookRequest;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postBook.repository.PostBookRepostiory;
import A1BnB.backend.domain.post.exception.PostException;
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
        List<Date> bookedDates = dateService.getDates(postBookRequest.checkInDate(), postBookRequest.checkOutDate());
        savePostBookInfo(post, currentMember, postBookRequest, bookedDates);
        // 호스트 정산 금액 추가
        post.getHost().addAmount(postBookRequest.paymentAmount());
    }

    private void savePostBookInfo (Post post, Member currentMember, PostBookRequest postBookRequest,
                                   List<Date> bookedDates){
        PostBookInfo postBookInfo = PostBookInfo.builder()
                .post(post)
                .guest(currentMember)
                .checkInDate(postBookRequest.checkInDate())
                .checkOutDate(postBookRequest.checkOutDate())
                .bookedDates(bookedDates)
                .paymentAmount(postBookRequest.paymentAmount())
                .build();
        postBookRepostiory.save(postBookInfo);
    }

    @Override
    @Transactional
    public void unbookPost(Post post, Member currentMember, Long bookId) {
        PostBookInfo postBookInfo = findPostBookInfo(bookId);
        Integer paymentAmount = postBookInfo.getPaymentAmount();
        postBookRepostiory.delete(postBookInfo);
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
