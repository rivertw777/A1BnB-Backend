package A1BnB.backend.domain.postBook.model;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.model.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "postBookInfos")
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
    @JoinColumn(name = "guestId")
    private Member guest;

    @NotNull
    @Column(name = "checkInDate")
    private LocalDateTime checkInDate;

    @NotNull
    @Column(name = "checkOutDate")
    private LocalDateTime checkOutDate;

    @OneToMany(mappedBy = "postBookInfo", cascade = CascadeType.ALL)
    private List<Date> bookedDates = new ArrayList<>();

    @NotNull
    @Column(name = "paymentAmount")
    private Integer paymentAmount;

    @Builder
    public PostBookInfo(Post post, Member guest, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                        List<Date> bookedDates, Integer paymentAmount) {
        setPost(post);
        setGuest(guest);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        setBookedDates(bookedDates);
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

    public void setBookedDates(List<Date> bookedDates) {
        this.bookedDates = bookedDates;
        for (Date date : bookedDates) {
            date.setPostBookInfo(this);
        }
    }

}