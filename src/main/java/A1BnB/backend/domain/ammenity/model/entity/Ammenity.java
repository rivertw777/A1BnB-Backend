package A1BnB.backend.domain.ammenity.model.entity;

import A1BnB.backend.domain.photo.model.entity.Photo;
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
@Table(name = "ammenities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ammenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ammenityId;

    @Column(name = "type")
    private String type;

    @Column(name = "confidence")
    private Double confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Builder
    public Ammenity(String type, Double confidence) {
        this.type = type;
        this.confidence = confidence;
    }

    public void setPhoto(Photo photo){
        this.photo = photo;
    }

}
