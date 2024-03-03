package A1BnB.backend.domain.post.model.entity;

import A1BnB.backend.domain.member.model.entity.Member;
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
@Table(name = "post_like_member_set")
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
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder
    public PostLikeInfo(Post post, Member member) {
        setPost(post);
        setMember(member);
    }

    public void setPost(Post post){
        this.post = post;
        post.setPostLikeInfos(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.setPostLikeInfos(this);
    }

}