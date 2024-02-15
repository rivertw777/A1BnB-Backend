package A1BnB.backend.domain.post.model.entity;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.BaseTimeEntity;
import jakarta.persistence.Column;
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
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "location", length = 100)
    private String location;

    @Builder
    public Post(Member author, String photoUrl, String location) {
        this.author = author;
        this.photoUrl = photoUrl;
        this.location = location;
    }

}
