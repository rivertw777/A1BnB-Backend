package A1BnB.backend.domain.photo.model.entity;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.room.model.entity.Room;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "photos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 원본 사진경로
    @NotNull
    @Column(name = "original_url")
    private String originalUrl;

    // 분석된 사진 경로
    @NotNull
    @Column(name = "detected_url")
    private String detectedUrl;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private List<Amenity> amenities = new ArrayList<>();

    @OneToOne(mappedBy = "photo", cascade = CascadeType.ALL)
    private Room room;

    @Builder
    public Photo(String originalUrl, String detectedUrl, List<Amenity> amenities, Room room) {
        this.originalUrl = originalUrl;
        this.detectedUrl = detectedUrl;
        setAmenities(amenities);
        setRoom(room);
    }

    public void setAmenities(List<Amenity> amenities){
        this.amenities = amenities;
        for (Amenity amenity : amenities) {
            amenity.setPhoto(this);
        }
    }

    public void setRoom(Room room){
        this.room = room;
        room.setPhoto(this);
    }

    public void setPost(Post post){
        this.post = post;
    }

}