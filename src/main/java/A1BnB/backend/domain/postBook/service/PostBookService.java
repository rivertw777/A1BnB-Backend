package A1BnB.backend.domain.postBook.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostBookService {
    void bookPost(Post post, Member currentMember, PostBookRequest postBookRequest);
    void unbookPost(Post post, Member currentMember);
    List<PostBookInfo> findByMember(Member currentMember);
    List<PostBookInfo> findByPosts(List<Post> posts);
}
