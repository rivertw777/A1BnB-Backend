package A1BnB.backend.domain.amenity.model.entity;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "amenities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "confidence")
    private Double confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoId")
    private Photo photo;

    @Builder
    public Amenity(String type, Double confidence) {
        this.type = type;
        this.confidence = confidence;
    }

    public void setPhoto(Photo photo){
        this.photo = photo;
    }

}
