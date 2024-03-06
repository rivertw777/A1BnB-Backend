package A1BnB.backend.domain.postBook.service;

import A1BnB.backend.domain.member.exception.MemberNotFoundException;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postBook.repository.PostBookRepostiory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostBookServiceImpl implements PostBookService {

    private final PostBookRepostiory postBookRepostiory;

    @Override
    @Transactional
    public List<LocalDateTime> bookPost(Post post, Member currentMember, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<LocalDateTime> bookedDates = getBookedDates(checkInDate, checkOutDate);
        savePostBookInfo(post, currentMember, bookedDates);
        return bookedDates;
    }

    private List<LocalDateTime> getBookedDates(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return Stream.iterate(checkInDate, date -> date.isBefore(checkOutDate), date -> date.plusDays(1))
                .collect(Collectors.toList());
    }

    private void savePostBookInfo (Post post, Member currentMember, List<LocalDateTime> bookedDates){
        PostBookInfo postBookInfo = PostBookInfo.builder()
                .post(post)
                .member(currentMember)
                .bookedDates(bookedDates)
                .build();
        postBookRepostiory.save(postBookInfo);
    }

    @Override
    @Transactional
    public List<LocalDateTime> unbookPost(Post post, Member currentMember) {
        List<LocalDateTime> bookedDates = findPostBookInfo(post, currentMember).getBookedDates();
        postBookRepostiory.deleteByPostAndMember(post, currentMember);
        return bookedDates;
    }

    public PostBookInfo findPostBookInfo(Post post, Member currentMember){
        return postBookRepostiory.findByPostAndMember(post, currentMember)
                .orElseThrow(()->new MemberNotFoundException("예약 정보가 없다."));
    }

}
