package A1BnB.backend.domain.post.model;

import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.photo.model.Photo;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postLike.model.PostLikeInfo;
import A1BnB.backend.global.model.BaseTimeEntity;
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
import java.util.ArrayList;
import java.util.List;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Member host;

    @NotNull
    @Column(name = "location", length = 100)
    private String location;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @NotNull
    @Column(name = "pricePerNight")
    private Integer pricePerNight;

    @NotNull
    @Column(name = "maximumOccupancy")
    private Integer maximumOccupancy;

    @NotNull
    @Column(name = "caption")
    private String caption;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikeInfo> postLikeInfos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostBookInfo> postBookInfos = new ArrayList<>();

    @Builder
    public Post(Member host, String location, List<Photo> photos, Integer pricePerNight, Integer maximumOccupancy,
                String caption) {
        setHost(host);
        this.location = location;
        setPhotos(photos);
        this.pricePerNight = pricePerNight;
        this.maximumOccupancy = maximumOccupancy;
        this.caption = caption;
    }

    private void setHost(Member host) {
        this.host = host;
        host.setPost(this);
    }

    private void setPhotos(List<Photo> photos){
        this.photos = photos;
        for (Photo photo : photos) {
            photo.setPost(this);
        }
    }

    public void setPostLikeInfos(PostLikeInfo postLikeInfo){
        this.postLikeInfos.add(postLikeInfo);
    }

    public void setPostBookInfos(PostBookInfo postBookInfo){
        this.postBookInfos.add(postBookInfo);
    }

}
