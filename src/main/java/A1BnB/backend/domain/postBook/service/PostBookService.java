package A1BnB.backend.domain.postBook.service;

import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.dto.PostDto.PostBookRequest;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.List;

public interface PostBookService {
    void bookPost(Post post, Member currentMember, PostBookRequest postBookRequest);
    void unbookPost(Post post, Member currentMember, Long bookId);
    List<PostBookInfo> findByGuest(Member currentMember);
    List<PostBookInfo> findByPosts(List<Post> posts);
}
