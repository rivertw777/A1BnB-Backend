package A1BnB.backend.domain.postLike.model.entity;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "postLikeInfos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId")
    private Member guest;

    @Builder
    public PostLikeInfo(Post post, Member guest) {
        setPost(post);
        setGuest(guest);
    }

    public void setPost(Post post){
        this.post = post;
        post.setPostLikeInfos(this);
    }

    public void setGuest(Member guest){
        this.guest = guest;
        guest.setPostLikeInfos(this);
    }

}