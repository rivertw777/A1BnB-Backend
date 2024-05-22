package A1BnB.backend.domain.date.model.entity;

import A1BnB.backend.domain.postBook.model.PostBookInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "booked_dates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date")
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postBookInfoId")
    private PostBookInfo postBookInfo;

    @Builder
    public Date(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setPostBookInfo(PostBookInfo postBookInfo) {
        this.postBookInfo = postBookInfo;
    }

}

