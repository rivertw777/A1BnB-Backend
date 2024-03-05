package A1BnB.backend.domain.postBook.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostBookService {
    List<LocalDateTime> bookPost(Post post, Member currentMember, LocalDateTime checkInDate, LocalDateTime checkOutDate);
    List<LocalDateTime> unbookPost(Post post, Member currentMember);
}
