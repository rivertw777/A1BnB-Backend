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
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private Member guest;

    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;

    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @Builder
    public PostBookInfo(Post post, Member guest, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                        Integer paymentAmount) {
        setPost(post);
        setGuest(guest);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.paymentAmount = paymentAmount;
    }

    public void setPost(Post post){
        this.post = post;
        post.setPostBookInfos(this);
    }

    public void setGuest(Member guest){
        this.guest = guest;
        guest.setPostBookInfos(this);
    }

}