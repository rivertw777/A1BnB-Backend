package A1BnB.backend.domain.room.model.entity;

import A1BnB.backend.domain.photo.model.entity.Photo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "probability")
    private Double probability;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoId")
    private Photo photo;

    @Builder
    public Room(String type, Double probability) {
        this.type = type;
        this.probability = probability;
    }

    public void setPhoto(Photo photo){
        this.photo = photo;
    }

}