package A1BnB.backend.domain.date.model.entity;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "available_dates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "date")
    private LocalDateTime date;

    @Builder
    public AvailableDate(Post post, LocalDateTime date) {
        setPost(post);
        this.date = date;
    }

    public void setPost(Post post){
        this.post = post;
        post.setAvailableDates(this);
    }

}

