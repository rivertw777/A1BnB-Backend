package A1BnB.backend.domain.postBook.model;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_book_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "booked_dates")
    private List<LocalDateTime> bookedDates;

    @Builder
    public PostBookInfo(Post post, Member member, List<LocalDateTime> bookedDates) {
        setPost(post);
        setMember(member);
        this.bookedDates = bookedDates;
    }

    public void setPost(Post post){
        this.post = post;
        post.setPostBookInfos(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.setPostBookInfos(this);
    }

}